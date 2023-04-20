package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model

import com.google.gson.annotations.SerializedName

data class AdvertisementItem(

        @SerializedName("ad_category_name")
        val adCategoryName: String? = null,

        @SerializedName("ad_category_id")
        val adCategoryId: Int? = null,

        @SerializedName("token")
        val token: List<TokenItem>? = null
)
