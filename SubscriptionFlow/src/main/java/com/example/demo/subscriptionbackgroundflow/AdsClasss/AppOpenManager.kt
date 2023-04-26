package com.example.demo.subscriptionbackgroundflow.AdsClasss

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isTestMode
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs
import java.text.SimpleDateFormat
import java.util.*

class AppOpenManager(var myApplication: Context) {
    val TAG = "AppOpenManager"
    val TAG1 = "ShowAppOpenAd"

    companion object {
        var appOpenAd: AppOpenAd? = null
        var isSend = false
    }

    init {
        myApplication.let { loadAppOpenAd(false, it) }
    }

    fun loadAppOpenAd(Adx: Boolean = false, context: Context) {
        logD(TAG, "MEDIUM_RECTANGLE  AppOpenID Call Function->${(isSend || isAdAvailable())}")
        if (isSend || isAdAvailable()) {
            return
        }
        with(BaseSharedPreferences(context)) {
            if (!mOpenAdsload!!){
               return
            }

        }


        var s = if (isTestMode) {
            "ca-app-pub-3940256099942544/3419835294"
        } else {
            if (Adx) {
                AppIDs.instnace?.getAdxOpenAds() ?: ""
            } else {
                AppIDs.instnace?.getGoogleOpenAds() ?: ""
            }
        }
        logD(TAG, "MEDIUM_RECTANGLE  AppOpenID ->$s")
        isSend = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            s,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    isSend = false
                    appOpenAd = ad
                    if (Adx) {
                        logD(TAG, "MEDIUM_RECTANGLE onAdLoaded: AppOpen ->ADX ")
                    } else {
                        logD(TAG, "MEDIUM_RECTANGLE onAdLoaded: AppOpen ->AdMob ")
                    }
                }
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isSend = false
                    appOpenAd = null
                    if (Adx) {
                        logD(
                            TAG,
                            "MEDIUM_RECTANGLE onAdFailedToLoad: AppOpen ->ADX ${loadAdError}"
                        )
                    } else {
                        logD(
                            TAG,
                            "MEDIUM_RECTANGLE onAdFailedToLoad: AppOpen ->AdMob ${loadAdError}"
                        )
                    }
                }
            }
        )
    }

    fun showAdIfAvailable(activity: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {
        if (isAdsShowing) {
            logD(TAG1, "The app open ad is already showing.")
            return
        }
        logD(TAG1, "NextActivity :Can not show ad.22--${isAdAvailable()}--${activity.javaClass.simpleName}")
        if (!isAdAvailable()) {
            logD(TAG1, "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete()
            loadAppOpenAd(false, activity)
            return
        }

        appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isAdsShowing = false
                logD(TAG1, "onAdDismissedFullScreenContent.")
                loadAppOpenAd(false, activity)
                onShowAdCompleteListener.onShowAdComplete()

            }
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isAdsShowing = false
                logD(TAG1, "onAdFailedToShowFullScreenContent: " + adError.message)
                loadAppOpenAd(false, activity)
                onShowAdCompleteListener.onShowAdComplete()

            }
            override fun onAdShowedFullScreenContent() {
                logD(TAG1, "onAdShowedFullScreenContent.")
            }
        }
        with(BaseSharedPreferences(myApplication)) {
            if (mSecondTimePremium == true && mFirstTimePremium == true && mFirstTimeApp >= 3 && !isoutApp!!
            ) {
                if (!isAdsShowing && isAdAvailable()) {
                    isAdsShowing = true
                    logD(
                        TAG1,
                        "NextActivity :Show Ads One--${isAdAvailable()}--${
                            mOpenAdsShow!!
                        }"
                    )
                    appOpenAd!!.show(activity)
                } else {
                    onShowAdCompleteListener.onShowAdComplete()
                }
            } else {
                if (!isAdsShowing && isAdAvailable() && mFirstTimeApp > 4 && isoutApp != true  ) {
                    logD(
                        TAG1,
                        "NextActivity :Show Ads Two--${isAdAvailable()}--${
                            mOpenAdsShow!!
                        }"
                    )
                    isAdsShowing = true
                    appOpenAd!!.show(activity)
                } else {
                    logD(
                        TAG1,
                        "NextActivity :Show Ads Three--${isAdAvailable()}--${
                            mOpenAdsShow!!
                        }"
                    )
                    if (isAdAvailable() && mOpenAdsShow!!) {
                        logD(
                            TAG1,
                            "NextActivity :Show Ads Four--${isAdAvailable()}--${
                                mOpenAdsShow!!
                            }"
                        )
                        isAdsShowing = true
                        appOpenAd!!.show(activity)
                    } else {
                        logD(
                            TAG1,
                            "NextActivity :Can not show ad.--${isAdAvailable()}--${
                                mOpenAdsShow!!
                            }"
                        )
                        onShowAdCompleteListener.onShowAdComplete()
                    }
                }
            }
        }
    }

    interface OnShowAdCompleteListener {
        fun onShowAdComplete()
    }

    fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }
}