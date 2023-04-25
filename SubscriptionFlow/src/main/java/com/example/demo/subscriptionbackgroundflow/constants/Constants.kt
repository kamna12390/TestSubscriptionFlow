package com.example.demo.subscriptionbackgroundflow.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import com.google.android.gms.ads.nativead.NativeAd

@SuppressLint("StaticFieldLeak")
object Constants {
    var PREMIUM_SIX_SKU = ""

    var BASIC_SKU = ""

    var PREMIUM_SKU = ""

    var Purchase_ID = ""

    internal var isDebugMode: Boolean = false
    internal var isTestMode: Boolean = false
    var isoutApp: Boolean? = false
    var unNativeAd: NativeAd? = null
    var IsOutAppPermission:Boolean=false
    var isAdsShowing: Boolean = false
    var packagerenlist: ArrayList<PackagesRen>? = null
    var mPremium_True_Icon:Drawable?=null
    var mBasic_Line_Icon:Drawable?=null
    var mClose_Icon:Drawable?=null
    var mPremium_Button_Icon:Drawable?=null
    var mPremium_CardSelected_Icon:Drawable?=null
    var mPremium_Cardunselected_Icon:Drawable?=null
    var mAppIcon:Drawable?=null
    var mAppName:String?=null
    var currentActivity: Activity? = null
    var mNew_NativeAdsLayout: Int? = null
    var mOld_NativeAdsLayout: Int? = null
    var mIsRevenuCat: Boolean? = false
     var mPremiumLine: ArrayList<LineWithIconModel>?=null
     var mPremiumScreenLine: ArrayList<LineWithIconModel>?=null
    var mHEIGHT:Int?=0
    var mWIDTH:Int?=0
    data class PackagesRen(
        val originalPrice: String,
        val freeTrialPeriod: String,
        val title: String,
        val price: String,
        val description: String,
        val subscriptionPeriod: String,
        val sku: String
    ) {
        override fun toString(): String {
            return "PackagesRen(originalPrice='$originalPrice', freeTrialPeriod='$freeTrialPeriod', title='$title', price='$price', description='$description', subscriptionPeriod='$subscriptionPeriod', sku='$sku')"
        }
    }
    data class LineWithIconModel(
        val mLine:String,
        val mLineRight:Boolean,
        val mIconLine:Drawable
    )
//            var PREMIUM_SIX_SKU = "subscribe_weekly_imagecrop"
//
//    var BASIC_SKU = "subscribe_monthly_imagecrop"
//
//    var PREMIUM_SKU = "subscribe_yearly_imagecrop"
//
//    var IMAGE_CROP = "goog_IuztdnsvmYVjRXaHMiaDmiyOOmN"
}