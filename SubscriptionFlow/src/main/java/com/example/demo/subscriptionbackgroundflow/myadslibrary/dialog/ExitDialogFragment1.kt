package com.example.demo.subscriptionbackgroundflow.myadslibrary.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.subscriptionbackgroundflow.R
import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.AppAdepter
import com.example.demo.subscriptionbackgroundflow.myadslibrary.classes.GridSpacingItemDecoration1
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.AppDatabase
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.AppModel
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.repository.DialogRepository
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection
import kotlinx.android.synthetic.main.dailog_layout_exit_fragment.recyclerViewApp
import kotlinx.android.synthetic.main.dailog_layout_exit_fragment.view.*
import kotlinx.android.synthetic.main.dailog_layout_exit_fragment1.*

class ExitDialogFragment1 : DialogFragment {

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var positive: String? = null
    private var negative: String? = null
    private var mListener: OnButtonClickListener? = null
    private val bottomSheetDialog: Dialog? = null
    private var titleImagel = 0

    private var drawable: MutableLiveData<Drawable>? = MutableLiveData()

    private var mAdepter: AppAdepter? = null
    private var isRemoveAds:Boolean = false


    constructor() {}

    constructor(mTitle: String?, message: String?, positive1: String?, negative1: String?, titleImage: Int, drawable: Drawable,isRemoveAds: Boolean, mListener: OnButtonClickListener?) {
        this.mTitle = mTitle
        this.mMessage = message
        positive = positive1
        negative = negative1
        this.mListener = mListener
        titleImagel = titleImage
        this.drawable?.value = drawable
        this.isRemoveAds = isRemoveAds
    }

    interface OnButtonClickListener {
        fun onPositive(exitDialogFragment: ExitDialogFragment1?)
        fun onNegative(exitDialogFragment: ExitDialogFragment1?)
        fun onDismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /* lifecycleScope.launch {
             var bit = withContext(Dispatchers.IO) {
                 CacheStore.createInstance(requireContext()).getCacheFile("MainBG")
             }
             Log.d(TAG, "onAttach: ${bit}")
             withContext(Dispatchers.Main) {
                 drawable?.value = BitmapDrawable(resources, bit)
             }
         }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(BottomSheetDialogFragment.STY    LE_NORMAL, R.style.materialButton);
        setStyle(STYLE_NORMAL, R.style.DialogFragmentTheme)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(TAG, "onDismiss: ")
        if (mListener != null) mListener!!.onDismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dailog_layout_exit_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = true

        Log.d(TAG, "onViewCreated: ${mMessage}")
        view.txtMessage?.text = mMessage
        view.txtTitle.text = mTitle

        val constraintLayout: ConstraintLayout = view.findViewById(R.id.cl_main_dialog)
        val myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce1)
        // Use bounce interpolator with amplitude 0.1 and frequency 15
        val interpolator = MyBounceInterpolator(0.1, 15.0)
        myAnim.interpolator = interpolator



        try {
            view.iv_dialog_logo.setImageDrawable(ContextCompat.getDrawable(requireContext(), titleImagel))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        view.btnPositive.setOnClickListener { mListener!!.onPositive(this) }
        view.btnNagative.setOnClickListener { mListener!!.onNegative(this) }

        recyclerViewApp?.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewApp?.addItemDecoration(GridSpacingItemDecoration1(3, dpToPx(requireContext(), 20).toInt(), true))
        recyclerViewApp?.itemAnimator = DefaultItemAnimator()

        val db = AppDatabase.getInstance(requireContext())

        val repository = DialogRepository(db)
        recyclerViewApp?.visibility = View.GONE

        val shared = requireContext().getSharedPreferences("app_center", Context.MODE_PRIVATE)

        if (InternetConnection.checkConnection(requireContext()) && !shared.getBoolean("subscribed", false)) {
            if(!isRemoveAds) {
                recyclerViewApp?.adapter = AppAdepter(requireContext(), ArrayList(), AppAdepter.OnAppClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                    dismiss()
                }, Color.BLACK).apply {
                    mAdepter = this
                }


                repository.getData().observe(requireActivity(), Observer {
                    if (it.isNotEmpty()) {
                        val list = ArrayList<AppModel>()
                        recyclerViewApp?.visibility = View.VISIBLE
                        it.forEach {
                            if (!it.app_link.contains("com.crop.photo.image.resize.cut.tools")) {
                                list.add(AppModel(
                                        it.position,
                                        it.name,
                                        it.app_link,
                                        it.image,
                                        it.is_trending
                                ))
                            }
                        }
                        mAdepter?.addData(list)
                    }
                })
            }

            //constraintLayout.startAnimation(myAnim)
            constraintLayout.scaleX = 1.6f
            constraintLayout.scaleY = 1.6f
            contentLayout.alpha = 0f
            contentLayout.animate().alpha(1f).setDuration(500).setInterpolator(DecelerateInterpolator()).start()
            constraintLayout.animate().scaleX(1f).scaleY(1f).setDuration(400).setInterpolator(DecelerateInterpolator()).start()

        } else {
            constraintLayout.scaleX = 1.6f
            constraintLayout.scaleY = 1.6f
            contentLayout.alpha = 0f
            contentLayout.animate().alpha(1f).setDuration(500).setInterpolator(DecelerateInterpolator()).start()
            constraintLayout.animate().scaleX(1f).scaleY(1f).setDuration(400).setInterpolator(DecelerateInterpolator()).start()

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*bottomSheetDialog = getDialog();
        // ((View) getView().getParent()).setPadding(0,0,0,);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        width -= width / 8;
        bottomSheetDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);*/
        /*if (drawable != null) {
            bottomSheetDialog.getWindow().setBackgroundDrawable(drawable);
        }*/
        // setColoredNavBar(true);
    }

    private fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        try {
            drawable?.observe(this, Observer {
                Log.d(TAG, "onStart: $it")
                if (it != null) {
                    rootDialog.background = it
                    //dialog.window!!.setBackgroundDrawable(it)
                } else {
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            })
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dialog
    }

    private fun setColoredNavBar(coloredNavigationBar: Boolean) {
        if (coloredNavigationBar && bottomSheetDialog!!.window != null && Build.VERSION.SDK_INT >= 21) {
            //if (isColorLight(Color.WHITE)) {
            bottomSheetDialog.window!!.navigationBarColor = Color.WHITE
            if (Build.VERSION.SDK_INT >= 26) {
                bottomSheetDialog.window!!.navigationBarColor = Color.WHITE
                var flags = bottomSheetDialog.window!!.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                bottomSheetDialog.window!!.decorView.systemUiVisibility = flags
            }
            /* } else {


                if (Build.VERSION.SDK_INT >= 26) {
                    int flags = bottomSheetDialog.getWindow().getDecorView().getSystemUiVisibility();
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    bottomSheetDialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                }
            }*/
        }
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (dialog != null && dialog!!.window != null) {
                val w = dialog!!.window
                /*w!!.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    w.statusBarColor = Color.TRANSPARENT
                }*/
                //StatusBarUtils.setTransparent(window, mActivity)
                w!!.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    w!!.statusBarColor = Color.TRANSPARENT
                }
            }
        }
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, height)
    }

    override fun onPause() {
        super.onPause()
        if(dialog!=null && dialog!!.isShowing){
            dialog!!.dismiss()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setWhiteNavigationBar(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)
            val dimDrawable = GradientDrawable()
            // ...customize your dim effect here
            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(Color.WHITE)
            val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
            val windowBackground = LayerDrawable(layers)
            windowBackground.setLayerInsetTop(1, metrics.heightPixels)
            window.setBackgroundDrawable(windowBackground)
            var flags = dialog.window!!.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.decorView.systemUiVisibility = flags
        }
    }

    private fun isColorLight(@ColorInt color: Int): Boolean {
        if (color == Color.BLACK) return false else if (color == Color.WHITE || color == Color.TRANSPARENT) return true
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.4
    }

    inner class MyBounceInterpolator(amplitude: Double, frequency: Double) : Interpolator {
        private var mAmplitude = 1.0
        private var mFrequency = 10.0
        override fun getInterpolation(time: Float): Float {
            return (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1).toFloat()
        }

        init {
            mAmplitude = amplitude
            mFrequency = frequency
        }
    }


    override fun dismiss() {
        try {
            //CacheStore.createInstance(requireContext()).clearCache("MainBG")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.dismiss()
    }


    companion object {
        private const val TAG = "ExitDialogFragment"
    }
}