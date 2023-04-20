package com.example.demo.subscriptionbackgroundflow.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseActivity
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : BaseActivity() {

    private var mPrivacyPolicyActivity: PrivacyPolicyActivity?=null

    private var mWebview: WebView? = null
    private var ctOffline: ConstraintLayout? = null
    private var mInternetDisable: ConstraintLayout? = null
    override fun getActivityContext(): AppCompatActivity {
        return this@PrivacyPolicyActivity
    }
    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        mPrivacyPolicyActivity = this@PrivacyPolicyActivity
        mInternetDisable = findViewById<ConstraintLayout>(R.id.ctInternetDisable)
        mWebview = findViewById(R.id.webView)
        ctOffline = findViewById(R.id.ctOffline)
        if (!isOnline) {
            ctOffline!!.visibility=View.VISIBLE
            mWebview!!.visibility=View.GONE
        }else{
            ctOffline!!.visibility=View.GONE
            mWebview!!.visibility=View.VISIBLE
        }
        mInternetDisable?.setOnClickListener {

            if (!isOnline) {
                ctOffline!!.visibility=View.VISIBLE
                mWebview!!.visibility=View.GONE
                Toast.makeText(
                    this,
                    "Please check internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                startWebView("https://agneshpipaliya.blogspot.com/2019/03/image-crop-n-wallpaper-changer.html")
                ctOffline!!.visibility=View.GONE
                mWebview!!.visibility=View.VISIBLE
            }
        }
        startWebView("https://agneshpipaliya.blogspot.com/2019/03/image-crop-n-wallpaper-changer.html")
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {
        val settings: WebSettings = mWebview!!.settings
        settings.javaScriptEnabled = true
        pd_mdialog.visibility=View.VISIBLE
        mWebview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (pd_mdialog.isVisible) {
                    pd_mdialog.visibility=View.GONE
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }
        }
        mWebview?.loadUrl(url)
    }
}