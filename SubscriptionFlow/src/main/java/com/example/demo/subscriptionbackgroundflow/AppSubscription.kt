package com.example.demo.subscriptionbackgroundflow

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.demo.subscriptionbackgroundflow.billing.BillingClientLifecycle
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mHEIGHT
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mWIDTH
import com.example.demo.subscriptionbackgroundflow.data.LocalDataSource
import com.example.demo.subscriptionbackgroundflow.db.AppDatabase
import com.example.demo.subscriptionbackgroundflow.executors.AppExecutors
import com.example.demo.subscriptionbackgroundflow.helper.logD
import com.example.demo.subscriptionbackgroundflow.repository.DataRepository
import org.jetbrains.anko.displayMetrics

open class AppSubscription : Application(){

    companion object {
        private var isDebug = false
    }

    override fun onCreate() {
        super.onCreate()
        mHEIGHT =(displayMetrics.heightPixels / resources.displayMetrics.density).toInt()
        mWIDTH =(displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
    }
    private val executors = AppExecutors()
    private val database: AppDatabase
        get() = AppDatabase.getInstance(this)
    private val localDataSource: LocalDataSource
        get() = LocalDataSource.getInstance(executors, database)
    val billingClientLifecycle: BillingClientLifecycle
        get() = BillingClientLifecycle.getInstance(this)
    val repository: DataRepository
        get() = DataRepository.getInstance(localDataSource, billingClientLifecycle)
    fun initDebug(debug : Boolean) {
        isDebug = debug
    }
    fun isDebug() = isDebug
}