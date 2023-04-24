package com.example.demo.testsubscriptionflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import com.example.demo.subscriptionbackgroundflow.AdsClasss.AppOpenManager
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionBackgroundActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.example.demo.subscriptionbackgroundflow.ui.SubSplashBaseActivity
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity : SubSplashBaseActivity() {
    private val COUNTER_TIME = 5L
    private var mcountRemaining: Long = 0L
    val TAG: String = javaClass.simpleName
    private var appOpenManager: AppOpenManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        appOpenManager = AppOpenManager(applicationContext)

        createTimer(COUNTER_TIME)
    }

    override fun registerPurchases(
        isSubscribe: Boolean,
        paymentState: Int,
        sku: String,
        orderId: String
    ) {
        Log.d(TAG, "registerPurchases: --=$isSubscribe")
        BaseSharedPreferences(this).mIS_SUBSCRIBED=isSubscribe
    }

    private fun createTimer(seconds: Long) {
        val countDownTimer: CountDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mcountRemaining = millisUntilFinished / 1000 + 1

            }

            override fun onFinish() {
                mcountRemaining = 0

                if (applicationContext == null) {
                    Log.d(TAG, "Application Context Null")
                    openNextActivity()
                    return
                }


                if (isOnline) {
                    mSubscriptionFlow()
                } else {
                    Log.d("TAG", "newFallo: loadOpenAppAd Call--2")
                    mSubscriptionFlow()
                }


            }
        }
        countDownTimer.start()
    }

    private fun mSubscriptionFlow() {
        with(BaseSharedPreferences(this)) {
            if (!mFirstTimePremium!!) {
                logD(TAG, "->First Day Subscription Screen Open")
                mFirstTimePremium = true
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(Date())
                mFirstDate = currentDate
                mOneDay = true
                mFirstTimeApp = 0
//                val mclass="$packageName.${MainActivity::class.simpleName}"
                val mclass =
                    "com.example.demo.testsubscriptionflow.${MainActivity::class.simpleName}"
                startActivity(
                    Intent(this@SplashScreenActivity, SubscriptionBackgroundActivity::class.java)
                        .putExtra("AppOpen", "SplashScreen")
                        .putExtra("mNextActivityIntent", mclass)
                )
                finish()
            } else {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(Date())
                val day = sdf.parse(currentDate)
                if (sdf.parse(mFirstDate!!)!!.before(day)) {
                    if (!mSecondTimePremium!!) {
                        logD(TAG, "->Second Day Subscription Screen Open")
                        mOneDay = false
                        mTwoDay = true
                        mSecondTime = true
                        mFirstTimeApp = 0
                        mSecondTimePremium = true
                        mOpenAdsload=true
//                        val mclass="$packageName.${MainActivity::class.simpleName}"
                        val mclass =
                            "com.example.demo.testsubscriptionflow.${MainActivity::class.simpleName}"
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                SubscriptionBackgroundActivity::class.java
                            )
                                .putExtra("AppOpen", "SplashScreen")
                                .putExtra("mNextActivityIntent", mclass)
                        )
                        finish()
                    }
                    else {
                        //Ads Showing
                        mTwoDay=false
                        mOneDay=false
                        logD(TAG, "->Second Day Ads Showing")
                        mOpenAdsShow = true
                        appOpenManager!!.showAdIfAvailable(this@SplashScreenActivity,
                            object : AppOpenManager.OnShowAdCompleteListener {
                                override fun onShowAdComplete() {
                                    mOpenAdsShow = false
                                    openNextActivity()
                                }
                            })
                    }
                }
                else if (mOneDay!! && sdf.parse(mFirstDate!!)!! == day) {
                    if (mFirstTimePremium!! && !mSecondTime!!) {
                        logD(TAG, "->First Day Second Time Subscription Screen Open")
                        mOneDay = true
                        mSecondTime = true
                        mOpenAdsload=true
//                        val mclass="$packageName.${MainActivity::class.simpleName}"
                        val mclass =
                            "com.example.demo.testsubscriptionflow.${MainActivity::class.simpleName}"
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                SubscriptionBackgroundActivity::class.java
                            )
                                .putExtra("AppOpen", "SplashScreen")
                                .putExtra("mNextActivityIntent", mclass)
                        )
                        finish()
                    }
                    else {
                        //Ads Showing
                        logD(TAG, "->First Day Three Time Ads Showing")
                        mOpenAdsShow = true
                        appOpenManager!!.showAdIfAvailable(this@SplashScreenActivity,
                            object : AppOpenManager.OnShowAdCompleteListener {
                                override fun onShowAdComplete() {
                                    mOpenAdsShow = false
                                    openNextActivity()
                                }
                            })
                    }

                }
                else {
                    //Ads Showing
                    logD(TAG, "->Three Day Ads Showing")
                    mOpenAdsShow = true
                    mTwoDay=false
                    mOneDay=false
                    appOpenManager!!.showAdIfAvailable(this@SplashScreenActivity,
                        object : AppOpenManager.OnShowAdCompleteListener {
                            override fun onShowAdComplete() {
                                mOpenAdsShow = false
                                openNextActivity()
                            }
                        })
                }
            }
        }

    }

    private fun openNextActivity() {

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (!isFinishing) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }, 100)
    }
}