package com.example.demo.subscriptionbackgroundflow.AdsClasss

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs

class InterstitialAds {

    companion object {
        val TAG = "InterstitialAdsHelper"
        val TAG1 = "ShowInterstitialAd"
        var mInterstitialAd: InterstitialAd? = null
        var isSend=false
        var instence: InterstitialAds? = null
            get() {

                if (field == null) {
                    field = InterstitialAds()
                }
                return field
            }
    }

    fun loadInterstitialAd(context: Context, Adx: Boolean? = false) {
        if (mInterstitialAd != null) {
            return
        }
        if (isSend){
            return
        }
        if (BaseSharedPreferences(context).mIS_SUBSCRIBED!! || !context.isOnline) {
            return
        }

        var s = if (Adx == true) {
//            "ca-app-pub-3940256099942544/1033173712"
            AppIDs.instnace?.getGoogleAdxInterstitial() ?: ""
        } else {
//            "ca-app-pub-3940256099942544/1033173712"
            AppIDs.instnace?.getGoogleInterstitial() ?: ""
        }
        logD(TAG, "MEDIUM_RECTANGLE  InterstitialID ->$s")
        isSend=true
        InterstitialAd.load(
            context,
            s,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    isSend=false
                    mInterstitialAd = null
                    if (Adx == true) {
                        logD(TAG,"MEDIUM_RECTANGLE onAdFailedToLoad:interstitialAd->ADX ->${adError}")
                    } else {
                        logD(TAG,"MEDIUM_RECTANGLE onAdFailedToLoad:interstitialAd->AdMob ->${adError}")
                    }

                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isSend=false
                    mInterstitialAd = interstitialAd
                    if (Adx == true) {
                        logD(TAG,"MEDIUM_RECTANGLE onAdLoaded:interstitialAd->ADX ")
                    } else {
                        logD(TAG,"MEDIUM_RECTANGLE onAdLoaded:interstitialAd->AdMob ")
                    }
                }
            })

    }

    fun showInterstitialAd(
        context: Context,
        onShow: () -> Unit,
        onError: () -> Unit,
        onPro: () -> Unit,
        isPro: Boolean = false
    ) {

        if (isPro) {
            onPro()
        } else {
            try {
                if (mInterstitialAd != null) {
                    mInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback() {

                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()

                                isAdsShowing = false
//                            if (mInterstitialAd == interstitialAd1) {
                                mInterstitialAd = null
                                loadInterstitialAd(context)

//                            }
                                onShow()
                                logD(TAG1,"Show Interstitial Ads")

                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                logD(TAG1,"Show Interstitial OnError->${p0.message}")
                                isAdsShowing = false
                                mInterstitialAd = null
                                onError()
                            }

                            override fun onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent()
                                isAdsShowing = true
                            }

                        }
                    mInterstitialAd!!.show(context as Activity)
                } else {
                    logD(TAG1,"Show Interstitial Not Load")

                    mInterstitialAd = null
                    isAdsShowing = false
                    loadInterstitialAd(context)
                    onError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }

        }


    }
}