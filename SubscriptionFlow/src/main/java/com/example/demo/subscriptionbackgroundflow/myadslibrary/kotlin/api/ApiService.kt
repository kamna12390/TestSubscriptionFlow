package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api

import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.ResponseID
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.ResponseModel
import retrofit2.http.GET

interface ApiService {

    @GET("more_app")
    suspend fun getAllApp(): ResponseModel

    @GET("advertisement_list")
    suspend fun getIDs() : ResponseID

}
