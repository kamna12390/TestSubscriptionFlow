package com.example.demo.subscriptionbackgroundflow.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.example.demo.subscriptionbackgroundflow.AppSubscription
import com.example.demo.subscriptionbackgroundflow.billing.BillingClientLifecycle
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.manager.PreferencesKeys
import com.example.demo.subscriptionbackgroundflow.manager.SubscriptionManager
import com.example.demo.subscriptionbackgroundflow.viewmodel.BillingViewModel

abstract class BaseSubscriptionActivity : AppCompatActivity() {

    private val mBillingViewModel: BillingViewModel by viewModels<BillingViewModel>()
    // val mSubscriptionViewModel: SubscriptionStatusViewModel by viewModels<SubscriptionStatusViewModel>()

    private lateinit var billingClientLifecycle: BillingClientLifecycle

    private var mPriceMap: HashMap<String, String> = HashMap()
    private var mPriceMapMicro: HashMap<String, Long> = HashMap()
    private var mTrialPeriod: HashMap<String, String> = HashMap()

    protected var liveDataPrice = MutableLiveData<HashMap<String, String>>()
    protected var liveDataPriceMicro = MutableLiveData<HashMap<String, Long>>()
    protected var liveDataPeriod = MutableLiveData<HashMap<String, String>>()
    protected var currencyCode = MutableLiveData<String>()

    lateinit var subscriptionManager: SubscriptionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscriptionManager = SubscriptionManager(this)

        mPriceMap[Constants.PREMIUM_SIX_SKU] = subscriptionManager.getString(PreferencesKeys.SIXMonth_PRICE,"₹100.00")
        mPriceMap[Constants.BASIC_SKU] = subscriptionManager.getString(PreferencesKeys.MONTH_PRICE,"₹249.00")
        mPriceMap[Constants.PREMIUM_SKU] = subscriptionManager.getString(PreferencesKeys.YEAR_PRICE,"₹1,999.00")

        mPriceMapMicro[Constants.PREMIUM_SIX_SKU] = subscriptionManager.getLong(PreferencesKeys.YSIX_PRICE_MICRO,100000000)
        mPriceMapMicro[Constants.BASIC_SKU] = subscriptionManager.getLong(PreferencesKeys.MONTH_PRICE_MICRO,249000000)
        mPriceMapMicro[Constants.PREMIUM_SKU] = subscriptionManager.getLong(PreferencesKeys.YEAR_PRICE_MICRO,1999000000)

        mTrialPeriod[Constants.BASIC_SKU] = subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"P3D")
        mTrialPeriod[Constants.PREMIUM_SKU] = subscriptionManager.getString(PreferencesKeys.YEAR_TRIAL_PERIOD,"P3D")
        mTrialPeriod[Constants.PREMIUM_SIX_SKU] = subscriptionManager.getString(PreferencesKeys.SIX_TRIAL_PERIOD,"P3D")
        currencyCode.postValue(subscriptionManager.getString(PreferencesKeys.CURRENCY_CODE,"INR"))
        liveDataPrice.postValue(mPriceMap)
        liveDataPriceMicro.postValue(mPriceMapMicro)
        liveDataPeriod.postValue(mTrialPeriod)

        /*subscriptionManager.values.asLiveData().observe(this, Observer {
            it[PreferencesKeys.MONTH_PRICE]?.let {
                mPriceMap[Constants.BASIC_SKU] = it
            } ?: kotlin.run {
                mPriceMap[Constants.BASIC_SKU] = "₹250.00"
            }
            it[PreferencesKeys.YEAR_PRICE]?.let {
                mPriceMap[Constants.PREMIUM_SKU] = it
            } ?: kotlin.run {
                mPriceMap[Constants.PREMIUM_SKU] = "₹1,550.00"
            }

            it[PreferencesKeys.MONTH_TRIAL_PERIOD]?.let {
                mTrialPeriod[Constants.BASIC_SKU] = it
            } ?: kotlin.run {
                mTrialPeriod[Constants.BASIC_SKU] = "P3D"
            }
            it[PreferencesKeys.YEAR_TRIAL_PERIOD]?.let {
                mTrialPeriod[Constants.PREMIUM_SKU] = it
            } ?: kotlin.run {
                mTrialPeriod[Constants.PREMIUM_SKU] = "P3D"
            }

        })*/

        // Billing APIs are all handled in the this lifecycle observer.
        billingClientLifecycle = (application as AppSubscription).billingClientLifecycle
        lifecycle.addObserver(billingClientLifecycle)


        // Register purchases when they change.
        billingClientLifecycle.purchaseUpdateEvent.observe(this, Observer {
            registerPurchases(it)
        })

        // Launch the billing flow when the user clicks a button to buy something.
        mBillingViewModel.buyEvent.observe(this, Observer {
            it?.let {
                billingClientLifecycle.launchBillingFlow(this, it)
            }
        })

        mBillingViewModel.skusWithSkuDetails.observe(this, Observer {
            it.forEach {
                val sku: SkuDetails = it.value
                if (sku.sku == Constants.BASIC_SKU) {
                    subscriptionManager.setMonthPrice(sku.price)
                    subscriptionManager.setMonthPrice(sku.priceAmountMicros)
                    subscriptionManager.setMonthTrialPeriod(sku.freeTrialPeriod)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                } else if (sku.sku == Constants.PREMIUM_SKU) {
                    subscriptionManager.setYearPrice(sku.price)
                    subscriptionManager.setYearPrice(sku.priceAmountMicros)
                    subscriptionManager.setYearTrialPeriod(sku.freeTrialPeriod)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                } else if (sku.sku == Constants.PREMIUM_SIX_SKU) {
                    subscriptionManager.setSixMonthrPrice(sku.price)
                    subscriptionManager.setSixPrice(sku.priceAmountMicros)
                    subscriptionManager.setSixTrialPeriod(sku.freeTrialPeriod)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                }
            }

        })


    }

    fun onMonthPlan() {
        mBillingViewModel.buyBasic()
    }

    fun onYearPlan() {
        mBillingViewModel.buyPremium()
    }
    fun onSixPlan() {
        mBillingViewModel.buySixPremium()
    }

    /**
     * Register SKUs and purchase tokens with the server.
     */
    private fun registerPurchases(purchaseList: List<Purchase>?) {
        purchaseList?.let {
            Log.d(TAG, "registerPurchases: ${purchaseList.size}")
            if (it.isNotEmpty()) {
                subscriptionManager.setSubscribe(true)
                onPurchases(it[0].orderId,it[0].skus[0])
            } else {
                subscriptionManager.setSubscribe(false)
            }
        } ?: kotlin.run {
            subscriptionManager.setSubscribe(false)
        }
    }

    private fun refreshData() {
        billingClientLifecycle.queryPurchases()
        //mSubscriptionViewModel.manualRefresh()
    }

    /**
     * Callback for Purchases
     */
    abstract fun onPurchases(orderId : String,str: String)

    companion object {
        private const val TAG = "SubscriptionActivity"
    }
}