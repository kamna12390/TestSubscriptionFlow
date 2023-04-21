package com.example.demo.subscriptionbackgroundflow.viewmodel

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.demo.subscriptionbackgroundflow.constants.Constants.BASIC_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.Purchase_ID
import com.example.demo.subscriptionbackgroundflow.constants.Constants.IsOutAppPermission
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SIX_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isAdsShowing
import com.example.demo.subscriptionbackgroundflow.constants.Constants.isoutApp


public class Config() : Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
//        PREMIUM_SIX_SKU = parcel.readString().toString()
//        BASIC_SKU = parcel.readString().toString()
//        PREMIUM_SKU = parcel.readString().toString()
//        Purchase_ID = parcel.readString().toString()
//        isoutApp = parcel.readBoolean()
//        IsOutAppPermission = parcel.readBoolean()
//        isAdsShowing = parcel.readBoolean()
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(PREMIUM_SIX_SKU)
        dest.writeString(BASIC_SKU)
        dest.writeString(PREMIUM_SKU)
        dest.writeString(Purchase_ID)
        dest.writeBoolean(isoutApp!!)
        dest.writeBoolean(IsOutAppPermission)
        dest.writeBoolean(isAdsShowing)
    }

    companion object CREATOR : Parcelable.Creator<Config> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Config {
            return Config(parcel)
        }

        override fun newArray(size: Int): Array<Config?> {
            return arrayOfNulls(size)
        }
    }

    fun setPREMIUM_SIX_SKU(string: String){
//        PREMIUM_SIX_SKU=string
    }

    fun getPREMIUM_SIX_SKU():String{
        return PREMIUM_SIX_SKU
    }

    fun setBASIC_SKU(string: String){
//        BASIC_SKU=string
    }
    fun getBASIC_SKU(): String {
        return BASIC_SKU
    }
    fun setPREMIUM_SKU(string: String){
//        PREMIUM_SKU=string
    }
    fun getPREMIUM_SKU(): String {
        return PREMIUM_SKU
    }

    fun setPurchase_ID(string: String){
//        Purchase_ID=string
    }
   fun getPurchase_ID(): String {
       return Purchase_ID
   }

    fun setIsOutApp(boolean: Boolean){
        isoutApp=boolean
    }
    fun getIsOutApp(): Boolean? {
        return isoutApp
    }

    fun setIsOutAppPermission(boolean: Boolean){
        IsOutAppPermission=boolean
    }

    fun getIsOutAppPermission():Boolean{
        return IsOutAppPermission
    }
}