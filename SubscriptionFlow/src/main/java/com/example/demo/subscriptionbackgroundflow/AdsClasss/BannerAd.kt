package com.example.demo.subscriptionbackgroundflow.AdsClasss

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowMetrics
import android.widget.FrameLayout
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.google.android.gms.ads.*
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs
import org.jetbrains.anko.windowManager


object BannerAd {
    val TAG = "BannerAd"
    @SuppressLint("MissingPermission")
    fun loadBannerAdMEDIUM_RECTANGLE(
        context: Context,
        fl_adplaceholder_home: FrameLayout,
        Adx: Boolean = false,
        height: Int
    ) {
        logD("BannerAd", "loadBannerAd: addddd")
        val mIsSubScribe = BaseSharedPreferences(context).mIS_SUBSCRIBED
        if (mIsSubScribe!!) {
            return
        }
        var adView: AdView = AdView(context)
        var s = if (Adx) {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleAdxBanner() ?: ""
        } else {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleBanner()!! ?: ""
        }
        logD(TAG, "MEDIUM_RECTANGLE  BannerID ->$s")
        adView.adUnitId = s
        val AdWidth = getAdWidth(context, adView)
        val adSize = AdSize(AdWidth, height)
//        val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
        adView.adSize = adSize
//        adView.setAdSize(AdSize.MEDIUM_RECTANGLE)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.setAdListener(object : AdListener() {
            override fun onAdClicked() {
                isoutApp = true
            }

            override fun onAdClosed() {
                isoutApp = false
            }

            override fun onAdFailedToLoad(adError: LoadAdError?) {
                if (Adx) {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->ADX ${adError?.message}"
                    )
                } else {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->AdMob ${adError?.message}"
                    )
                }
                if (!Adx) {
                    loadBannerAdMEDIUM_RECTANGLE(
                        context,
                        fl_adplaceholder_home,
                        true,
                        height = height
                    )
                }

            }

            override fun onAdImpression() {}

            override fun onAdLoaded() {
                if (Adx) {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->ADX ")
                } else {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->AdMob ")
                }
//                if (!Adx){
//                    Toast.makeText(context, "Google Ads Loade Banner-> $s", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(context, "ADX Ads Loade Banner-> $s", Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onAdOpened() {
                isoutApp = true
            }
        })
        fl_adplaceholder_home.addView(adView)
    }

    @SuppressLint("MissingPermission")
    fun loadBannerAdLEADERBOARD(
        context: Context,
        fl_adplaceholder_home: FrameLayout,
        Adx: Boolean = false
    ) {
        logD("MEDIUM_RECTANGLE", "loadBannerAd: addddd")
        val mIsSubScribe = BaseSharedPreferences(context).mIS_SUBSCRIBED
        if (mIsSubScribe!!) {
            return
        }

        var adView: AdView = AdView(context)
        var s = if (Adx) {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleAdxBanner() ?: ""
        } else {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleBanner()!! ?: ""
        }
        logD(TAG, "MEDIUM_RECTANGLE  BannerID ->$s")
        adView.adUnitId = s

        val adSize = context.adSize(fl_adplaceholder_home)
//        val adSize = AdSize.getInlineAdaptiveBannerAdSize(context, 320)
        adView.adSize = adSize
//        adView.setAdSize(AdSize.LEADERBOARD)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.setAdListener(object : AdListener() {
            override fun onAdClicked() {
                isoutApp = true
            }

            override fun onAdClosed() {
                isoutApp = false
            }

            override fun onAdFailedToLoad(adError: LoadAdError?) {
                if (Adx) {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->ADX ${adError?.message}"
                    )
                } else {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->AdMob ${adError?.message}"
                    )
                }
//                if (!Adx) {
//                    loadBannerAdLEADERBOARD(context, fl_adplaceholder_home, true)
//                }
            }

            override fun onAdImpression() {}

            override fun onAdLoaded() {
                if (Adx) {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->ADX ")
                } else {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->AdMob ")
                }
            }

            override fun onAdOpened() {
                isoutApp = true
            }
        })
        fl_adplaceholder_home.addView(adView)

    }

    fun loadBannerHelperLEADERBOARD(
        context: Context,
        fl_adplaceholder_home: FrameLayout,
        Adx: Boolean = false,
        onShow: () -> Unit,
        onError: () -> Unit
    ) {
        logD("MEDIUM_RECTANGLE", "loadBannerAd: addddd")
        val mIsSubScribe = BaseSharedPreferences(context).mIS_SUBSCRIBED
        if (mIsSubScribe!!) {
            return
        }

        var adView: AdView = AdView(context)
        var s = if (Adx) {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleAdxBanner() ?: ""
        } else {
//            "ca-app-pub-3940256099942544/6300978111"
            AppIDs.instnace?.getGoogleBanner()!! ?: ""
        }
        logD(TAG, "MEDIUM_RECTANGLE  BannerID ->$s")
        adView.adUnitId = s

        val adSize = context.adSize(fl_adplaceholder_home)
//        val adSize = AdSize.getInlineAdaptiveBannerAdSize(context, 320)
        adView.adSize = adSize
//        adView.setAdSize(AdSize.LEADERBOARD)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.setAdListener(object : AdListener() {
            override fun onAdClicked() {
//                viewline.visibility = View.GONE
                isoutApp = true
            }

            override fun onAdClosed() {
                isoutApp = false
            }

            override fun onAdFailedToLoad(adError: LoadAdError?) {
                if (Adx) {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->ADX ${adError?.message}"
                    )
                } else {
                    logD(
                        "TAG",
                        "MEDIUM_RECTANGLE onAdFailedToLoad: BannerAds ->AdMob ${adError?.message}"
                    )
                }
                onError()
//                if (!Adx) {
//                    loadBannerAdLEADERBOARD(context, fl_adplaceholder_home, true)
//                }
            }

            override fun onAdImpression() {}

            override fun onAdLoaded() {
                if (Adx) {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->ADX ")
                } else {
                    logD("TAG", "MEDIUM_RECTANGLE onAdLoaded: BannerAds ->AdMob ")
                }
                onShow()
            }

            override fun onAdOpened() {
                isoutApp = true
            }
        })
        fl_adplaceholder_home.addView(adView)

    }

    @SuppressLint("NewApi")
    private fun getAdSizeOld(context: Context, adView: AdView): AdSize? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics =
                (context as Activity).windowManager.currentWindowMetrics
            val bounds: Rect = windowMetrics.bounds
            var adWidthPixels: Float = adView.width.toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }
            val density: Float = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()

            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

        } else {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
//             val bounds: Rect = windowMetrics.bounds
            var adWidthPixels: Float = adView.width.toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = displayMetrics.widthPixels.toFloat()
            }
            val density: Float = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()

            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

        }
    }

    private fun Context.adSize(fAdContainer: FrameLayout): AdSize {
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = fAdContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }

    @SuppressLint("NewApi")
    fun getAdWidth(context: Context, adView: AdView): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics =
                (context as Activity).windowManager.currentWindowMetrics
            val bounds: Rect = windowMetrics.bounds
            var adWidthPixels: Float = adView.width.toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }
            val density: Float = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return adWidth
        } else {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

//            val bounds: Rect = windowMetrics.bounds
            var adWidthPixels: Float = adView.width.toFloat()

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = displayMetrics.widthPixels.toFloat()
            }
            val density: Float = context.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return adWidth
        }
    }
}