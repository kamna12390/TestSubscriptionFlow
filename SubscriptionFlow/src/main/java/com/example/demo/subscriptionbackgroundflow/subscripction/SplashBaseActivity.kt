package com.example.demo.subscriptionbackgroundflow.subscripction

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.example.demo.subscriptionbackgroundflow.ui.SubSplashBaseActivity


abstract class SplashBaseActivity : SubSplashBaseActivity()  {

    //Context Object to access child class
    protected lateinit var mContext: Context
    //Activity Object to access child class
    protected lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this@SplashBaseActivity
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        TAG = this::class.simpleName

        bindCallbacks()

        bindAction()
    }

    /**
     * this method use to initialize call backs
     */
    abstract fun bindCallbacks()

    /**
     * this method call after bind Action
     * to perform action
     */
    abstract fun bindAction()

    companion object {
        var TAG = this::class.simpleName
    }
}