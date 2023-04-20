package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseID(

        @SerializedName("ResponseCode")
        val status: String?,

        @SerializedName("ResponseMessage")
        val message: String?,

        @SerializedName("data")
        val data: ArrayList<DataItem>?
)