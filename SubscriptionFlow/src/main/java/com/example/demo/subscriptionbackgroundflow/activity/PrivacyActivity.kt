package com.example.demo.subscriptionbackgroundflow.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseActivity
import com.example.demo.subscriptionbackgroundflow.helper.isOnline
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyActivity : BaseActivity() {

    private var mPrivacyPolicyActivity: PrivacyActivity?=null
    override fun getActivityContext(): AppCompatActivity {
        return this@PrivacyActivity
    }
    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        mPrivacyPolicyActivity = this@PrivacyActivity
        if (!isOnline) {
            ctOffline!!.visibility=View.VISIBLE
            webView!!.visibility=View.GONE
        }else{
            ctOffline!!.visibility=View.GONE
            webView!!.visibility=View.VISIBLE
        }
        ctInternetDisable?.setOnClickListener {

            if (!isOnline) {
                ctOffline!!.visibility=View.VISIBLE
                webView!!.visibility=View.GONE
                Toast.makeText(
                    this,
                    "Please check internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                startWebView()
                ctOffline!!.visibility=View.GONE
                webView!!.visibility=View.VISIBLE
            }
        }
        startWebView()
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView() {
        val url="https://agneshpipaliya.blogspot.com/2019/03/image-crop-n-wallpaper-changer.html"
        val settings: WebSettings = webView!!.settings
        settings.javaScriptEnabled = true
        pd_mdialog.visibility=View.VISIBLE
        webView?.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (pd_mdialog.isVisible) {
                    pd_mdialog.visibility=View.GONE
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }
        }
        webView?.loadUrl(url)
    }
}