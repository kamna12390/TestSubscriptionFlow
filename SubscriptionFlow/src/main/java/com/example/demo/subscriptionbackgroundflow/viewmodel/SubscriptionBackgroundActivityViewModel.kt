package com.example.demo.subscriptionbackgroundflow.viewmodel

//import com.example.demo.subscriptionbackgroundflow.MyApplication.Companion.packagerenlist
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModel
import com.example.demo.subscriptionbackgroundflow.AdsClasss.InterstitialAds
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppIcon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppName
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mBasic_Line_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mClose_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_Button_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_True_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.packagerenlist
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBackgroundBinding
import com.example.demo.subscriptionbackgroundflow.helper.logD
import kotlin.math.roundToInt

class SubscriptionBackgroundActivityViewModel(
    var binding: ActivitySubscriptionBackgroundBinding,
   var  mActivity: AppCompatActivity
) : ViewModel() {
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three","_four","_five","_six","_seven","_eight")
    init {
        onMain()
    }
    fun onMain(){

        InterstitialAds().loadInterstitialAd(mActivity)
        with(binding) {
            if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                if (BaseSharedPreferences(mActivity).mIS_SUBSCRIBED!!) {
                    mActivity.onBackPressed()
//                    super.onBackPressed()
                }
            }
            if (mActivity.intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                if (BaseSharedPreferences(mActivity).mSecondTime!!) {
                    txtTryLimited.visibility = View.GONE

                } else {
                    txtTryLimited.visibility = View.VISIBLE
                    txtTryLimited.text = "OR TRY LIMITED VERSION"
                }
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                txtTryLimited.visibility = View.VISIBLE
                txtTryLimited.text = "Click here for more plans"
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                txtTryLimited.visibility = View.GONE
            }
        }

        setUI()
        setLineView()
    }
    fun setLineView(){
        with(binding){
            ivClose.setImageDrawable(mClose_Icon)
            imgAppIcon.setImageDrawable(mAppIcon)
            mCLUnlockLayout.setBackgroundDrawable(Constants.mPremium_Button_Icon)
            for (i in  0..7){
                logD("isforrlooopcheck","----$i--${mPremiumLine!!.size}--${idname[i]}--${(mPremiumLine!!.size==(i+1))}")
                if (mPremiumLine!!.size<=i){
                    val id_name="txt${idname[i]}"
                    val redId=mActivity.resources.getIdentifier(id_name,"id",mActivity.packageName)
                    val txt: TextView = mActivity.findViewById(redId)
                    txt.visibility=View.GONE

                    val id_name1="img_true${idname[i]}"
                    val redId1=mActivity.resources.getIdentifier(id_name1,"id",mActivity.packageName)
                    val img_true: ImageView = mActivity.findViewById(redId1)
                    img_true.visibility=View.GONE

                    val id_name2="img_Pright${idname[i]}"
                    val redId2=mActivity.resources.getIdentifier(id_name2,"id",mActivity.packageName)
                    val img_Pright: ImageView = mActivity.findViewById(redId2)
                    img_Pright.visibility=View.GONE

                    val id_name3="img_Bright${idname[i]}"
                    val redId3=mActivity.resources.getIdentifier(id_name3,"id",mActivity.packageName)
                    val img_Bright: ImageView = mActivity.findViewById(redId3)
                    img_Bright.visibility=View.GONE
                }else{
                    val id_name="txt${idname[i]}"
                    val redId=mActivity.resources.getIdentifier(id_name,"id",mActivity.packageName)
                    val txt: TextView = mActivity.findViewById(redId)
                    txt.visibility=View.VISIBLE
                    txt.text= mPremiumLine!![i].mLine

                    val id_name1="img_true${idname[i]}"
                    val redId1=mActivity.resources.getIdentifier(id_name1,"id",mActivity.packageName)
                    val img_true: ImageView = mActivity.findViewById(redId1)
                    img_true.visibility=View.VISIBLE
                    img_true.setImageDrawable(mPremiumLine!![i].mIconLine)

                    val id_name2="img_Pright${idname[i]}"
                    val redId2=mActivity.resources.getIdentifier(id_name2,"id",mActivity.packageName)
                    val img_Pright: ImageView = mActivity.findViewById(redId2)
                    img_Pright.visibility=View.VISIBLE
                    img_Pright.setImageDrawable(mPremium_True_Icon)

                    val id_name3="img_Bright${idname[i]}"
                    val redId3=mActivity.resources.getIdentifier(id_name3,"id",mActivity.packageName)
                    val img_Bright: ImageView = mActivity.findViewById(redId3)
                    img_Bright.visibility=View.VISIBLE
                    if (mPremiumLine!![i].mLineRight){
                        img_Bright.setImageDrawable(mBasic_Line_Icon)
                    }else{
                        img_Bright.setImageDrawable(mPremium_True_Icon)
                    }
                }

            }
        }
    }
    fun setUI(){
        with(binding){
            if (isPiePlus()) {
                setCloseIconPosition(
                    fParentLayout = mainleyouut2, // Parent Constraint Layout
                    fCloseIcon = ivClose, // Image View
                    fIconPosition = IconPosition.RIGHT // IconPosition Left or Right
                )
            }

            txtAppname.text=mAppName
            Handler().postDelayed(Runnable {
                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    textPrice.text = "${packagerenlist?.get(0)?.price}/Month after ${
                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                            getSubTrial(it1)
                        }
                    } of FREE trial."
                }
            }, 200)
        }
    }

    private fun View.onGlobalLayout(callback: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
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
    fun setCloseIconPosition(fParentLayout: ConstraintLayout, fCloseIcon: ImageView, fIconPosition: IconPosition) {
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
                                        marginEnd = mActivity.resources.getDimension(com.intuit.sdp.R.dimen._10sdp).roundToInt()
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
//    override fun onPurchases(orderId: String, str: String) {
//        BaseSharedPreferences(mActivity).mIS_SUBSCRIBED=true
//    }

}