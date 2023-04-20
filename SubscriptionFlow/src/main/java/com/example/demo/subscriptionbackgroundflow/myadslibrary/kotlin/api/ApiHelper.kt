package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getData() = apiService.getAllApp()

    suspend fun getIds() = apiService.getIDs()

}