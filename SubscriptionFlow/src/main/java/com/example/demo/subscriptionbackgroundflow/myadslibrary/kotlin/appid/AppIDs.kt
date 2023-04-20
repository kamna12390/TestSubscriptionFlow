package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.appid

import android.annotation.SuppressLint
import android.content.Context
import kotlin.random.Random


class AppIDs() {

    fun isUpToDate(): Boolean {
        if (!isTest && getGoogleBanner(0) == "ca-app-pub-3940256099942544/6300978111")
            return false
        return true
    }
    fun getGoogleOpenAds(pos: Int = 0): String {
        val list = Utility().getGoogleOpenAds(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/3419835294"
            } else {
                if (list!!.size == 0) {
                    "ca-app-pub-3940256099942544/3419835294"
                } else
                    list[0]
            }

        }
    }
    fun getAdxOpenAds(pos: Int = 0): String {
        val list = Utility().getAdxOpenAds(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/3419835294"
            } else {
                if (list!!.size == 0) {
                    "ca-app-pub-3940256099942544/3419835294"
                } else
                    list[0]
            }

        }
    }
    fun getGoogleBanner(pos: Int = 0): String {
        val list = Utility().getGoogleBanner(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/6300978111"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/6300978111"
                } else
                    list[0]
            }
        }
    }
    fun getGoogleAdxBanner(pos: Int = 0): String {
        val list = Utility().getGoogleAdxBanner(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/6300978111"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/6300978111"
                } else
                    list[0]
            }
        }
    }

    fun getGoogleInterstitial(pos: Int = 0): String {
        val list = Utility().getGoogleInterstitial(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/1033173712"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/1033173712"
                } else
                    list[0]
            }
        }
    }
    fun getGoogleAdxInterstitial(pos: Int = 0): String {
        val list = Utility().getGoogleAdxInterstitial(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/1033173712"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/1033173712"
                } else
                    list[0]
            }
        }
    }

    fun getGoogleNative(pos: Int = 0): String {
        val list = Utility().getGoogleNative(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/2247696110"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/2247696110"
                } else
                    list[0]
            }

        }
    }
    fun getGoogleAdxNative(pos: Int = 0): String {
        val list = Utility().getGoogleNative(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/2247696110"
            } else {
                if (list.size == 0) {
                    "ca-app-pub-3940256099942544/2247696110"
                } else
                    list[0]
            }

        }
    }

    fun getGoogleRewardVideo(pos: Int = 0): String {
        val list = Utility().getGoogleRewardVideo(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/5224354917"
            } else {
                if (list!!.size == 0) {
                    "ca-app-pub-3940256099942544/5224354917"
                } else
                    list[0]
            }

        }
    }
    fun getGoogleAdxRewardVideo(pos: Int = 0): String {
        val list = Utility().getGoogleAdxRewardVideo(mContext, APP_ID)
        return if (list != null && list!!.size > pos) {
            list[pos]
        } else {
            if (list == null) {
                "ca-app-pub-3940256099942544/5224354917"
            } else {
                if (list!!.size == 0) {
                    "ca-app-pub-3940256099942544/5224354917"
                } else
                    list[0]
            }

        }
    }

    fun getFacebookBanner(pos: Int = 0): String {
        val list = Utility().getFacebookBanner(mContext, APP_ID)
        if (list == null || (list != null && list.size == 0)) {
            return ""
        }
        return if (list!!.size > pos) {
            list[pos]
        } else {
            list[0]
        }
    }

    fun getFacebookInterstitial(pos: Int = 0): String {
        val list = Utility().getFacebookInterstitial(mContext, APP_ID)
        if (list == null || (list != null && list.size == 0)) {
            return ""
        }
        return if (list!!.size > pos) {
            list[pos]
        } else {
            list[0]
        }
    }

    fun getRandomFacebookInterstitial(): String {
        val list = Utility().getFacebookInterstitial(mContext, APP_ID)

        if (list == null || (list != null && list.size == 0)) {
            return ""
        }

        val sharedPreferences = mContext?.getSharedPreferences("AdsId", Context.MODE_PRIVATE)
        val json = sharedPreferences!!.getString("idList", "")
        val mList = if (json != null)
            json.convertList()
        else
            ArrayList<String>()
        return if (mList != null && mList!!.isNotEmpty()) {
            val temp = ArrayList<String>()
            temp.addAll(list!!.toList())
            mList.forEach {
                temp.remove(it)
            }
            if (temp.isEmpty()) {
                val index: Int = Random.nextInt(0, list!!.size)
                temp.add(list!![index])
                sharedPreferences.edit().putString("idList", temp.convertString()).apply()
                list!![index]
            } else {
                val index: Int = Random.nextInt(0, temp.size)
                val li = ArrayList<String>()
                li.addAll(mList)
                if (!li.contains(temp!![index]))
                    li.add(temp!![index])
                sharedPreferences.edit().putString("idList", li.convertString()).apply()
                temp!![index]
            }
        } else {
            if (list!!.isEmpty()) {
                return ""
            }
            val temp = ArrayList<String>()
            val index: Int = Random.nextInt(0, list!!.size)
            temp.add(list!![index])
            sharedPreferences.edit().putString("idList", temp.convertString()).apply()
            list!![index]
        }
    }

    fun getRandomNative(): String {

        val list = Utility().getFacebookNative(mContext, APP_ID)

        if (list == null || (list != null && list.size == 0)) {
            return ""
        }

        val sharedPreferences = mContext?.getSharedPreferences("AdsId", Context.MODE_PRIVATE)
        val json = sharedPreferences!!.getString("idListNative", "")
        val mList = if (json != null)
            json.convertList()
        else
            ArrayList<String>()
        return if (mList != null && mList!!.isNotEmpty()) {
            val temp = ArrayList<String>()
            temp.addAll(list!!.toList())
            mList.forEach {
                temp.remove(it)
            }
            if (temp.isEmpty()) {
                val index: Int = Random.nextInt(0, list!!.size)
                temp.add(list!![index])
                sharedPreferences.edit().putString("idListNative", temp.convertString()).apply()
                list!![index]
            } else {
                val index: Int = Random.nextInt(0, temp.size)
                val li = ArrayList<String>()
                li.addAll(mList)
                if (!li.contains(temp!![index]))
                    li.add(temp!![index])
                sharedPreferences.edit().putString("idListNative", li.convertString()).apply()
                temp!![index]
            }
        } else {
            if (list!!.isEmpty()) {
                return ""
            }
            val temp = ArrayList<String>()
            val index: Int = Random.nextInt(0, list!!.size)
            temp.add(list!![index])
            sharedPreferences.edit().putString("idListNative", temp.convertString()).apply()
            list!![index]
        }
    }

    fun getFacebookNative(pos: Int = 0): String {
        val list = Utility().getFacebookNative(mContext, APP_ID)
        if (list == null || (list != null && list.size == 0)) {
            return ""
        }
        return if (list!!.size > pos) {
            list[pos]
        } else {
            list[0]
        }
    }

    fun getFacebookNativeBanner(pos: Int = 0): String {
        val list = Utility().getFacebookNativeBanner(mContext, APP_ID)
        if (list == null || (list != null && list.size == 0)) {
            return ""
        }
        return if (list!!.size > pos) {
            list[pos]
        } else {
            list[0]
        }
    }


    companion object {
        private var APP_ID = 0
        private var mContext: Context? = null

        @JvmStatic
        var isTest: Boolean = false

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var instnace: AppIDs? = null
            get() {
                if (field == null) {
                    field = AppIDs()
                }
                return field
            }

        @JvmStatic
        fun init(context: Context, id: Int, isTest: Boolean = false) {
            APP_ID = id
            mContext = context
            this.isTest = isTest
            Utility.isTest = isTest
        }

        const val AUTO_WALLPAPER = 1
        const val SOLID_COLOR = 2
        const val NAME_PHOTO = 4
        const val TEST_APP = 5
        const val IMAGE_CROP = 7
    }


}