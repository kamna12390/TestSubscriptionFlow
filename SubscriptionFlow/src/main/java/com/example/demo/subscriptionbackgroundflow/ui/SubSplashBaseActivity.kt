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

abstract class SubSplashBaseActivity : AppCompatActivity() {

    private val mBillingViewModel: BillingViewModel by viewModels<BillingViewModel>()

    private lateinit var billingClientLifecycle: BillingClientLifecycle

    protected lateinit var subscriptionManager: SubscriptionManager

    private var isSubscribe = false

    private var mPriceMap: HashMap<String, Long> = HashMap()

    protected var liveDataPrice = MutableLiveData<HashMap<String, Long>>()
    protected var currencyCode = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscriptionManager = SubscriptionManager(this)

        mPriceMap[Constants.PREMIUM_SIX_SKU] = subscriptionManager.getLong(PreferencesKeys.YSIX_PRICE_MICRO,100000000)
        mPriceMap[Constants.BASIC_SKU] = subscriptionManager.getLong(PreferencesKeys.MONTH_PRICE_MICRO,249000000)
        mPriceMap[Constants.PREMIUM_SKU] = subscriptionManager.getLong(PreferencesKeys.YEAR_PRICE_MICRO,1999000000)
        currencyCode.postValue(subscriptionManager.getString(PreferencesKeys.CURRENCY_CODE,"INR"))
        liveDataPrice.postValue(mPriceMap)

        // Billing APIs are all handled in the this lifecycle observer.
        billingClientLifecycle = (application as AppSubscription).billingClientLifecycle
        lifecycle.addObserver(billingClientLifecycle)

        isSubscribe = subscriptionManager.geBoolean(PreferencesKeys.SUBSCRIBE,false)

        // Register purchases when they change.
        billingClientLifecycle.purchaseUpdateEvent.observe(this, Observer {
            purchases(it)
        })

        mBillingViewModel.skusWithSkuDetails.observe(this, Observer {
            it.forEach {
                val sku: SkuDetails = it.value
                Log.d(TAG, "onCreate: $sku")
                if (sku.sku == Constants.BASIC_SKU) {
                    subscriptionManager.setMonthPrice(sku.price)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                } else if (sku.sku == Constants.PREMIUM_SKU) {
                    subscriptionManager.setYearPrice(sku.price)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                }else if (sku.sku == Constants.PREMIUM_SIX_SKU) {
                    subscriptionManager.setSixMonthrPrice(sku.price)
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                }

            }

        })

    }

    /**
     * Register SKUs and purchase tokens with the server.
     */
    private fun purchases(purchaseList: List<Purchase>?) {
        var paymentState : Int = -1
        var sku : String = ""
        var orderId : String = ""
        purchaseList?.let {
            Log.d(TAG, "registerPurchases: ${purchaseList.size}")

            for (purchase in purchaseList) {
                Log.d(TAG, "registerPurchases: ${purchase.purchaseState} $purchase")
                paymentState = purchase.purchaseState
                orderId = purchase.orderId
                sku = purchase.skus[0]
            }


            if (purchaseList.isNotEmpty()) {
                subscriptionManager.setSubscribe(true)
                registerPurchases(true,paymentState,sku,orderId)
            } else {
                subscriptionManager.setSubscribe(false)
                registerPurchases(false,paymentState,sku,orderId)
            }

        } ?: subscriptionManager.setSubscribe(false)

        if (purchaseList == null) {
            registerPurchases(false,paymentState,sku,orderId)
        }

    }

    /**
     * return result for subscribe
     */
    fun isSubscribe() = isSubscribe


    /**
     * it will called when registerPurchases called
     */
    abstract fun registerPurchases(
        isSubscribe: Boolean,
        paymentState: Int,
        sku: String,
        orderId: String
    )

    companion object {
        private const val TAG = "SubSplashBaseActivity"
    }
}