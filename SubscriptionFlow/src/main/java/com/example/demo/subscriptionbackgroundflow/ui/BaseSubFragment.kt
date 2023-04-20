package com.example.demo.subscriptionbackgroundflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demo.subscriptionbackgroundflow.AppSubscription
import com.example.demo.subscriptionbackgroundflow.BuildConfig
import com.example.demo.subscriptionbackgroundflow.manager.PreferencesKeys
import com.example.demo.subscriptionbackgroundflow.manager.SubscriptionManager

open class BaseSubFragment : Fragment() {

    protected lateinit var subscriptionManager: SubscriptionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscriptionManager = SubscriptionManager(requireContext())

    }


    /**
     * return result for subscribe
     */
    fun isSubscribe() = if (AppSubscription().isDebug() && BuildConfig.DEBUG)
        true
    else subscriptionManager.geBoolean(PreferencesKeys.SUBSCRIBE, false)
}