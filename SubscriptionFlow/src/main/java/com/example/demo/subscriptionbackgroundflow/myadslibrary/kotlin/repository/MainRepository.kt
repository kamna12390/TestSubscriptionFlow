package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.repository

import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api.ApiHelper

class MainRepository(
        private val mApiHelper: ApiHelper
) {

    suspend fun getData() = mApiHelper.getData()

    suspend fun getIds() = mApiHelper.getIds()

}