package com.example.demo.subscriptionbackgroundflow.myadslibrary.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNew {
    private var retrofit: Retrofit? = null
//    external fun getBaseURL(): String?


    @JvmStatic
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://autolivewallpaper.vasundharaapps.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}