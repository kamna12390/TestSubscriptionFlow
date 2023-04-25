package com.example.demo.subscriptionbackgroundflow.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.demo.subscriptionbackgroundflow.AdsClasss.AppOpenManager
import com.example.demo.subscriptionbackgroundflow.AdsClasss.InterstitialAds
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SIX_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mHEIGHT
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mIsRevenuCat
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mWIDTH
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBackgroundBinding
import com.example.demo.subscriptionbackgroundflow.helper.*
import com.example.demo.subscriptionbackgroundflow.ui.BaseSubscriptionActivity
import com.example.demo.subscriptionbackgroundflow.viewmodel.SubscriptionBackgroundActivityViewModel
import com.example.demo.subscriptionbackgroundflow.viewmodel.SubscriptionViewModel
import org.jetbrains.anko.displayMetrics

class SubscriptionBackgroundActivity : BaseSubscriptionActivity() {
    lateinit var binding: ActivitySubscriptionBackgroundBinding

    //    var mProgressBar: ProgressDialog? = null
    var mNextIntent: String? = null
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription_background)
        binding.viewmodel = SubscriptionBackgroundActivityViewModel(binding, this,liveDataPeriod,liveDataPrice,object :SubscriptionBackgroundActivityViewModel.IsSelecterdPlan{
            override fun monMonthPlan() {
                onMonthPlan()
            }

            override fun monYearPlan() {
                onYearPlan()
            }

            override fun monBackPress() {
            onBackPressed()
            }

        })

        mNextIntent = intent.getStringExtra("mNextActivityIntent")
        if (BaseSharedPreferences(this).mOpenAdsload!!) {
            AppOpenManager(applicationContext)
        }
        setUI()
    }



    override fun onPurchases(orderId: String, str: String) {
        BaseSharedPreferences(this).mIS_SUBSCRIBED = true
        showBackPress()
    }
    @SuppressLint("SetTextI18n")

    fun setUI(){
        mHEIGHT = (displayMetrics.heightPixels / resources.displayMetrics.density).toInt()
        mWIDTH = (displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
        logD(
            "DeviceHeightAndWeight",
            "height==$mHEIGHT===weight==$mWIDTH---${getNavigationBarHeight()}"
        )
        if (getNavigationBarHeight() >= 20) {
            val newLayoutParams: ConstraintLayout.LayoutParams =
                binding.mCLCenter.layoutParams as ConstraintLayout.LayoutParams
            if (mHEIGHT == 592 && mWIDTH == 360) {
                newLayoutParams.bottomMargin = (getNavigationBarHeight() + 80)
            } else {
                newLayoutParams.bottomMargin = (getNavigationBarHeight() + 30)
            }
            binding.mCLCenter.layoutParams = newLayoutParams
//            logD("IsCheckNavigation", "IsNavigationHegiht->${getNavigationBarHeight()}")
        } else {
            val newLayoutParams: ConstraintLayout.LayoutParams =
                binding.mCLCenter.layoutParams as ConstraintLayout.LayoutParams
            newLayoutParams.bottomMargin = (getNavigationBarHeight())
            binding.mCLCenter.layoutParams = newLayoutParams
        }
        val lp: ConstraintLayout.LayoutParams =
            binding.ivClose.layoutParams as ConstraintLayout.LayoutParams
        if (mHEIGHT==592 && mWIDTH==360) {
            lp.setMargins(0, (getStatusBarHeight() + 31), 0, 0)
        }else{
            lp.setMargins(0, (getStatusBarHeight() + 25), 0, 0)
        }
        binding.ivClose.layoutParams = lp
//        logD("IsCheckStatusBar", "IsStatusBarHeight->${getStatusBarHeight()}")
    }
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
//        window.navigationBarColor=resources.getColor(R.color.nav_color)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                //                        | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        window.decorView.setOnSystemUiVisibilityChangeListener { i ->
            if (i == 0 && View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE //                                 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            //                            | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            or View.SYSTEM_UI_FLAG_IMMERSIVE)
            }
        }
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

    }

    override fun onResume() {
        super.onResume()
        if (BaseSharedPreferences(this).mIS_SUBSCRIBED!!){
            onBackPressed()
        }
    }

    override fun onBackPressed() {

        if (intent.getStringExtra("AppOpen")
                .equals("SplashScreen") || intent.getStringExtra("AppOpen")
                .equals("SettingsActivity") || intent.getStringExtra("AppOpen")
                .equals("BaseActivity")
        ) {
            logD("Subback", "--${BaseSharedPreferences(this).mIS_SUBSCRIBED!!}--${isOnline}")
            if (!BaseSharedPreferences(this).mIS_SUBSCRIBED!! && isOnline) {
                if (InterstitialAds.mInterstitialAd != null) {
                    showAppInterstitialAd()
                } else {

                    if (intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                        NextActivity()
                    } else if (intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                        showBackPress()
                    } else if (intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                        showBackPress()
                    } else {
                        showBackPress()
                    }
                }
            } else {
                if (intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                    NextActivity()
                } else if (intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                    showBackPress()
                } else if (intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                    showBackPress()
                } else {
                    showBackPress()
                }
            }
        }
    }

    fun NextActivity() {
        Constants.isActivitychange=true
        startActivity(Intent(this, Class.forName(mNextIntent)))
//        finish()
    }

    private fun showAppInterstitialAd() {
        InterstitialAds().showInterstitialAd(this, onShow = {
            if (intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                NextActivity()
            } else if (intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                showBackPress()
            } else if (intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                showBackPress()
            } else {
                showBackPress()
            }
        }, onError = {

            showBackPress()
        }, onPro = {
            showBackPress()
        }, BaseSharedPreferences(this).mIS_SUBSCRIBED!!)
    }

    private fun showBackPress() {
        Constants.isActivitychange=true
        super.onBackPressed()

    }
}