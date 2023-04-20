package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model

import com.google.gson.annotations.SerializedName

data class DataItem(

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("advertisement")
        val advertisement: List<AdvertisementItem?>? = null,

        @SerializedName("id")
        val id: Int? = null
)