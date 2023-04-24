//package com.example.demo.subscriptionbackgroundflow.myadslibrary.ui
//
//import android.annotation.SuppressLint
//import android.content.*
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.os.*
//import android.util.Log
//import android.view.View
//import android.view.Window
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.example.demo.subscriptionbackgroundflow.R
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.Api.ApiNew
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.ImagesItem
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.helper.DBHelperNew
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.helper.StatusBarUtil
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.interfaces.ApiInterface
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.receiver.NetworkChangeReceiver
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection
//import kotlinx.android.synthetic.main.activity_more_app_new.*
//import kotlinx.android.synthetic.main.layout_contant_main_new.*
//import kotlinx.android.synthetic.main.new_activity_more_app.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.*
//import kotlin.collections.ArrayList
//
//class NewMoreAppActivity : AppCompatActivity() {
//    private var apiInterface: ApiInterface? = null
//    var mDataList: ArrayList<ImagesItem>? = ArrayList()
//    private var mContext: Context? = null
//    private var receiver: Receiver? = null
//    var isCalled = false
//    var model: com.example.demo.subscriptionbackgroundflow.myadslibrary.Response? = null
//    private var dbHelper: DBHelperNew? = null
//    var mDataStoreList: ArrayList<String> = ArrayList()
//    var mDataImageLink: ArrayList<String> = ArrayList()
//    var mDataShareLink: ArrayList<String> = ArrayList()
//    var isCursor = false
//    private var mNetworkReceiver: NetworkChangeReceiver? = null
//
//    @SuppressLint("Range")
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.new_activity_more_app)
//
//        mContext = this@NewMoreAppActivity
////        StatusBarUtil.setTransparent(window, this)
////        StatusBarUtil.setDarkIconStatusBar(findViewById<View>(R.id.appBar),this)
//        StatusBarUtil.setStatusBarGradiant(this)
////        findViewById<View>(R.id.appBar).setPadding(
////            0,
////            StatusBarUtil.getStatusBarHeight(resources),
////            0,
////            0
////        )
//        //init Receiver
//        receiver = Receiver()
//        mNetworkReceiver = NetworkChangeReceiver()
//
////        setStatusBarColor()
//        dbHelper = DBHelperNew(this@NewMoreAppActivity)
//        initListener()
//        initAction()
//        if (InternetConnection.checkConnection(this@NewMoreAppActivity)) {
//            getAppData()
//            isCalled = true
//            val newlist = dbHelper!!.allMoreAppData
//            while (newlist.moveToNext()) {
//                mDataImageLink.add(newlist.getString(newlist.getColumnIndex(newlist.getColumnName(3))))
//                mDataShareLink.add(newlist.getString(newlist.getColumnIndex(newlist.getColumnName(4))))
//            }
//
//            Log.d("TAG", "doInBackground12121212: ${mDataImageLink}")
//            if (newlist.count > 0) {
//                isCursor = true
//                cl_loadingNew.visibility = View.GONE
//                Log.d("TAG", "onCreate12121: database")
//                Glide.with(applicationContext).load(mDataImageLink[0])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader.visibility = View.GONE
//                            appThumb.visibility = View.VISIBLE
//                            appThumb.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//                Glide.with(applicationContext).load(mDataImageLink[1])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader2.visibility = View.GONE
//                            appThumb2.visibility = View.VISIBLE
//                            appThumb2.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//                Glide.with(applicationContext).load(mDataImageLink[2])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader3.visibility = View.GONE
//                            appThumb3.visibility = View.VISIBLE
//                            appThumb3.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//                Glide.with(applicationContext).load(mDataImageLink[3])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader4.visibility = View.GONE
//                            appThumb4.visibility = View.VISIBLE
//                            appThumb4.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//                Glide.with(applicationContext).load(mDataImageLink[4])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader5.visibility = View.GONE
//                            appThumb5.visibility = View.VISIBLE
//                            appThumb5.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//                Glide.with(applicationContext).load(mDataImageLink[5])
//                    .override(800, 800).into(object : CustomTarget<Drawable>() {
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            transition: Transition<in Drawable>?
//                        ) {
//                            mLoader6.visibility = View.GONE
//                            appThumb6.visibility = View.VISIBLE
//                            appThumb6.setImageDrawable(resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//
//                    })
//            } else {
//                isCursor = false
//                Log.d("TAG", "onCreate12121: api")
//                getAppData()
//            }
//        } else {
//            ctOfflineNew.visibility = View.VISIBLE
//            ctErrornew.visibility = View.GONE
//            mainCl.visibility = View.GONE
//        }
//
//        registerReceiver(mNetworkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
//        registerReceiver(receiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
//
//
//        layoutConstr.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[0])))
//            } else {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse((model!!.data?.get(0)!!.images!![0].size!!))
//                    )
//                )
//            }
//            isEnabledMore()
//        }
//        layoutConstr2.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[1])))
//            } else {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse((model!!.data?.get(0)!!.images!![1].size!!))
//                    )
//                )
//
//            }
//            isEnabledMore()
//        }
//        layoutConstr3.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[2])))
//            } else {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse((model!!.data?.get(0)!!.images!![2].size!!))
//                    )
//                )
//
//            }
//            isEnabledMore()
//
//        }
//        layoutConstr4.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[3])))
//            } else {
//                try {
//                    startActivity(
//                        Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse((model!!.data?.get(0)!!.images!![3].size!!))
//                        )
//                    )
//                } catch (e: Exception) {
//                }
//
//            }
//            isEnabledMore()
//        }
//        layoutConstr5.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[4])))
//            } else {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse((model!!.data?.get(0)!!.images!![4].size!!))
//                    )
//                )
//
//            }
//            isEnabledMore()
//        }
//        layoutConstr6.setOnClickListener {
//            isDisabled()
//            if (isCursor) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDataShareLink[5])))
//            } else {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse((model!!.data?.get(0)!!.images!![5].size!!))
//                    )
//                )
//
//            }
//            isEnabledMore()
//        }
//
//        ctErrornew.visibility = View.GONE
//
//
//    }
//
//    fun isDisabled() {
//        layoutConstr.isEnabled = false
//        layoutConstr4.isEnabled = false
//        layoutConstr2.isEnabled = false
//        layoutConstr3.isEnabled = false
//        layoutConstr5.isEnabled = false
//        layoutConstr6.isEnabled = false
//    }
//
//    fun isEnabledMore() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            layoutConstr.isEnabled = true
//            layoutConstr4.isEnabled = true
//            layoutConstr2.isEnabled = true
//            layoutConstr3.isEnabled = true
//            layoutConstr5.isEnabled = true
//            layoutConstr6.isEnabled = true
//        }, 2000)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    fun setStatusBarColor() {
//        val window: Window = window
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                window.statusBarColor =
//                    ContextCompat.getColor(this@NewMoreAppActivity, R.color.colorPick)
//            }
//        }
//    }
//
//    private fun setWindowFlag(bits: Int, on: Boolean) {
//        val win = window
//        val winParams = win.attributes
//        if (on) {
//            winParams.flags = winParams.flags or bits
//        } else {
//            winParams.flags = winParams.flags and bits.inv()
//        }
//        win.attributes = winParams
//    }
//
//    fun initAction() {
//        ctOfflineNew.setVisibility(View.GONE)
//        ctErrornew.setVisibility(View.GONE)
//
//
//        if (!InternetConnection.checkConnection(this)) {
//            ctOfflineNew.visibility = View.VISIBLE
//            cl_loadingNew.visibility = View.GONE
//            ctErrornew.setVisibility(View.GONE)
////            mainCl.visibility = View.GONE
//
//        } else {
////            mainCl.visibility = View.VISIBLE
//        }
////        cl_loadingNew.visibility = View.GONE
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//    }
//
//    fun initListener() {
//        imgBtnBackNew.setOnClickListener(View.OnClickListener { onBackPressed() })
//        imgBtnShareNew.setOnClickListener(View.OnClickListener {
//            imgBtnShareNew.isEnabled = false
//            try {
//                val shareIntent = Intent(Intent.ACTION_SEND)
//                shareIntent.type = "text/plain"
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Image Crop")
//                var shareMessage =
//                    "\nTry Image Crop for Crop Images and Videos with amazing features Download app now from given link\n\n"
//                shareMessage += "https://play.google.com/store/apps/details?id=com.crop.photo.image.resize.cut.tools&hl=en"
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
//                startActivity(Intent.createChooser(shareIntent, "Choose One"))
//            } catch (e: java.lang.Exception) {
//                //e.toString();
//            }
//            Handler(Looper.getMainLooper()).postDelayed({
//                imgBtnShareNew.isEnabled = true
//            },5000)
//        })
//        findViewById<View>(R.id.txtRetry).setOnClickListener {
//            object : AsyncTask<Void?, Void?, Void?>() {
//                @SuppressLint("Range")
//                override fun doInBackground(vararg params: Void?): Void? {
////                        getAppData()
//                    if (InternetConnection.checkConnection(this@NewMoreAppActivity)) {
//                        getAppData()
//                        isCalled = true
//                        val newlist = dbHelper!!.allMoreAppData
////            newlist.moveToFirst()
//                        while (newlist.moveToNext()) {
//                            mDataImageLink.add(
//                                newlist.getString(
//                                    newlist.getColumnIndex(
//                                        newlist.getColumnName(
//                                            3
//                                        )
//                                    )
//                                )
//                            )
//                            mDataShareLink.add(
//                                newlist.getString(
//                                    newlist.getColumnIndex(
//                                        newlist.getColumnName(
//                                            4
//                                        )
//                                    )
//                                )
//                            )
//                        }
//
//
//
//                        Log.d("TAG", "doInBackground12121212: ${mDataImageLink}")
//                        if (newlist.count > 0) {
//                            isCursor = true
//                            runOnUiThread {
//                                ctOfflineNew.visibility = View.GONE
//                                ctErrornew.visibility = View.GONE
//                                cl_loadingNew.visibility = View.GONE
//                            }
//                            Log.d("TAG", "onCreate12121: database")
//                            Glide.with(applicationContext).load(mDataImageLink[0])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader.visibility = View.GONE
//                                        appThumb.visibility = View.VISIBLE
//                                        appThumb.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[1])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader2.visibility = View.GONE
//                                        appThumb2.visibility = View.VISIBLE
//                                        appThumb2.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[2])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader3.visibility = View.GONE
//                                        appThumb3.visibility = View.VISIBLE
//                                        appThumb3.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[3])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader4.visibility = View.GONE
//                                        appThumb4.visibility = View.VISIBLE
//                                        appThumb4.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[4])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader5.visibility = View.GONE
//                                        appThumb5.visibility = View.VISIBLE
//                                        appThumb5.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[5])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader6.visibility = View.GONE
//                                        appThumb6.visibility = View.VISIBLE
//                                        appThumb6.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                        } else {
//                            isCursor = false
//                            Log.d("TAG", "onCreate12121: api")
//                            getAppData()
//                        }
//                    } else {
//                        runOnUiThread {
//                            Toast.makeText(
//                                this@NewMoreAppActivity,
//                                resources.getString(R.string.please_turn_on_internet),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                    return null
//                }
//            }.execute()
//        }
//        findViewById<View>(R.id.txtRetry1).setOnClickListener {
//            object : AsyncTask<Void?, Void?, Void?>() {
//                @SuppressLint("Range")
//                override fun doInBackground(vararg params: Void?): Void? {
//                    if (InternetConnection.checkConnection(this@NewMoreAppActivity)) {
//                        getAppData()
//                        isCalled = true
//                        val newlist = dbHelper!!.allMoreAppData
////            newlist.moveToFirst()
//                        while (newlist.moveToNext()) {
//                            mDataImageLink.add(
//                                newlist.getString(
//                                    newlist.getColumnIndex(
//                                        newlist.getColumnName(
//                                            3
//                                        )
//                                    )
//                                )
//                            )
//                            mDataShareLink.add(
//                                newlist.getString(
//                                    newlist.getColumnIndex(
//                                        newlist.getColumnName(
//                                            4
//                                        )
//                                    )
//                                )
//                            )
//                        }
//
//
//
//                        Log.d("TAG", "doInBackground12121212: ${mDataImageLink}")
//                        if (newlist.count > 0) {
//                            isCursor = true
//                            ctOfflineNew.visibility = View.GONE
//                            ctErrornew.visibility = View.GONE
//                            cl_loadingNew.visibility = View.GONE
//                            Log.d("TAG", "onCreate12121: database")
//                            Glide.with(applicationContext).load(mDataImageLink[0])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader.visibility = View.GONE
//                                        appThumb.visibility = View.VISIBLE
//                                        appThumb.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[1])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader2.visibility = View.GONE
//                                        appThumb2.visibility = View.VISIBLE
//                                        appThumb2.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[2])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader3.visibility = View.GONE
//                                        appThumb3.visibility = View.VISIBLE
//                                        appThumb3.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[3])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader4.visibility = View.GONE
//                                        appThumb4.visibility = View.VISIBLE
//                                        appThumb4.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[4])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader5.visibility = View.GONE
//                                        appThumb5.visibility = View.VISIBLE
//                                        appThumb5.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                            Glide.with(applicationContext).load(mDataImageLink[5])
//                                .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        mLoader6.visibility = View.GONE
//                                        appThumb6.visibility = View.VISIBLE
//                                        appThumb6.setImageDrawable(resource)
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {
//                                    }
//
//                                })
//                        } else {
//                            isCursor = false
//                            Log.d("TAG", "onCreate12121: api")
//                            getAppData()
//                        }
//                    } else {
//                        runOnUiThread {
//                            Toast.makeText(
//                                this@NewMoreAppActivity,
//                                resources.getString(R.string.please_turn_on_internet),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                    return null
//                }
//            }.execute()
//        }
//    }
//
//    fun getAppData() {
//
//        Log.d("TAG", "getAppData111: api calla")
//        runOnUiThread {
//            mainCl.visibility = View.GONE
//            ctOfflineNew.visibility = View.GONE
//            ctErrornew.visibility = View.GONE
//            if (!isCursor) {
//                cl_loadingNew.visibility = View.VISIBLE
//            }
//            if (isCursor) {
//                mainCl.visibility = View.VISIBLE
//            }
//        }
//
//
//
//        apiInterface = ApiNew.client!!.create(ApiInterface::class.java)
//        apiInterface!!.allAppNew.enqueue(object :
//            Callback<com.example.demo.subscriptionbackgroundflow.myadslibrary.Response> {
//            @SuppressLint("Range")
//            override fun onResponse(
//                call: Call<com.example.demo.subscriptionbackgroundflow.myadslibrary.Response>,
//                response: Response<com.example.demo.subscriptionbackgroundflow.myadslibrary.Response>
//            ) {
//
//                Log.d("TAG", "responseCode: ${response.body()!!.responseCode}")
//
//                if (response.body()!!.responseCode == "1" && response.body()!!.data!!.isNotEmpty()) {
//                    model = response.body()
//                    Log.d("TAG", "onResponse: ${model!!.data}")
//                    cl_loadingNew.visibility = View.GONE
//
////                    mainCl.visibility = View.VISIBLE
//                    if (model?.data != null) {
//                        mainCl.visibility = View.VISIBLE
//                        mDataList = model!!.data!![0].images
//
//                        Log.d("TAG", "onResponseqqqqq: ${mDataList!!.size}")
//                        if (!isCursor) {
//                            dbHelper!!.deleteData()
//                            for (i in mDataList!!.indices) {
//
//                                val appModel: ImagesItem = mDataList!![i]
//                                if (!appModel.size!!.contains("com.crop.photo.image.resize.cut.tools")) {
//                                    dbHelper!!.insertPath(
//                                        mDataList!![i].position!!,
//                                        mDataList!![i].name!!,
//                                        mDataList!![i].thumbImage!!,
//                                        mDataList!![i].size!!
//                                    )
//                                }
////
//                                Log.d("TAG", "onResponseqqqqq: ${mDataList!!.size}")
//                                Log.d("TAG", "onResponse: $mDataList")
//
//
//                            }
//                            val newListData = dbHelper!!.allMoreAppData
//                            while (newListData.moveToNext()) {
//                                mDataStoreList.add(
//                                    newListData.getString(
//                                        newListData.getColumnIndex(
//                                            newListData.getColumnName(1)
//                                        )
//                                    )
//                                )
//                            }
//                            newListData.close()
//                            val newlist = dbHelper!!.allMoreAppData
////            newlist.moveToFirst()
//                            if (newlist.count == 6) {
//                                while (newlist.moveToNext()) {
//                                    mDataImageLink.add(
//                                        newlist.getString(
//                                            newlist.getColumnIndex(
//                                                newlist.getColumnName(3)
//                                            )
//                                        )
//                                    )
//                                    mDataShareLink.add(
//                                        newlist.getString(
//                                            newlist.getColumnIndex(
//                                                newlist.getColumnName(4)
//                                            )
//                                        )
//                                    )
//                                }
//
//                                Log.d("TAG", "doInBackground12121212: ${mDataImageLink}")
//                                if (newlist.count > 0) {
//                                    isCursor = true
//                                    cl_loadingNew.visibility = View.GONE
//                                    Log.d("TAG", "onCreate12121: database")
//                                    Glide.with(applicationContext).load(mDataImageLink[0])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader.visibility = View.GONE
//                                                appThumb.visibility = View.VISIBLE
//                                                appThumb.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                    Glide.with(applicationContext).load(mDataImageLink[1])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader2.visibility = View.GONE
//                                                appThumb2.visibility = View.VISIBLE
//                                                appThumb2.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                    Glide.with(applicationContext).load(mDataImageLink[2])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader3.visibility = View.GONE
//                                                appThumb3.visibility = View.VISIBLE
//                                                appThumb3.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                    Glide.with(applicationContext).load(mDataImageLink[3])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader4.visibility = View.GONE
//                                                appThumb4.visibility = View.VISIBLE
//                                                appThumb4.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                    Glide.with(applicationContext).load(mDataImageLink[4])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader5.visibility = View.GONE
//                                                appThumb5.visibility = View.VISIBLE
//                                                appThumb5.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                    Glide.with(applicationContext).load(mDataImageLink[5])
//                                        .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                            override fun onResourceReady(
//                                                resource: Drawable,
//                                                transition: Transition<in Drawable>?
//                                            ) {
//                                                mLoader6.visibility = View.GONE
//                                                appThumb6.visibility = View.VISIBLE
//                                                appThumb6.setImageDrawable(resource)
//                                            }
//
//                                            override fun onLoadCleared(placeholder: Drawable?) {
//                                            }
//
//                                        })
//                                }
//                            }
//
//                        } else {
//                            val mDataNewApi = dbHelper!!.allMoreAppData
//                            mDataImageLink.clear()
//                            while (mDataNewApi.moveToNext()) {
//                                mDataImageLink.add(
//                                    mDataNewApi.getString(
//                                        mDataNewApi.getColumnIndex(
//                                            mDataNewApi.getColumnName(3)
//                                        )
//                                    )
//                                )
//                            }
//                            Log.d("TAG", "onResponsehfhfhfhfhhf: ${mDataList!!.size}")
//                            for (k in mDataImageLink!!.indices) {
//                                if (mDataList!![k].thumbImage == mDataImageLink[k]) {
//                                    Log.d("TAG", "onResponsehfhfhfhfhhf: same data")
//                                } else {
//                                    Log.d("TAG", "onResponsehfhfhfhfhhf: diffrent data")
//                                    isCursor = true
//                                    dbHelper!!.deleteData()
//                                    for (i in mDataList!!.indices) {
//                                        val appModel: ImagesItem = mDataList!![i]
//                                        if (!appModel.size!!.contains("com.crop.photo.image.resize.cut.tools")) {
//                                            dbHelper!!.insertPath(
//                                                mDataList!![i].position!!,
//                                                mDataList!![i].name!!,
//                                                mDataList!![i].thumbImage!!,
//                                                mDataList!![i].size!!
//                                            )
//                                        }
//
//
//                                    }
//                                }
//                            }
//                        }
//                        Log.d("TAG", "onResponseDatata: $mDataStoreList")
//
//
////                    recyclerViewNew.adapter =
////                        MoreAppNewAdepter(applicationContext, model!!.data,object : MoreAppNewAdepter.OnAppsClickListener{
////                            override fun onAppClick(model: String) {
////                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(model)))
////                            }
////
////                        })
////                        new ValidTask().execute();
////                         new GetVersionCode().execute(i);
//                    } else {
//                        runOnUiThread {
//                            mainCl.visibility = View.GONE
//                            cl_loadingNew.setVisibility(View.GONE)
//                            ctOfflineNew.setVisibility(View.GONE)
//                            ctErrornew.setVisibility(View.VISIBLE)
//                        }
//                    }
//                } else {
//                    runOnUiThread {
//                        cl_loadingNew.setVisibility(View.GONE)
//                        ctOfflineNew.setVisibility(View.GONE)
//                        ctErrornew.setVisibility(View.VISIBLE)
//                    }
//                }
//
//
//            }
//
//            override fun onFailure(
//                call: Call<com.example.demo.subscriptionbackgroundflow.myadslibrary.Response>,
//                t: Throwable
//            ) {
//                runOnUiThread {
//                    try {
//                        Thread.sleep(1000)
//                    } catch (e: InterruptedException) {
//                        e.printStackTrace()
//                    }
//                    cl_loadingNew.setVisibility(View.GONE)
//                    ctOfflineNew.setVisibility(View.GONE)
//                    ctErrornew.setVisibility(View.GONE)
//                    if (!InternetConnection.checkConnection(this@NewMoreAppActivity)) ctOfflineNew.setVisibility(
//                        View.VISIBLE
//                    ) else ctErrornew.setVisibility(
//                        View.VISIBLE
//                    )
//                }
//            }
//
//        })
//    }
//
//    inner class Receiver : BroadcastReceiver {
//        constructor() : super()
//
//        @SuppressLint("Range")
//        override fun onReceive(context: Context, intent: Intent) {
//            if (InternetConnection.checkConnection(context)) {
//                Log.d("internet", "onReceive: isonline")
//                getAppData()
//                if (InternetConnection.checkConnection(context)) {
//                    Log.d("nenenwnw", "onReceive11: internet On ")
////                    getAppData()
//                    isCalled = true
//                    val newlist = dbHelper!!.allMoreAppData
//                    while (newlist.moveToNext()) {
//                        mDataImageLink.add(
//                            newlist.getString(
//                                newlist.getColumnIndex(
//                                    newlist.getColumnName(
//                                        3
//                                    )
//                                )
//                            )
//                        )
//                        mDataShareLink.add(
//                            newlist.getString(
//                                newlist.getColumnIndex(
//                                    newlist.getColumnName(
//                                        4
//                                    )
//                                )
//                            )
//                        )
//                    }
//                    Log.d("TAG", "doInBackground12121212: ${mDataImageLink}")
//                    if (newlist.count > 0) {
//                        isCursor = true
//                        ctOfflineNew.visibility = View.GONE
//                        ctErrornew.visibility = View.GONE
//                        cl_loadingNew.visibility = View.GONE
//                        Log.d("TAG", "onCreate12121: database")
//                        Glide.with(applicationContext).load(mDataImageLink[0])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader.visibility = View.GONE
//                                    appThumb.visibility = View.VISIBLE
//                                    appThumb.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                        Glide.with(applicationContext).load(mDataImageLink[1])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader2.visibility = View.GONE
//                                    appThumb2.visibility = View.VISIBLE
//                                    appThumb2.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                        Glide.with(applicationContext).load(mDataImageLink[2])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader3.visibility = View.GONE
//                                    appThumb3.visibility = View.VISIBLE
//                                    appThumb3.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                        Glide.with(applicationContext).load(mDataImageLink[3])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader4.visibility = View.GONE
//                                    appThumb4.visibility = View.VISIBLE
//                                    appThumb4.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                        Glide.with(applicationContext).load(mDataImageLink[4])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader5.visibility = View.GONE
//                                    appThumb5.visibility = View.VISIBLE
//                                    appThumb5.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                        Glide.with(applicationContext).load(mDataImageLink[5])
//                            .override(800, 800).into(object : CustomTarget<Drawable>() {
//                                override fun onResourceReady(
//                                    resource: Drawable,
//                                    transition: Transition<in Drawable>?
//                                ) {
//                                    mLoader6.visibility = View.GONE
//                                    appThumb6.visibility = View.VISIBLE
//                                    appThumb6.setImageDrawable(resource)
//                                }
//
//                                override fun onLoadCleared(placeholder: Drawable?) {
//                                }
//
//                            })
//                    } else {
//                        isCursor = false
////                        getAppData()
//                    }
//                } else {
//                    Log.d("nenenwnw", "onReceive11: internet off ")
//                    mainCl.visibility = View.GONE
//                    ctOfflineNew.visibility = View.VISIBLE
//
//
//                }
//            } else {
//                mainCl.visibility = View.GONE
//                ctOfflineNew.visibility = View.VISIBLE
//                Log.d("internet", "onReceive: isoffline")
//                isCalled = false
//            }
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(mNetworkReceiver)
//        unregisterReceiver(receiver)
//    }
//
//}