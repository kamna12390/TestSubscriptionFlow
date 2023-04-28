package com.example.demo.subscriptionbackgroundflow.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crop.photo.image.resize.cut.tools.subscripction.ProductPurchaseHelper.getProductInfo
import com.example.demo.subscriptionbackgroundflow.activity.PrivacyActivity
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionActivity.Companion.plans
import com.example.demo.subscriptionbackgroundflow.activity.TermsActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SIX_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mIsRevenuCat
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumScreenLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_CardSelected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_Cardunselected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.packagerenlist
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBinding
import com.example.demo.subscriptionbackgroundflow.helper.*
import com.example.demo.subscriptionbackgroundflow.manager.PreferencesKeys
import com.example.demo.subscriptionbackgroundflow.manager.SubscriptionManager


class SubscriptionViewModel(
    var binding: ActivitySubscriptionBinding,
    var mActivity: AppCompatActivity,
    var liveDataPeriod: MutableLiveData<HashMap<String, String>>,
    var liveDataPrice: MutableLiveData<HashMap<String, String>>,
    var subscriptionManager: SubscriptionManager,
    var isSelecterdPlan: IsSelecterdPlan,

    ) : ViewModel() {
    @SuppressLint("AnnotateVersionCheck")
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three", "_four", "_five", "_six", "_seven", "_eight")

    init {
        onMain()
    }

    interface IsSelecterdPlan {
        fun monMonthPlan()
        fun monYearPlan()
        fun monBackPress()
    }

    fun onMain() {
        setUI()
        setSubScriptionUI()
        setLineView()
        initListener()
    }

    @SuppressLint("SetTextI18n")
    fun setSubScriptionUI() {
        with(binding) {
            if (mIsRevenuCat!!) {
                packagerenlist!![0].sku.let {
                    if (it == "") {
                        txtMonthBottom.visibility = View.GONE
                    } else {
                        txtMonthlyPrice.text = packagerenlist!![0].price
                        txtMonthBottom.text = "Enjoy ${
                            packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                getSubTrial(it1)
                            }
                        } Free Trial"
                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                            val size = it1.length
                            val period = it1.substring(1, size - 1)
                            val str = it1.substring(size - 1, size)
                            Log.d("TAG", "getSubTrial: ${size} $period - $str")
                            when (str) {
                                "D" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                "W" -> {
                                    try {
                                        if (period.toInt() == 1) txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                        else txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                    } catch (e: Exception) {
                                        txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                    }
                                }
                                "M" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                "Y" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                    }
                }
                packagerenlist!![1].sku.let {

                    if (it == "") {
                        Log.d("enjoy", "onCreate:enjoy <-------------> 2")
                        txtYearlyPrice.visibility = View.GONE
                    } else {
                        txtYearlyPrice.text = packagerenlist!![1].price
                    }


                    packagerenlist?.get(1)?.price?.let { it1 ->
                        getMonthBaseYearlyDiscount(
                            packagerenlist?.get(0)?.price!!, it1
                        ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
                            txtYearBottom.text =
                                "${
                                    yearlyMonthBaseDiscountPrice.replace(
                                        ".00",
                                        ""
                                    )
                                }/month as BEST price"
                        }
                    }
                }
            } else {

                liveDataPeriod.observe(mActivity) { trial ->
                    liveDataPrice.observe(mActivity) { price ->
                        price[Constants.PREMIUM_SIX_SKU]?.let { month ->
                            price[PREMIUM_SKU]?.let { year ->
                                getMonthBaseYearlyDiscount(
                                    month,
                                    year
                                ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
                                    if (yearlyMonthBaseDiscountPrice.equals("₹590.00")) {
                                        txtYearBottom.text =
                                            "₹275/month as BEST price"
                                    } else {
                                        txtYearBottom.text =
                                            "${
                                                yearlyMonthBaseDiscountPrice.replace(
                                                    ".00",
                                                    ""
                                                )
                                            }/month as BEST price"
                                    }
                                }
                            }
                        }
                        PREMIUM_SKU.getProductInfo?.let { year ->
                            PREMIUM_SIX_SKU.getProductInfo?.let { month ->
                                if (year.freeTrialPeriod.equals("Not Found", true)) {
                                    txtUnlockKriadl.text = "Continue"
                                } else {
                                    txtUnlockKriadl.text = "start free trial"
                                }
                                if (month.freeTrialPeriod.equals("Not Found", true)) {
                                    txtMonthBottom.gone
                                } else {
                                    txtMonthBottom.text = "Enjoy ${
                                        getSubTrial(
                                            subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")
                                        )
                                    } Free Trial"
                                }
                            }
                        }
//                        price[Constants.PREMIUM_SIX_SKU]?.let {
//                            it
//                        }?.
//                        let {
//                            price[PREMIUM_SKU]?.let {
//                                it
//                            }?.let { it1 ->
//                                com.example.demo.subscriptionbackgroundflow.helper.getMonthBaseYearlyDiscount(
//                                    it, it1
//                                ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
//                                    Log.d("TAG", "ondffgCreate: $yearlyMonthBaseDiscountPrice")
//                                    if (yearlyMonthBaseDiscountPrice.equals("₹590.00")) {
//                                        txtYearBottom.text =
//                                            "₹275/month as BEST price"
//                                    } else {
//                                        txtYearBottom.text =
//                                            "${
//                                                yearlyMonthBaseDiscountPrice.replace(
//                                                    ".00",
//                                                    ""
//                                                )
//                                            }/month as BEST price"
//                                    }
//                                }
//                            }
//                        }
                    txtYearlyPrice.text = "${
                        price[PREMIUM_SKU]?.let {
                            it
                        }
                    }"
                    txtMonthlyPrice.text = "${
                        price[Constants.PREMIUM_SIX_SKU]?.let {
                            it
                        }
                    }"
                }

//                    trial[PREMIUM_SKU]?.let {
//                        if (it == "") {
//                            txtUnlockKriadl.text = "Continue"
//                        } else {
//                            txtUnlockKriadl.text = "start free trial"
//                        }
//                    }

//                    subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"").let {
//                        if (it == "") {
//                            txtMonthBottom.gone
//                        } else {
//                            txtMonthBottom.text = "Enjoy ${
//                                getSubTrial(
//                                    it
//                                )
//                            } Free Trial"
//                        }
//                    }
            }
        }
    }
}

@SuppressLint("NewApi", "SetTextI18n")
fun setUI() {
    with(binding) {
        if (isPiePlus()) {
            mActivity.setCloseIconPosition(
                fParentLayout = clMainLayout, // Parent Constraint Layout
                fCloseIcon = imgClose, // Image View
                fIconPosition = IconPosition.RIGHT // IconPosition Left or Right
            )
        }
        imgClose.setImageDrawable(Constants.mClose_Icon)
        imgAppIcon.setImageDrawable(Constants.mAppIcon)
        mCLUnlockLayout.setBackgroundDrawable(Constants.mPremium_Button_Icon)
        txtAppname.text = Constants.mAppName
        mIVMonthSelection.background = mPremium_Cardunselected_Icon
        mIVYearSelection.background = mPremium_CardSelected_Icon

    }
}

@SuppressLint("DiscouragedApi")
private fun setLineView() {
    with(binding) {
        for (i in 0..7) {
            logD(
                "mPremiumScreenLine",
                "----$i--${mPremiumScreenLine!!.size}--${idname[i]}"
            )
            if (mPremiumScreenLine!!.size <= i) {
                val id_name = "txt${idname[i]}"
                val redId =
                    mActivity.resources.getIdentifier(id_name, "id", mActivity.packageName)
                val txt: TextView = mActivity.findViewById(redId)
                txt.visibility = View.GONE

                val id_name1 = "img_true${idname[i]}"
                val redId1 =
                    mActivity.resources.getIdentifier(id_name1, "id", mActivity.packageName)
                val img_true: ImageView = mActivity.findViewById(redId1)
                img_true.visibility = View.GONE

            } else {
                val id_name = "txt${idname[i]}"
                val redId =
                    mActivity.resources.getIdentifier(id_name, "id", mActivity.packageName)
                val txt: TextView = mActivity.findViewById(redId)
                txt.visibility = View.VISIBLE
                txt.text = mPremiumScreenLine!![i].mLine

                val id_name1 = "img_true${idname[i]}"
                val redId1 =
                    mActivity.resources.getIdentifier(id_name1, "id", mActivity.packageName)
                val img_true: ImageView = mActivity.findViewById(redId1)
                img_true.visibility = View.VISIBLE
                img_true.setImageDrawable(mPremiumScreenLine!![i].mIconLine)
            }

        }
    }
}

private fun initListener() {
    with(binding) {
        binding.imgClose.click {
            isSelecterdPlan.monBackPress()
        }
        mCLMonthLayout.setOnClickListener {
            mIVYearSelection.background = mPremium_Cardunselected_Icon
            mIVMonthSelection.background = mPremium_CardSelected_Icon
            if (mIsRevenuCat!!) {
                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} at FREE trial, Then Enjoy ${
                                packagerenlist?.get(0)?.price
                            }/Days"
                        "W" -> {
                            try {
                                if (period.toInt() == 1) txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            } catch (e: Exception) {
                                txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                        "M" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "Y" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        else -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                    }
                    txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${
                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                            getSubTrial(it1)
                        }
                    } trial"
                }
            } else {
                PREMIUM_SIX_SKU.getProductInfo?.let { month ->
                    if (month.freeTrialPeriod.equals("Not Found", true)) {
                        txtUnlockKriadl.text = "Continue"
                    } else {
                        txtUnlockKriadl.text = "start free trial"
                    }
                }
            }
//                    liveDataPeriod.observe(mActivity) { trial ->
//                        subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"").let {
//                            if (it == "") {
//                                txtUnlockKriadl.text = "Continue"
//                            } else {
//                                txtUnlockKriadl.text = "start free trial"
//                            }
//                        }
//                    }
//                }
            plans = PREMIUM_SIX_SKU

        }
        mCLYearLayout.setOnClickListener {
            mIVYearSelection.background = mPremium_CardSelected_Icon
            mIVMonthSelection.background = mPremium_Cardunselected_Icon
            if (mIsRevenuCat!!) {
                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "W" -> {
                            try {
                                if (period.toInt() == 1) txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            } catch (e: Exception) {
                                txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                        "M" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "Y" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        else -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                    }
//                    txtFeature.text = "${packagerenlist?.get(1)?.price}/yearly after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                }
            } else {
                PREMIUM_SKU.getProductInfo?.let { year ->
                    if (year.freeTrialPeriod.equals("Not Found", true)) {
                        txtUnlockKriadl.text = "Continue"
                    } else {
                        txtUnlockKriadl.text = "start free trial"
                    }
                }
//                    liveDataPeriod.observe(mActivity) { trial ->
//                        trial[PREMIUM_SKU]?.let {
//                            if (it == "") {
//                                txtUnlockKriadl.text = "Continue"
//                            } else {
//                                txtUnlockKriadl.text = "start free trial"
//                            }
//                        }
//                    }

            }
            plans = PREMIUM_SKU

        }

        txtBtnPrivacy.click {
            mActivity.startActivity(Intent(mActivity, PrivacyActivity::class.java))
        }
        txtBtnCondition.click {
            mActivity.startActivity(Intent(mActivity, TermsActivity::class.java))
        }
        mCLUnlockLayout.setOnClickListener {
            if (mActivity.isOnline) {
                when (plans) {
                    PREMIUM_SIX_SKU -> {
                        isSelecterdPlan.monMonthPlan()
                    }
                    PREMIUM_SKU -> {
                        isSelecterdPlan.monYearPlan()
                    }

                }
            } else {
                mActivity.showToast(
                    "Please check internet connection.",
                    android.widget.Toast.LENGTH_SHORT
                )
            }
        }
    }
}


}