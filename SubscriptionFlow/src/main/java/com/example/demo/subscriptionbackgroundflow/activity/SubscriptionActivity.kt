package com.example.demo.subscriptionbackgroundflow.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.crop.photo.image.resize.cut.tools.subscripction.ProductPurchaseHelper
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mHEIGHT
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mWIDTH
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBinding
import com.example.demo.subscriptionbackgroundflow.helper.*
import com.example.demo.subscriptionbackgroundflow.ui.BaseSubscriptionActivity
import com.example.demo.subscriptionbackgroundflow.viewmodel.SubscriptionViewModel

class SubscriptionActivity : BaseSubscriptionActivity() , ProductPurchaseHelper.ProductPurchaseListener{
    lateinit var binding: ActivitySubscriptionBinding

    companion object {
        var plans = Constants.PREMIUM_SKU
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()

        logD("DeviceHeightAndWeight", "height==${mHEIGHT}===weight==${mWIDTH}")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)
        binding.viewmodel = SubscriptionViewModel(binding, this,liveDataPeriod,liveDataPrice,subscriptionManager,object :SubscriptionViewModel.IsSelecterdPlan{
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
        setUI()
    }


    fun setUI() {
        if (getNavigationBarHeight() >= 20) {
            val newLayoutParams: ConstraintLayout.LayoutParams =
                binding.mCLPriceLayer.layoutParams as ConstraintLayout.LayoutParams
            if (mHEIGHT == 592 && mWIDTH == 360) {
                newLayoutParams.bottomMargin = (getNavigationBarHeight() + 83)
            } else {
                newLayoutParams.bottomMargin = (getNavigationBarHeight() + 37)
            }

            binding.mCLPriceLayer.layoutParams = newLayoutParams
        } else {
            val newLayoutParams: ConstraintLayout.LayoutParams =
                binding.mCLPriceLayer.layoutParams as ConstraintLayout.LayoutParams
            newLayoutParams.bottomMargin = (getNavigationBarHeight())
            binding.mCLPriceLayer.layoutParams = newLayoutParams
        }
        val lp: ConstraintLayout.LayoutParams =
            binding.imgClose.layoutParams as ConstraintLayout.LayoutParams
        if (mHEIGHT == 592 && mWIDTH == 360) {
            lp.setMargins(0, (getStatusBarHeight() + 31), 0, 0)
        } else {
            lp.setMargins(0, (getStatusBarHeight() + 25), 0, 0)
        }
        binding.imgClose.layoutParams = lp
        logD("IsCheckStatusBar", "IsStatusBarHeight->${getStatusBarHeight()}")
    }

    override fun onPurchases(orderId: String, str: String) {
        BaseSharedPreferences(this).mIS_SUBSCRIBED = true
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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

    override fun onPurchasedSuccess(purchase: Purchase) {
        BaseSharedPreferences(this).mIS_SUBSCRIBED = true
        onBackPressed()
    }

    override fun onProductAlreadyOwn() {
        BaseSharedPreferences(this).mIS_SUBSCRIBED = true
        onBackPressed()
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        TODO("Not yet implemented")
    }

    override fun onBillingKeyNotFound(productId: String) {
        TODO("Not yet implemented")
    }
}