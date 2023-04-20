package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api

import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.helper.NativeHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(NativeHelper().getAppBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}