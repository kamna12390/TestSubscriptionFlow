package com.example.demo.subscriptionbackgroundflow.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModel
import com.example.demo.subscriptionbackgroundflow.helper.click
import com.example.demo.subscriptionbackgroundflow.activity.PrivacyPolicyActivity
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionActivity.Companion.plans
import com.example.demo.subscriptionbackgroundflow.activity.TermsActivity
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.BASIC_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumScreenLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_CardSelected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_Cardunselected_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.packagerenlist
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBinding
import com.example.demo.subscriptionbackgroundflow.helper.logD
import kotlinx.android.synthetic.main.activity_subscription.*
import kotlin.math.roundToInt


class SubscriptionViewModel(
    var binding: ActivitySubscriptionBinding,
    var mActivity: AppCompatActivity
) : ViewModel() {
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three", "_four", "_five", "_six", "_seven", "_eight")

    init {
        onMain()
    }

    fun onMain() {
        setUI()
        setLineView()
        initListener()
    }


    @SuppressLint("NewApi", "SetTextI18n")
    fun setUI() {
        with(binding) {
            if (isPiePlus()) {
                setCloseIconPosition(
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
                    txt.text =mPremiumScreenLine!![i].mLine

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
    private fun initListener(){
        with(binding){
            mCLMonthLayout.setOnClickListener {
                mIVYearSelection.background=mPremium_Cardunselected_Icon
                mIVMonthSelection.background=mPremium_CardSelected_Icon
                plans = BASIC_SKU
                packagerenlist?.get(0)?.freeTrialPeriod?.let {it1->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" ->    txtFeature.text = "${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} at FREE trial, Then Enjoy ${packagerenlist?.get(0)?.price}/Days"
                        "W" -> {
                            try {
                                if (period.toInt() == 1)   txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                                else   txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                            } catch (e: Exception) {
                                txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                            }
                        }
                        "M" ->txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                        "Y" ->   txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                        else -> txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                    }
                    txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                }
            }
            mCLYearLayout.setOnClickListener {
                mIVYearSelection.background=mPremium_CardSelected_Icon
                mIVMonthSelection.background=mPremium_Cardunselected_Icon
                plans = PREMIUM_SKU
                packagerenlist?.get(0)?.freeTrialPeriod?.let {it1->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" ->    txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                        "W" -> {
                            try {
                                if (period.toInt() == 1)   txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                                else   txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                            } catch (e: Exception) {
                                txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                            }
                        }
                        "M" ->txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                        "Y" ->   txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                        else -> txtFeature.text = "${packagerenlist?.get(1)?.price}/year after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                    }
//                    txtFeature.text = "${packagerenlist?.get(1)?.price}/yearly after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                }
            }
            txtBtnPrivacy.click {
                mActivity.startActivity(Intent(mActivity,PrivacyPolicyActivity::class.java))
            }
            txtBtnCondition.click {
                mActivity.startActivity(Intent(mActivity,TermsActivity::class.java))
            }
        }
    }
    private fun getMonthBaseYearlyDiscount(
        monthPrice: String,
        yearPrice: String,
        onDiscountCalculated: (yearlyDiscountPercentage: Double, yearlyMonthBaseDiscountPrice: String) -> Unit
    ) {
        monthPrice.getPriceInDouble.let { lMonthNumber ->
            yearPrice.getPriceInDouble.let { lYearNumber ->
                val lMonthPrize: Double = (lMonthNumber * 12) - lYearNumber
                val lYearPrizeBaseOfMonth = (lMonthNumber * 12)
                var lDiscountPercentage: Double = (lMonthPrize / lYearPrizeBaseOfMonth) * 100

                lDiscountPercentage *= 100
                lDiscountPercentage = lDiscountPercentage.toInt().toDouble()
                lDiscountPercentage /= 100

                val lDiscountPrice = monthPrice.replace(
                    String.format("%.2f", lMonthNumber),
                    String.format("%.2f", (((lYearNumber / 12).roundToInt()).toDouble())),
                    false
                )

                onDiscountCalculated.invoke(lDiscountPercentage, lDiscountPrice)
            }
        }
    }

    private val String.getPriceInDouble: Double
        get() {
            return if (this.isNotEmpty() && !(this.equals("Not Found", ignoreCase = false))) {
                try {
                    this.replace("""[^0-9.]""".toRegex(), "").toDouble()
                } catch (e: Exception) {
                    0.0
                }
            } else {
                0.0
            }
        }

    private fun getSubTrial(trial: String): String? {
        return try {
            val size = trial.length
            val period = trial.substring(1, size - 1)
            val str = trial.substring(size - 1, size)
            Log.d("TAG", "getSubTrial: ${size} $period - $str")
            when (str) {
                "D" -> "$period days"
                "W" -> {
                    try {
                        if (period.toInt() == 1) "7 days" else "$period Week"
                    } catch (e: Exception) {
                        "$period Week"
                    }
                }
                "M" -> "$period Months"
                "Y" -> "${period.toInt() * 12} Months"
                else -> "$period Months"
            }
        } catch (e: Exception) {
            "12 Months"
        }
    }

    private fun View.onGlobalLayout(callback: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        })
    }

    enum class IconPosition {
        LEFT, RIGHT
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setCloseIconPosition(
        fParentLayout: ConstraintLayout,
        fCloseIcon: ImageView,
        fIconPosition: IconPosition
    ) {
        fParentLayout.setOnApplyWindowInsetsListener { _, insets ->
            insets.displayCutout?.let { cutout ->
                val cutOutRect: Rect = cutout.boundingRects[0]
//                logE("setCloseIconPosition", "cutOutRect::->$cutOutRect")
                fCloseIcon.let { closeIcon ->
                    closeIcon.onGlobalLayout {
                        val closeIconRect = Rect()
                        closeIcon.getGlobalVisibleRect(closeIconRect)
//                        logE("setCloseIconPosition", "closeIconRect::->$closeIconRect")
//                        logE("setCloseIconPosition", "----------------------------------------")
//                        logE("setCloseIconPosition", "----------------------------------------")
//                        logE("setCloseIconPosition", "cutOut contains close::->${cutOutRect.contains(closeIconRect)}")
//                        logE("setCloseIconPosition", "cutOut contains close right::->${cutOutRect.contains(closeIconRect.right, closeIconRect.top)}")
//                        logE("setCloseIconPosition", "cutOut contains close left::->${cutOutRect.contains(closeIconRect.left, closeIconRect.bottom)}")
//                        logE("setCloseIconPosition", "cutOut contains close top::->${cutOutRect.contains(closeIconRect.left, closeIconRect.top)}")
//                        logE("setCloseIconPosition", "cutOut contains close bottom::->${cutOutRect.contains(closeIconRect.right, closeIconRect.bottom)}")
//                        logE("setCloseIconPosition", "----------------------------------------")
//                        logE("setCloseIconPosition", "----------------------------------------")
//                        logE("setCloseIconPosition", "close contains cutOut::->${closeIconRect.contains(cutOutRect)}")
//                        logE("setCloseIconPosition", "close contains cutOut right::->${closeIconRect.contains(cutOutRect.right, cutOutRect.top)}")
//                        logE("setCloseIconPosition", "close contains cutOut left::->${closeIconRect.contains(cutOutRect.left, cutOutRect.bottom)}")
//                        logE("setCloseIconPosition", "close contains cutOut top::->${closeIconRect.contains(cutOutRect.left, cutOutRect.top)}")
//                        logE("setCloseIconPosition", "close contains cutOut bottom::->${closeIconRect.contains(cutOutRect.right, cutOutRect.bottom)}")
                        if (closeIconRect.contains(cutOutRect)
                            || closeIconRect.contains(cutOutRect.right, cutOutRect.top)
                            || closeIconRect.contains(cutOutRect.left, cutOutRect.bottom)
                            || closeIconRect.contains(cutOutRect.left, cutOutRect.top)
                            || closeIconRect.contains(cutOutRect.right, cutOutRect.bottom)
                            || cutOutRect.contains(closeIconRect)
                            || cutOutRect.contains(closeIconRect.right, closeIconRect.top)
                            || cutOutRect.contains(closeIconRect.left, closeIconRect.bottom)
                            || cutOutRect.contains(closeIconRect.left, closeIconRect.top)
                            || cutOutRect.contains(closeIconRect.right, closeIconRect.bottom)
                        ) {
                            closeIcon.updateLayoutParams<ConstraintLayout.LayoutParams> {
                                when (fIconPosition) {
                                    IconPosition.LEFT -> {
                                        startToStart = ConstraintSet.PARENT_ID
                                        endToEnd = ConstraintSet.UNSET
                                    }
                                    IconPosition.RIGHT -> {
                                        endToEnd = ConstraintSet.PARENT_ID
                                        startToStart = ConstraintSet.UNSET
                                        marginEnd =
                                            mActivity.resources.getDimension(com.intuit.sdp.R.dimen._10sdp)
                                                .roundToInt()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return@setOnApplyWindowInsetsListener insets
        }
    }

//    override fun onPurchases(orderId: String, str: String) {
//        BaseSharedPreferences(mActivity).mIS_SUBSCRIBED=true
//    }
}