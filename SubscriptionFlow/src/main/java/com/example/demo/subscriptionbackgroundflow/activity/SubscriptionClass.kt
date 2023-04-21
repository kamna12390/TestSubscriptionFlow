package com.example.demo.subscriptionbackgroundflow.activity

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleOwner
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.BASIC_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.IsOutAppPermission
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SIX_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.Purchase_ID
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isDebugMode
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppIcon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppName
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mBasic_Line_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mClose_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mNew_NativeAdsLayout
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mOld_NativeAdsLayout
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumScreenLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_Button_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_CardSelected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_Cardunselected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_True_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.packagerenlist
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.getOfferingsWith
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.Helper
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs
 object SubscriptionClass {
    public class ActivityBuilder(private val activity: Context) :
        Builder() {
        override fun Subcall(): Builder {

            Purchases.debugLogsEnabled = true
            Purchases.configure(
                PurchasesConfiguration.Builder(activity, Purchase_ID).build()
            )
            Purchases.sharedInstance.getOfferingsWith({ error ->
                // An error occurred
                logD("SubscriptionList", "error->${error.message}")
            }) { offerings ->
                offerings.current?.availablePackages?.takeUnless { it.isNullOrEmpty() }?.let {
                    // Display packages for sale
//                    logD(
//                        "yagnik",
//                        "suc-> 1 ${"originalPrice :- " + it[0].product.originalPrice + "\n" + "freeTrialPeriod :- " + it[0].product.freeTrialPeriod + "\n" + "title :- " + it[0].product.title + "\n" + "price :- " + it[0].product.price + "\n" + "description :- " + it[0].product.description + "\n" + "subscriptionPeriod :- " + it[0].product.subscriptionPeriod + "\n" + "sku :- " + it[0].product.sku + "\n"}"
//                    )
                    logD("SubscriptionList", "1->${it[0].product.sku}--2->${it[1].product.sku}--3->${it[0].product.sku}")
                    packagerenlist?.clear()
                    Constants.BASIC_SKU = it[0].product.sku
                    Constants.PREMIUM_SKU = it[1].product.sku
                    Constants.PREMIUM_SIX_SKU = it[0].product.sku

                    packagerenlist = arrayListOf()
                    packagerenlist?.add(
                        Constants.PackagesRen(
                            it[0].product.originalPrice.toString(),
                            it[0].product.freeTrialPeriod.toString(),
                            it[0].product.title,
                            it[0].product.price,
                            it[0].product.description,
                            it[0].product.subscriptionPeriod.toString(),
                            it[0].product.sku
                        )
                    )
                    packagerenlist?.add(
                        Constants.PackagesRen(
                            it[1].product.originalPrice.toString(),
                            it[1].product.freeTrialPeriod.toString(),
                            it[1].product.title,
                            it[1].product.price,
                            it[1].product.description,
                            it[1].product.subscriptionPeriod.toString(),
                            it[1].product.sku
                        )
                    )
//                    logD(
//                        "yagnik",
//                        "suc-> 2 ${"originalPrice :- " + it[1].product.originalPrice + "\n" + "freeTrialPeriod :- " + it[1].product.freeTrialPeriod + "\n" + "title :- " + it[1].product.title + "\n" + "price :- " + it[1].product.price + "\n" + "description :- " + it[1].product.description + "\n" + "subscriptionPeriod :- " + it[1].product.subscriptionPeriod + "\n" + "sku :- " + it[1].product.sku + "\n"}"
//                    )

                }
            }
            return this
        }

        override fun Adsliber(int: Int,life: LifecycleOwner): Builder {
            logD("mAdsAppID", "mAdsAppID->$int---$isDebugMode")
//            Toast.makeText(activity,"mAdsAppID->$int---$isDebugMode",Toast.LENGTH_LONG).show()
            Helper().startDataSync(activity, life)
            AppIDs.init(activity, int, false)
            logD(
                "SubscriptionList",
                "=->${packagerenlist?.toString()}"
            )
            return this
        }
    }

    abstract class Builder  {
        fun setPREMIUM_SIX_SKU(string: String): Builder {
            PREMIUM_SIX_SKU=string
            return this
        }

        fun getPREMIUM_SIX_SKU(): String {
            return Constants.PREMIUM_SIX_SKU
        }

        fun setBASIC_SKU(string: String): Builder {
            BASIC_SKU=string
            return this
        }

        fun getBASIC_SKU(): String {
            return Constants.BASIC_SKU
        }

        fun setPREMIUM_SKU(string: String): Builder {
            PREMIUM_SKU=string
            return this
        }

        fun getPREMIUM_SKU(): String {
            return Constants.PREMIUM_SKU
        }

        fun setPurchase_ID(string: String): Builder {
            Purchase_ID=string
            return this
        }

        fun getPurchase_ID(): String {
            return Constants.Purchase_ID
        }

        //        fun setisSUBSCRIBED(boolean: Boolean):Builder {
//            config.setisSUBSCRIBED(boolean)
//            return this
//        }
        fun getIS_SUBSCRIBED(context: Context?): Boolean {
            return BaseSharedPreferences(context!!).mIS_SUBSCRIBED!!
        }

        fun setIsOutApp(boolean: Boolean): Builder {
            isoutApp=boolean
            return this
        }

        fun getIsOutApp(): Boolean? {
            return isoutApp
        }

        fun setIsOutAppPermission(boolean: Boolean): Builder {
            IsOutAppPermission=boolean
            return this
        }

        fun getIsOutAppPermission(): Boolean {
            return IsOutAppPermission
        }

        fun setActivityOpen(boolean: Boolean, application: Application): Builder {
            BaseSharedPreferences(application).mActivityOpen = boolean
            return this
        }

        fun setIsDebugMode(boolean: Boolean): Builder {
            isDebugMode = boolean
            return this
        }

        fun setPremium_True_Icon(drawable: Drawable): Builder {
            mPremium_True_Icon = drawable
            return this
        }

        fun setBasic_Line_Icon(drawable: Drawable): Builder {
            mBasic_Line_Icon = drawable
            return this
        }

        fun setClose_Icon(drawable: Drawable): Builder {
            mClose_Icon = drawable
            return this
        }

        fun setPremium_Button_Icon(drawable: Drawable): Builder {
            mPremium_Button_Icon = drawable
            return this
        }

        fun setPremium_CardSelected_Icon(drawable: Drawable): Builder {
            mPremium_CardSelected_Icon = drawable
            return this
        }

        fun setPremium_Cardunselected_Icon(drawable: Drawable): Builder {
            mPremium_Cardunselected_Icon = drawable
            return this
        }

        fun setListOfLine(premiumLine: ArrayList<Constants.LineWithIconModel>): Builder {
            mPremiumLine = premiumLine
            return this
        }

        fun setMainScreenListOfLine(premiumLine: ArrayList<Constants.LineWithIconModel>): Builder {
            mPremiumScreenLine = premiumLine
            return this
        }

        fun setDefaultPackagList(premiumLine: ArrayList<Constants.PackagesRen>): Builder {
            packagerenlist = premiumLine
            return this
        }

        fun setAppName(AppName: String): Builder {
            mAppName = AppName
            return this
        }

        fun setAppIcon(drawable: Drawable): Builder {
            mAppIcon = drawable
            return this
        }

        fun setNew_NativeAdsLayout(int: Int): Builder {
            mNew_NativeAdsLayout = int
            return this
        }
        fun setOld_NativeAdsLayout(int: Int): Builder {
            mOld_NativeAdsLayout = int
            return this
        }

        abstract fun Subcall(): Builder
        abstract fun Adsliber(int: Int,life: LifecycleOwner): Builder
    }
}