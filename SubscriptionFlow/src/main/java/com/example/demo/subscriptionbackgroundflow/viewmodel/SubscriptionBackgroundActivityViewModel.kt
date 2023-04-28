package com.example.demo.subscriptionbackgroundflow.viewmodel

//import com.example.demo.subscriptionbackgroundflow.MyApplication.Companion.packagerenlist
import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crop.photo.image.resize.cut.tools.subscripction.ProductPurchaseHelper.getProductInfo
import com.example.demo.subscriptionbackgroundflow.AdsClasss.InterstitialAds
import com.example.demo.subscriptionbackgroundflow.activity.SubscriptionActivity
import com.example.demo.subscriptionbackgroundflow.basemodule.BaseSharedPreferences
import com.example.demo.subscriptionbackgroundflow.constants.Constants
import com.example.demo.subscriptionbackgroundflow.constants.Constants.PREMIUM_SIX_SKU
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppIcon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mAppName
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mBasic_Line_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mClose_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mIsRevenuCat
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremiumLine
import com.example.demo.subscriptionbackgroundflow.constants.Constants.mPremium_True_Icon
import com.example.demo.subscriptionbackgroundflow.constants.Constants.packagerenlist
import com.example.demo.subscriptionbackgroundflow.databinding.ActivitySubscriptionBackgroundBinding
import com.example.demo.subscriptionbackgroundflow.helper.*
import com.example.demo.subscriptionbackgroundflow.manager.PreferencesKeys
import com.example.demo.subscriptionbackgroundflow.manager.SubscriptionManager
import kotlin.math.roundToInt

class SubscriptionBackgroundActivityViewModel(
    var binding: ActivitySubscriptionBackgroundBinding,
    var  mActivity: AppCompatActivity,
    var liveDataPeriod: MutableLiveData<HashMap<String, String>>,
    var liveDataPrice: MutableLiveData<HashMap<String, String>>,
    var subscriptionManager: SubscriptionManager,
    var isSelecterdPlan: IsSelecterdPlan
) : ViewModel() {
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three","_four","_five","_six","_seven","_eight")
    val TAG =mActivity.javaClass.simpleName
    interface IsSelecterdPlan {
        fun monMonthPlan()
        fun monYearPlan()
        fun monBackPress()
    }
    init {
        onMain()
    }
    fun onMain(){
        InterstitialAds().loadInterstitialAd(mActivity)
        setUI()
        setSubScriptionUI()
        setLineView()
        initListener()
    }
    fun initListener() {
        binding.ivClose.click {
            isSelecterdPlan.monBackPress()
        }
        binding.mCLUnlockLayout.click {
            if (mActivity.isOnline) {
                isSelecterdPlan.monMonthPlan()

            } else {
                mActivity.showToast("Please check internet connection.", android.widget.Toast.LENGTH_SHORT)
            }
        }
        binding.txtTryLimited.click {
            if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                val mintent = Intent(mActivity, SubscriptionActivity::class.java)
                mintent.putExtra("AppOpen", mActivity.intent.getStringExtra("AppOpen"))
                mActivity.startActivity(mintent)
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                isSelecterdPlan.monBackPress()
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                isSelecterdPlan.monBackPress()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun setSubScriptionUI(){
        with(binding){
            if (isPiePlus()) {
                mActivity.setCloseIconPosition(
                    fParentLayout = mainleyouut2, // Parent Constraint Layout
                    fCloseIcon = ivClose, // Image View
                    fIconPosition = IconPosition.RIGHT // IconPosition Left or Right
                )
            }
            txtAppname.text=mAppName
                if (mIsRevenuCat!!){
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
            }else{
                liveDataPeriod.observe(mActivity) { trial ->
                    liveDataPrice.observe(mActivity) { price ->

//                        logD(TAG," onCreate: liveDataPeriod->$trial\nliveDataPrice->$price\n trial->${subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")}")
                        PREMIUM_SIX_SKU.getProductInfo?.let { month ->

                            if (month.freeTrialPeriod.equals("Not Found", true)) {
                                textPrice.text = "${
                                    price[PREMIUM_SIX_SKU]?.replace(
                                        ".00",
                                        ""
                                    )
                                }/Month."
                                txtUnlockKriadl.text = "Continue"
                            } else {
                                textPrice.text =
                                    "${
                                        getSubTrial(
                                            subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")
                                        )
                                    } FREE trial, then ${
                                        price[PREMIUM_SIX_SKU]?.replace(
                                            ".00",
                                            ""
                                        )
                                    }/Month."
                                txtUnlockKriadl.text = "start free trial"
                            }
                        }
                    }
                }
            }
        }
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
    }


}