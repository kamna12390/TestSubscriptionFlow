package com.example.demo.subscriptionbackgroundflow.basemodule

import android.content.Intent
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionBackgroundActivity
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.IsOutAppPermission
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp
import com.example.demo.subscriptionbackgroundflow.helper.logD

abstract class BaseActivity : AppCompatActivity() {
    @UiThread
    abstract fun getActivityContext(): AppCompatActivity
    val mActivity: AppCompatActivity
        get() {
            return getActivityContext()
        }

    @Suppress("PropertyName")
    val TAG: String = javaClass.simpleName
    override fun onStart() {
        super.onStart()

        with(BaseSharedPreferences(this)) {
            logD(
                "AppBackgroundFlow",
                "mIS_SUBSCRIBED->$mIS_SUBSCRIBED \n isAdsShowing->$isAdsShowing \n IsOutAppPermission->$IsOutAppPermission \n" +
                        "mActivityOpen->$mActivityOpen \n isoutApp->$isoutApp \n mFirstTimeApp->$mFirstTimeApp \n mOneDay->$mOneDay \n" +
                        "mTwoDay->$mTwoDay"
            )
            if (!mIS_SUBSCRIBED!! && !isAdsShowing && !IsOutAppPermission && mActivityOpen!! && !isoutApp!!) {
                val intent = Intent(
                    mActivity,
                    SubscriptionBackgroundActivity::class.java
                )

                if (mFirstTimeApp == 0) {
                    mFirstTimeApp += 1
                }

                if (mOneDay!!) {
                    if (mFirstTimeApp == 1) {
                        mFirstTimeApp += 1
                        intent.putExtra("AppOpen", "BaseActivity")
                        startActivity(intent)
                        Log.d(
                            TAG,
                            "AppBackgroundFlow->:mFirstTimeApp==1  App Open On Background->${
                                mFirstTimeApp
                            }"
                        )
                    } else if (mFirstTimeApp >= 4 && mFirstTimeApp != 1
                    ) {
                        if (mFirstTimeApp == 4) {
                            mFirstTimeApp += 1
                        } else {
                        }

                    } else {
                        if (mFirstTimeApp == 3 || mFirstTimeApp == 2
                        ) {
                            if (mFirstTimeApp == 3) {
                                mOpenAdsload = true
                            }
                            mFirstTimeApp += 1
                            intent.putExtra("AppOpen", "BaseActivity")
                            startActivity(intent)
                            Log.d(
                                TAG,
                                "AppBackgroundFlow->: else App Open On Background->${mFirstTimeApp}"
                            )

                        } else {
                        }

                    }
                }
                else if (mTwoDay!!) {
                    Log.d(
                        TAG,
                        "AppBackgroundFlow->:Second day App Open On Background->${
                            mFirstTimeApp
                        }"
                    )
                    if (mFirstTimeApp == 1) {
                        mFirstTimeApp += 1
                        if (mFirstTimeApp == 2) {
                            mOpenAdsload = true
                        }
                        intent.putExtra("AppOpen", "BaseActivity")
                        startActivity(intent)
                    } else {
                        mFirstTimeApp += 1
                    }
                } else {}
            } else {
                isoutApp = false
            }
        }

    }
}