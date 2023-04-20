package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model

import com.google.gson.annotations.SerializedName

data class AppModel(

        @SerializedName("position")
        val position: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("app_link")
        val app_link: String?,

        @SerializedName("image")
        val image: String?,

        @SerializedName("is_trending")
        val is_trending: Boolean?
) {

}