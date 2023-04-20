package com.example.demo.testsubscriptionflow

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.example.demo.subscriptionbackgroundflow.AdsClasss.BannerAd.loadBannerAdLEADERBOARD
import com.example.demo.subscriptionbackgroundflow.AdsClasss.NativeAds
import com.example.demo.subscriptionbackgroundflow.AdsClasss.RewardedAds
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionActivity
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionBackgroundActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.example.demo.subscriptionbackgroundflow.ui.BaseSubscriptionActivity

class MainActivity : BaseActivity() {
    override fun getActivityContext(): AppCompatActivity {
        return this
    }
    private var receiver: Receiver? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = Receiver()
        registerReceiver(receiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }
    inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("MainActivity", "onReceive: Ads--${isOnline}")
            if (isOnline) {
                if (!BaseSharedPreferences(this@MainActivity).mIS_SUBSCRIBED!!) {
                    NativeAds().loadNativeAds(
                        this@MainActivity, false,
                        findViewById(R.id.fl_adplaceholder)
                    ) {
                        if (it == 1) {
                        }
                        if (it == 0) {
                        }
                    }
                    RewardedAds.instence!!.loadRewardedAd(this@MainActivity)
                    loadBannerAdLEADERBOARD(
                        context = this@MainActivity,
                        fl_adplaceholder_home = findViewById<FrameLayout>(R.id.fl_adplaceholder_viewphoto),
                        false,
                    )
                } else {

                }
            } else {

            }
        }
    }
    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return;
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Click Back Again To Exit", Toast.LENGTH_SHORT).show();

        Handler(Looper.getMainLooper()).postDelayed(
            { doubleBackToExitPressedOnce = false },
            2000
        )
    }

    fun Subscripcation_Open(view: View) {
        if (BaseSharedPreferences(this).mIS_SUBSCRIBED!!){
            Toast.makeText(this, "You PRO User", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(Intent(this, SubscriptionBackgroundActivity::class.java)
                .putExtra("AppOpen", "SettingsActivity"))
        }

    }

    fun Rewarde_Open(view: View) {
        RewardedAds.instence?.showRewardedAd(this, OnUserEarned = {

        }, onClose = {

        }, onError = {

        }, onPro = {

        },isPro = false)
    }
}