package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.convertList() : List<String>? {
    val myType = object : TypeToken<ArrayList<String>>() {}.type
    return Gson().fromJson<List<String>>(this, myType)
}

fun List<String>.convertString() : String {
    return Gson().toJson(this)
}