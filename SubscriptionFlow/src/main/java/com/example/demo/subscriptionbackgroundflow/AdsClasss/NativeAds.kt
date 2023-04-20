package com.example.demo.subscriptionbackgroundflow.AdsClasss

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mNativeAdsLayout
import com.example.demo.subscriptionbackgroundflow.constants.Constants.unNativeAd
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid.AppIDs
import org.jetbrains.anko.layoutInflater

class NativeAds {
    private val TAG = NativeAds::class.java.simpleName

    var isSend = false
    fun loadNativeAds(context: Context, Adx:Boolean?=false, fAdContainer: FrameLayout, listener: (Int) -> Unit) {
        var s =if (Adx==true){
//            "ca-app-pub-3940256099942544/2247696110"
            AppIDs.instnace?.getGoogleAdxNative()
        }else{
//            "ca-app-pub-3940256099942544/2247696110"
            AppIDs.instnace?.getGoogleNative()
        }
        logD(TAG, "MEDIUM_RECTANGLE  NativeID ->$s")
        if (isSend){
            logD(TAG, "loadNativeAds: isSend->$isSend")
            return
        }
//        image
//        val s = "ca-app-pub-3940256099942544/2247696110"
//        video
//        s = "ca-app-pub-3940256099942544/1044960115"
//        val adView = context.layoutInflater.inflate(R.layout.layout_native_ads_test, null) as NativeAdView
        val adView = context.layoutInflater.inflate(mNativeAdsLayout!!, null) as NativeAdView
        if (!context.isOnline){
            unNativeAd = null
            isSend = false
            listener(0)
        }
        if (unNativeAd == null) {
            logD(TAG, "loadOfflineNativeAdvance: NativeAd load Loading..")
            val nativeAdOptions = NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()
            val builder = AdLoader.Builder(context, s).withNativeAdOptions(nativeAdOptions)
//            val builder = AdLoader.Builder(context, s)
                .forNativeAd { nativeAd ->
                    populateNativeAdView(nativeAd, adView)
                    fAdContainer.removeAllViews()
                    fAdContainer.addView(adView)
                }

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    listener(0)
                    isSend = false
                    if (Adx == true){
                        logD(TAG, "MEDIUM_RECTANGLE onAdFailedToLoad:nativeAd->ADX ${error.message}")
                    }else{
                        logD(TAG, "MEDIUM_RECTANGLE onAdFailedToLoad:nativeAd->AdMob ${error.message} ")
                    }
//                    if (Adx==false){
//                        loadOfflineNativeHomeAdvanceBig(context,true, fAdContainer,listener)
//                    }
                }
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    listener(1)
                    isSend = false
                    if (Adx == true){
                        logD(TAG, "MEDIUM_RECTANGLE onAdLoaded:nativeAd->ADX ")
                    }else{
                        logD(TAG, "MEDIUM_RECTANGLE onAdLoaded:nativeAd->AdMob ")
                    }
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    logD(TAG, "MEDIUM_RECTANGLE onAdClicked:Click Ads ")
                    unNativeAd = null
                    isSend = false
                    isoutApp=true
                    try {
                        loadNativeAds(context,false, fAdContainer, listener)
                    } catch (e: Exception) {
                    }
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
            isSend = true
        } else {
            logD(TAG, "MEDIUM_RECTANGLE:Ads Already Load Set Ads ")
            populateNativeAdView(unNativeAd!!, adView)
            fAdContainer.removeAllViews()
            fAdContainer.addView(adView)
            listener(1)
        }


    }
    fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        Log.i(TAG, Throwable().stackTrace[0].methodName)
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        //  unNativeAd?.destroy()
        unNativeAd = nativeAd
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
//        adView.imageView = adView.findViewById(R.id.ad_image)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
//        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
//        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        if (nativeAd.mediaContent != null && adView.mediaView != null) {
            logD(TAG, "populateNativeAdView: ${nativeAd.mediaContent}")
            adView.mediaView!!.setMediaContent(nativeAd.mediaContent!!)
        }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null && adView.bodyView != null) {
            adView.bodyView!!.visibility = View.GONE
        } else if (adView.bodyView != null) {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null && adView.callToActionView != null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else if (adView.callToActionView != null) {
            adView.callToActionView!!.visibility = View.VISIBLE
//            (adView.callToActionView as Button).text = nativeAd.callToAction
            (adView.callToActionView as AppCompatTextView).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null && adView.iconView != null) {
            adView.iconView!!.visibility = View.GONE
        } else if (adView.iconView != null) {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }
//        if (nativeAd.images == null && adView.imageView != null) {
//            adView.imageView!!.visibility = View.GONE
//        } else if (adView.imageView != null) {
//            (adView.imageView as ImageView).setImageDrawable(
//                nativeAd.icon!!.drawable
//            )
//            adView.imageView!!.visibility = View.VISIBLE
//        }

//        if (nativeAd.price == null && adView.priceView != null) {
//            adView.priceView!!.visibility = View.INVISIBLE
//        } else if (adView.priceView != null) {
//            adView.priceView!!.visibility = View.VISIBLE
//            (adView.priceView as TextView).text = nativeAd.price
//        }


//        if (nativeAd.store == null && adView.storeView != null) {
//            adView.storeView!!.visibility = View.INVISIBLE
//        } else if (adView.storeView != null) {
//            adView.storeView!!.visibility = View.VISIBLE
//            (adView.storeView as TextView).text = nativeAd.store
//        }
        
        // for custom view
        if (adView.priceView != null) {
            adView.priceView!!.visibility = View.GONE
        }
        if (adView.storeView != null) {
            adView.storeView!!.visibility = View.GONE
        }

        if (nativeAd.starRating == null && adView.starRatingView != null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else if (adView.starRatingView != null) {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null && adView.advertiserView != null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else if (adView.advertiserView != null) {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)

    }
}