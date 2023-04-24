package com.example.demo.testsubscriptionflow

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.demo.subscriptionbackgroundflow.AdsClasss.AppOpenManager
import com.example.demo.subscriptionbackgroundflow.AppSubscription
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionClass
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.IsOutAppPermission
import com.example.demo.subscriptionbackgroundflow.constants.Constants.currentActivity
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.helper.logD

class MyApplication : AppSubscription(), LifecycleObserver,
    Application.ActivityLifecycleCallbacks {
    private var mPackagerenList = arrayListOf(
        Constants.PackagesRen(
            originalPrice = "₹610.00",
            freeTrialPeriod = "P1W",
            title = "Image Crop - Monthly PRO (Photo Crop: Cut, Convert, Trim)",
            price = "₹610.00",
            description = "",
            subscriptionPeriod = "P1M",
            sku = "subscribe_monthly_imagecrop_799"
        ),
        Constants.PackagesRen(
            originalPrice = "₹3,100.00",
            freeTrialPeriod = "P1W",
            title = "Image Crop - Monthly PRO (Photo Crop: Cut, Convert, Trim)",
            price = "₹3,100.00",
            description = "",
            subscriptionPeriod = "P1Y",
            sku = "subscribe_yearly_imagecrop_3999"
        )
    )
    val AppID=7
    var appOpenManager: AppOpenManager? = null


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

         val mPremiumLine = arrayListOf(
            Constants.LineWithIconModel("Custom Size",false,this.resources.getDrawable(R.drawable.ic_pip)),
            Constants.LineWithIconModel("Multiple Merge Video",false,this.resources.getDrawable(R.drawable.ic_font)),
            Constants.LineWithIconModel("Unique Mp3 Cutter",false,this.resources.getDrawable(R.drawable.ic_eff_background)),
            Constants.LineWithIconModel("Advance Editing Tool",false,this.resources.getDrawable(R.drawable.ic_editong_tool)),
            Constants.LineWithIconModel("Remove ADS",false,this.resources.getDrawable(R.drawable.ic_ads)),
            Constants.LineWithIconModel("24/7 Customer Support",false,this.resources.getDrawable(R.drawable.ic_eff_background))
        )
        val mPremiumScreenLine = arrayListOf(
            Constants.LineWithIconModel("Advanced Editing Tool",false,this.resources.getDrawable(R.drawable.ic_pip)),
            Constants.LineWithIconModel("Effective Backgrounds",false,this.resources.getDrawable(R.drawable.ic_font)),
            Constants.LineWithIconModel("Unlimited Emojis & Fonts",false,this.resources.getDrawable(R.drawable.ic_eff_background)),
            Constants.LineWithIconModel("All PRO Creative Templates",false,this.resources.getDrawable(R.drawable.ic_true_icon)),
            Constants.LineWithIconModel("Ads Free Experience",false,this.resources.getDrawable(R.drawable.ic_ads)),
            Constants.LineWithIconModel("No pay for any design",false,this.resources.getDrawable(R.drawable.ic_eff_background))
        )
        SubscriptionClass.ActivityBuilder(this)
            .Adsliber(AppID,ProcessLifecycleOwner.get())
            .setBASIC_SKU("subscribe_monthly_imagecrop")
            .setPREMIUM_SKU("subscribe_yearly_imagecrop")
            .setPREMIUM_SIX_SKU("subscribe_weekly_imagecrop")
            .setIsRevenuCat(false)
            .setPurchase_ID("goog_IuztdnsvmYVjRXaHMiaDmiyOOmN")
            .setIsDebugMode(true)
            .setListOfLine(mPremiumLine)
            .setMainScreenListOfLine(mPremiumScreenLine)
            .setDefaultPackagList(mPackagerenList)
            .setAppName(this.resources.getString(R.string.app_name))
            .setAppIcon(this.resources.getDrawable(R.drawable.ic_app_icon))
            .setPremium_True_Icon(this.resources.getDrawable(R.drawable.ic_select_trail))
            .setBasic_Line_Icon(this.resources.getDrawable(R.drawable.ic_line_lock))
            .setClose_Icon(this.resources.getDrawable(R.drawable.close_icon))
            .setPremium_CardSelected_Icon(this.resources.getDrawable(R.drawable.ic_pro_selected))
            .setPremium_Cardunselected_Icon(this.resources.getDrawable(R.drawable.ic_pro_selection))
            .setPremium_Button_Icon(this.resources.getDrawable(R.drawable.bg_sub_btn_))
            .setNew_NativeAdsLayout(R.layout.layout_native_ads)
            .setOld_NativeAdsLayout(R.layout.layout_native_ads_old)
            .Subcall()
        appOpenManager = AppOpenManager(this)


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onappBackground() {
        SubscriptionClass.ActivityBuilder(this).let {
            if (!isoutApp!!) {
                it.setActivityOpen(true, this)
            }
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn: ComponentName? = am.getRunningTasks(1)[0].topActivity
        if (!BaseSharedPreferences(this).mIS_SUBSCRIBED!!) {
            if (!isoutApp!! && !IsOutAppPermission && !isAdsShowing
                && (cn?.className != "com.example.demo.testsubscriptionflow.SplashScreenActivity")
                && (cn?.className != "com.example.demo.subscriptionbackgroundflow.activity.SubscriptionBackgroundActivity")
                && (cn?.className != "com.example.demo.subscriptionbackgroundflow.activity.SubscriptionActivity")) {
                currentActivity?.let {
                    appOpenManager!!.showAdIfAvailable(it,
                        object : AppOpenManager.OnShowAdCompleteListener {
                            override fun onShowAdComplete() {

                            }
                        })
                }
            }

        }
        SubscriptionClass.ActivityBuilder(this).let {
            it.setActivityOpen(false, this)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (!isAdsShowing) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}