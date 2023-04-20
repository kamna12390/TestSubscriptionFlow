package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid

import android.content.Context
import android.util.Log
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.AppDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class Utility {


    companion object {

        var isTest = false

        private var GOOGLE_BANNER: Array<String>? = null
        private var GOOGLE_Adx_BANNER: Array<String>? = null
        private var GOOGLE_INTERSTITIAL: Array<String>? = null
        private var GOOGLE_Adx_INTERSTITIAL: Array<String>? = null
        private var GOOGLE_NATIVE: Array<String>? = null
        private var GOOGLE_Adx_NATIVE: Array<String>? = null
        private var GOOGLE_REWARD_VIDEO: Array<String>? = null
        private var GOOGLE_Adx_REWARD_VIDEO: Array<String>? = null
        private var ADX_OPEN_ADS: Array<String>? = null
        private var GOOGLE_OPEN_ADS: Array<String>? = null

        private var FACEBOOK_BANNER: Array<String>? = null
        private var FACEBOOK_INTERSTITIAL: Array<String>? = null
        private var FACEBOOK_NATIVE: Array<String>? = null
        private var FACEBOOK_NATIVE_BANNER: Array<String>? = null

    }
    fun getGoogleOpenAds(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_OPEN_ADS == null && !isTest) {
            GOOGLE_OPEN_ADS = getId(context, appId, 7, IdType.GOOGLE)
            if(GOOGLE_OPEN_ADS != null && GOOGLE_OPEN_ADS!!.size == 0){
                GOOGLE_OPEN_ADS = null
            }
            if (!isTest)
                GOOGLE_OPEN_ADS
            else {
                if (GOOGLE_OPEN_ADS != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_OPEN_ADS!!.size) {
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/3419835294")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_OPEN_ADS
            } else {
                if (GOOGLE_OPEN_ADS != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_OPEN_ADS!!.size) {
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/3419835294")
                }
            }
        }

    }
    fun getAdxOpenAds(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (ADX_OPEN_ADS == null && !isTest) {
            ADX_OPEN_ADS = getId(context, appId, 10, IdType.GOOGLE)
            if(ADX_OPEN_ADS != null && ADX_OPEN_ADS!!.size == 0){
                ADX_OPEN_ADS = null
            }
            if (!isTest)
                ADX_OPEN_ADS
            else {
                if (ADX_OPEN_ADS != null) {
                    val list = ArrayList<String>()
                    for (i in 0..ADX_OPEN_ADS!!.size) {
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/3419835294")
                }
            }
        } else {
            if (!isTest) {
                ADX_OPEN_ADS
            } else {
                if (ADX_OPEN_ADS != null) {
                    val list = ArrayList<String>()
                    for (i in 0..ADX_OPEN_ADS!!.size) {
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/3419835294")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/3419835294")
                }
            }
        }

    }
    fun getGoogleBanner(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_BANNER == null && !isTest) {
            GOOGLE_BANNER = getId(context, appId, 1, IdType.GOOGLE)
            if(GOOGLE_BANNER != null && GOOGLE_BANNER!!.size == 0){
                GOOGLE_BANNER = null
            }
            if (!isTest)
                GOOGLE_BANNER
            else {
                if (GOOGLE_BANNER != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_BANNER!!.size) {
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/6300978111")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_BANNER
            } else {
                if (GOOGLE_BANNER != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_BANNER!!.size) {
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/6300978111")
                }
            }
        }

    }
    fun getGoogleAdxBanner(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_Adx_BANNER == null && !isTest) {
            GOOGLE_Adx_BANNER = getId(context, appId, 12, IdType.GOOGLE)
            if(GOOGLE_Adx_BANNER != null && GOOGLE_Adx_BANNER!!.size == 0){
                GOOGLE_Adx_BANNER = null
            }
            if (!isTest)
                GOOGLE_Adx_BANNER
            else {
                if (GOOGLE_Adx_BANNER != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_BANNER!!.size) {
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/6300978111")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_Adx_BANNER
            } else {
                if (GOOGLE_Adx_BANNER != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_BANNER!!.size) {
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/6300978111")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/6300978111")
                }
            }
        }

    }


    fun getGoogleInterstitial(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_INTERSTITIAL == null && !isTest) {
            Log.d("TAHS", "getGoogleInterstitial: ")
            GOOGLE_INTERSTITIAL = getId(context, appId, 2, IdType.GOOGLE)
            Log.d("TAHS", "getGoogleInterstitial: ${GOOGLE_INTERSTITIAL!!.size}")
            if(GOOGLE_INTERSTITIAL != null && GOOGLE_INTERSTITIAL!!.size == 0){
                GOOGLE_INTERSTITIAL = null
            }
            Log.d("TAHS", "getGoogleInterstitial: ${GOOGLE_INTERSTITIAL}")
            if (!isTest)
                GOOGLE_INTERSTITIAL
            else {
                if (GOOGLE_INTERSTITIAL != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_INTERSTITIAL!!.size) {
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/1033173712")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_INTERSTITIAL
            } else {
                if (GOOGLE_INTERSTITIAL != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_INTERSTITIAL!!.size) {
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/1033173712")
                }
            }
        }

    }
    fun getGoogleAdxInterstitial(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_Adx_INTERSTITIAL == null && !isTest) {
            Log.d("TAHS", "getGoogleInterstitial: ")
            GOOGLE_Adx_INTERSTITIAL = getId(context, appId, 8, IdType.GOOGLE)
            Log.d("TAHS", "getGoogleInterstitial: ${GOOGLE_Adx_INTERSTITIAL!!.size}")
            if(GOOGLE_Adx_INTERSTITIAL != null && GOOGLE_Adx_INTERSTITIAL!!.size == 0){
                GOOGLE_Adx_INTERSTITIAL = null
            }
            Log.d("TAHS", "getGoogleInterstitial: ${GOOGLE_Adx_INTERSTITIAL}")
            if (!isTest)
                GOOGLE_Adx_INTERSTITIAL
            else {
                if (GOOGLE_Adx_INTERSTITIAL != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_INTERSTITIAL!!.size) {
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/1033173712")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_Adx_INTERSTITIAL
            } else {
                if (GOOGLE_Adx_INTERSTITIAL != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_INTERSTITIAL!!.size) {
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/1033173712")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/1033173712")
                }
            }
        }

    }
    
    fun getGoogleNative(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_NATIVE == null && !isTest) {
            GOOGLE_NATIVE = getId(context, appId, 3, IdType.GOOGLE)
            if(GOOGLE_NATIVE != null && GOOGLE_NATIVE!!.size == 0){
                GOOGLE_NATIVE = null
            }
            if (!isTest)
                GOOGLE_NATIVE
            else {
                if (GOOGLE_NATIVE != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_NATIVE!!.size) {
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/2247696110")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_NATIVE
            } else {
                if (GOOGLE_NATIVE != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_NATIVE!!.size) {
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/2247696110")
                }
            }
        }

    }
    fun getGoogleAdxNative(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_Adx_NATIVE == null && !isTest) {
            GOOGLE_Adx_NATIVE = getId(context, appId, 9, IdType.GOOGLE)
            if(GOOGLE_Adx_NATIVE != null && GOOGLE_Adx_NATIVE!!.size == 0){
                GOOGLE_Adx_NATIVE = null
            }
            if (!isTest)
                GOOGLE_Adx_NATIVE
            else {
                if (GOOGLE_Adx_NATIVE != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_NATIVE!!.size) {
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/2247696110")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_Adx_NATIVE
            } else {
                if (GOOGLE_Adx_NATIVE != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_NATIVE!!.size) {
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/2247696110")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/2247696110")
                }
            }
        }

    }

    fun getGoogleRewardVideo(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_REWARD_VIDEO == null && !isTest) {
            GOOGLE_REWARD_VIDEO = getId(context, appId, 4, IdType.GOOGLE)
            if(GOOGLE_REWARD_VIDEO != null && GOOGLE_REWARD_VIDEO!!.size == 0){
                GOOGLE_REWARD_VIDEO = null
            }
            if (!isTest)
                GOOGLE_REWARD_VIDEO
            else {
                if (GOOGLE_REWARD_VIDEO != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_REWARD_VIDEO!!.size) {
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/5224354917")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_REWARD_VIDEO
            } else {
                if (GOOGLE_REWARD_VIDEO != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_REWARD_VIDEO!!.size) {
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/5224354917")
                }
            }
        }

    }
    fun getGoogleAdxRewardVideo(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (GOOGLE_Adx_REWARD_VIDEO == null && !isTest) {
            GOOGLE_Adx_REWARD_VIDEO = getId(context, appId, 11, IdType.GOOGLE)
            if(GOOGLE_Adx_REWARD_VIDEO != null && GOOGLE_Adx_REWARD_VIDEO!!.size == 0){
                GOOGLE_Adx_REWARD_VIDEO = null
            }
            if (!isTest)
                GOOGLE_Adx_REWARD_VIDEO
            else {
                if (GOOGLE_Adx_REWARD_VIDEO != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_REWARD_VIDEO!!.size) {
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/5224354917")
                }
            }
        } else {
            if (!isTest) {
                GOOGLE_Adx_REWARD_VIDEO
            } else {
                if (GOOGLE_Adx_REWARD_VIDEO != null) {
                    val list = ArrayList<String>()
                    for (i in 0..GOOGLE_Adx_REWARD_VIDEO!!.size) {
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    if(list.size == 0){
                        list.add("ca-app-pub-3940256099942544/5224354917")
                    }
                    list.toTypedArray()
                } else {
                    arrayOf("ca-app-pub-3940256099942544/5224354917")
                }
            }
        }

    }

    fun getFacebookBanner(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (FACEBOOK_BANNER == null) {
            FACEBOOK_BANNER = getId(context, appId, 1, IdType.FACEBOOK)
            if(FACEBOOK_BANNER == null)
                arrayOf("")
            else
                FACEBOOK_BANNER
        } else {
            FACEBOOK_BANNER
        }
    }

    fun getFacebookInterstitial(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (FACEBOOK_INTERSTITIAL == null) {
            FACEBOOK_INTERSTITIAL = getId(context, appId, 2, IdType.FACEBOOK)
            if(FACEBOOK_INTERSTITIAL == null)
                arrayOf("")
            else
                FACEBOOK_INTERSTITIAL
        } else {
            FACEBOOK_INTERSTITIAL
        }
    }

    fun getFacebookNative(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (FACEBOOK_NATIVE == null) {
            FACEBOOK_NATIVE = getId(context, appId, 3, IdType.FACEBOOK)
            if(FACEBOOK_NATIVE == null)
                arrayOf("")
            else
                FACEBOOK_NATIVE
        } else {
            FACEBOOK_NATIVE
        }
    }

    fun getFacebookNativeBanner(context: Context?, appId: Int): Array<String>? = runBlocking {
        if (FACEBOOK_NATIVE_BANNER == null) {
            FACEBOOK_NATIVE_BANNER = getId(context, appId, 5, IdType.FACEBOOK)
            if(FACEBOOK_NATIVE_BANNER == null)
                arrayOf("")
            else
                FACEBOOK_NATIVE_BANNER
        } else {
            FACEBOOK_NATIVE_BANNER
        }
    }

    private suspend fun getId(context: Context?, appId: Int, category_id: Int, type: IdType): Array<String> {
        val db = AppDatabase.getInstance(context!!)
        var str: MutableList<String> = ArrayList()
        return coroutineScope {

            val list = db.IdDao().getAppId(appId)

            list?.forEach {
                it?.let {
                    if (it.category_id == category_id) {
                        if (type == IdType.GOOGLE) {
                            str.add(it.google ?: "")
                        }else {
                            str.add(it.facebook ?: "")
                        }
                    }
                }
            }
            str.toTypedArray()
        }
    }

}