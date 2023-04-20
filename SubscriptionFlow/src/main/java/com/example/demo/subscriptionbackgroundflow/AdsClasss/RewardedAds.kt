package com.example.demo.subscriptionbackgroundflow.AdsClasss

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs

class RewardedAds {


    companion object {

        val TAG = RewardedAds::class.java.simpleName
        var rewardedAd: RewardedAd? = null
        var isSend = false

        var instence: RewardedAds? = null
            get() {

                if (field == null) {
                    field = RewardedAds()
                }
                return field
            }
    }

    fun loadRewardedAd(context: Context,Adx:Boolean?=false) {

        if (rewardedAd != null) {
            logD(TAG, "MEDIUM_RECTANGLE loadRewardedAd:rewardedAd -> Not Null ")
            return
        }
        if (isSend){
            return
        }
        if (!isNetworkAvailable(context)){
            logD(TAG, "MEDIUM_RECTANGLE loadRewardedAd:rewardedAd -> NetworkAvailable->Not ")
            return
        }
        if (BaseSharedPreferences(context).mIS_SUBSCRIBED!!) {
            return
        }


        val s = if (Adx == true){
//            "ca-app-pub-3940256099942544/5224354917"
            AppIDs.instnace?.getGoogleAdxRewardVideo() ?: ""
        }else{
//            "ca-app-pub-3940256099942544/5224354917"
            AppIDs.instnace?.getGoogleRewardVideo() ?: ""
        }
        logD(TAG, "MEDIUM_RECTANGLE  RewardedID ->$s")
//        val s = "ca-app-pub-3940256099942544/5224354917"



        isSend=true
        RewardedAd.load(context,s, AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                logD(TAG, adError?.message)
                isSend=false
                rewardedAd = null
                if (Adx == true){
                    logD(TAG, "MEDIUM_RECTANGLE onAdFailedToLoad:RewardedAd->ADX ${adError.message}")
                }else{
                    logD(TAG, "MEDIUM_RECTANGLE onAdFailedToLoad:RewardedAd->AdMob ${adError.message} ")
                }
//                if (Adx==false){
                    loadRewardedAd(context,false)
//                }
            }

            override fun onAdLoaded(mrewardedAd: RewardedAd) {
                logD(TAG, "Ad was loaded.")
                isSend=false
                rewardedAd = mrewardedAd
                if (Adx == true){
                    logD(TAG, "MEDIUM_RECTANGLE onAdLoaded:RewardedAd->ADX ")
                }else{
                    logD(TAG, "MEDIUM_RECTANGLE onAdLoaded:RewardedAd->AdMob ")
                }
            }
        })

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }
    fun showRewardedAd(context: Context, OnUserEarned: () -> Unit, onError: () -> Unit,onClose: () -> Unit, onPro: () -> Unit, isPro: Boolean = false) {
        val cm = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isAdClosed = true

        if (isPro) {
            onPro()
        } else {
            try {

                if (cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected){
                    val mRewardedAd =rewardedAd
                    logD(TAG, "MEDIUM_RECTANGLE RewardedAd->Showwing ")
                    if (mRewardedAd != null) {
                        mRewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {

                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                logD(TAG, "MEDIUM_RECTANGLE RewardedAd->onAdDismissedFullScreenContent ")
                                if (isAdClosed){
                                    onClose()
                                }else{
                                    OnUserEarned()
                                }
                                isAdsShowing = false
                                if (mRewardedAd == rewardedAd) {
                                    rewardedAd = null
                                }
                                loadRewardedAd(context)
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                onError()
                                logD(TAG, "MEDIUM_RECTANGLE RewardedAd->onAdFailedToShowFullScreenContent->${p0.message} ")

                                isAdsShowing = false
                                if (mRewardedAd == rewardedAd) {
                                    rewardedAd = null
                                }
                                loadRewardedAd(context)
                            }

                            override fun onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent()
                                logD(TAG, "MEDIUM_RECTANGLE RewardedAd->onAdShowedFullScreenContent ")
                                isAdsShowing = true
                            }

                        }
                        mRewardedAd.show(context as Activity, OnUserEarnedRewardListener {
                            OnUserEarned()
                            isAdClosed = false
                            logD(TAG, "MEDIUM_RECTANGLE RewardedAd->OnUserEarned ")

                        })
                    } else {
                        onError()
                        logD(TAG, "MEDIUM_RECTANGLE RewardedAd->Is Null ")
                        if (mRewardedAd == rewardedAd) {
                            rewardedAd = null
                        }
                        isAdsShowing = false
                        loadRewardedAd(context)
                    }
                }else{
                    onError()
                }


            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }

        }


    }


}