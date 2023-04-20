package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model

import com.google.gson.annotations.SerializedName

data class TokenItem(

        @SerializedName("facebook")
        val facebook: String? = null,

        @SerializedName("google")
        val google: String? = null
)
