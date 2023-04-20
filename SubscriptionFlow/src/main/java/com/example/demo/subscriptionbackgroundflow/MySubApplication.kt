package com.example.demo.subscriptionbackgroundflow
import android.app.Application
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mHEIGHT
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mWIDTH
import com.example.demo.subscriptionbackgroundflow.helper.logD
import org.jetbrains.anko.displayMetrics

class MySubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        mHEIGHT=(displayMetrics.heightPixels / resources.displayMetrics.density).toInt()
//        mWIDTH=(displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
//        logD("DeviceHeightAndWeight", "height==$mHEIGHT===weight==$mWIDTH")
    }

}