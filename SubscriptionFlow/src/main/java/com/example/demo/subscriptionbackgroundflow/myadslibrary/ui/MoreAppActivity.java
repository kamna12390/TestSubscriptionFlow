//package com.example.demo.subscriptionbackgroundflow.myadslibrary.ui;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//import androidx.core.widget.NestedScrollView;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager.widget.ViewPager;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.GradientDrawable;
//import android.graphics.drawable.LayerDrawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.bumptech.glide.Glide;
//import com.google.android.material.appbar.AppBarLayout;
//import com.google.gson.Gson;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.Api.Api;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.dialog.FullscreenDialog;
//import com.example.demo.subscriptionbackgroundflow.R;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.MoreAppAdepter;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.SliderAdepter;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.helper.NativeHelper;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppDateModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.ResponseModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.receiver.NetworkChangeReceiver;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets.LockableNestedScrollView;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets.SlideViewPager;
//import com.takusemba.spotlight.OnSpotlightStateChangedListener;
//import com.takusemba.spotlight.OnTargetStateChangedListener;
//import com.takusemba.spotlight.Spotlight;
//import com.takusemba.spotlight.shape.Circle;
//import com.takusemba.spotlight.target.SimpleTarget;
//
//
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.ListIterator;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MoreAppActivity extends AppCompatActivity implements MoreAppAdepter.OnAppClickListener, MoreAppAdepter.OnAppLongClickListener {
//
//    private static final String TAG = "MoreAppActivity";
//
//
//    //widgets
//    private SlideViewPager slider;
//    private RecyclerView mAppListRv, mNewReleaseList;
//    private ImageButton imgBtnBack, imgBtnShare;
//    private LockableNestedScrollView mNestedScrollView;
//    private ConstraintLayout ctOffline, ctError, clLoading;
//    private ConstraintLayout coordinatorLayout;
//    //object
//    private Context mContext;
//    private SliderAdepter sliderAdepter;
//    private MoreAppAdepter adepter, newAdpeter;
//    private Handler handler;
//    private Runnable runnable;
//    private AlertDialog alertDialog;
//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
//    private Receiver receiver;
//    private NetworkChangeReceiver mNetworkReceiver;
//
//    //var
//    private ArrayList<AppModel> mList;
//    private ArrayList<AppModel> mOriginallist;
//    private ArrayList<AppModel> mNewRelease;
//
//
//    private int currentPage = 0;
//    private ArrayList<AppDateModel> mAppDateList;
//    private boolean isCalled;
//    private int oldScrollX;
//    private int oldScrollY;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_more_app);
//
//        //set Light Dark StatusBar
//        // setLightStatusBar(getWindow().getDecorView(), this);
//        transparentStatusAndNavigation();
//
//
//        findViewById(R.id.appBar).setPadding(0, getStatusBarHeight(), 0, 0);
//
//        //init Receiver
//        receiver = new Receiver();
//        mNetworkReceiver = new NetworkChangeReceiver();
//
//        registerReceiver(mNetworkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
//        registerReceiver(receiver, new IntentFilter("com.example.demo.subscriptionbackgroundflow.myadslibrary.custom"));
//
//        mContext = this;
//
//        mSharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
//        mEditor = mSharedPreferences.edit();
//
//        initView();
//
//        initListener();
//
//        initAction();
//
//
//    }
//
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }
//
//    private void initListener() {
//
//        findViewById(R.id.appBar).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//
//
//        imgBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        imgBtnShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Image Crop");
//                    String shareMessage = "\nTry Image Crop for Crop Images and Videos with amazing features Download app now from given link\n\n";
//                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.crop.photo.image.resize.cut.tools&hl=en";
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                    startActivity(Intent.createChooser(shareIntent, "Choose One"));
//                } catch (Exception e) {
//                    //e.toString();
//                }
//            }
//        });
//
//        findViewById(R.id.txtRetry).setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("StaticFieldLeak")
//            @Override
//            public void onClick(View view) {
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        Log.d(TAG, "doInBackground: " + InternetConnection.checkConnection(mContext));
//                        if (InternetConnection.checkConnection(mContext)) {
//                            getAppData();
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, "Please turn on internet.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                        return null;
//                    }
//
//                }.execute();
//            }
//        });
//
//        findViewById(R.id.txtRetry1).setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("StaticFieldLeak")
//            @Override
//            public void onClick(View view) {
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        Log.d(TAG, "doInBackground: " + InternetConnection.checkConnection(mContext));
//                        if (InternetConnection.checkConnection(mContext)) {
//                            getAppData();
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, "Please turn on internet.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                        return null;
//                    }
//
//                }.execute();
//            }
//        });
//
//
//    }
//
//    public static void setLightStatusBar(View view, Activity activity) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            int flags = view.getSystemUiVisibility();
//            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            view.setSystemUiVisibility(flags);
//            activity.getWindow().setStatusBarColor(Color.WHITE);
//        }
//    }
//
//    private void initView() {
//        slider = findViewById(R.id.adSlider);
//        mAppListRv = findViewById(R.id.appList);
//        mNewReleaseList = findViewById(R.id.newReleaseAppList);
//        imgBtnBack = findViewById(R.id.imgBtnBack);
//        imgBtnShare = findViewById(R.id.imgBtnShare);
//        mNestedScrollView = findViewById(R.id.nestedScrollview);
//        ctOffline = findViewById(R.id.ctOffline);
//        ctError = findViewById(R.id.ctError);
//        clLoading = findViewById(R.id.cl_loading);
//
//    }
//
//
//    @SuppressLint("StaticFieldLeak")
//    private void initAction() {
//
//        mList = new ArrayList<>();
//        mOriginallist = new ArrayList<>();
//        mAppDateList = new ArrayList<>();
//        mNewRelease = new ArrayList<>();
//
//
//        ctOffline.setVisibility(View.GONE);
//        ctError.setVisibility(View.GONE);
//
//        Glide.with(this).load(R.drawable.ads_bg).override(400).into((ImageView) findViewById(R.id.imageBackground));
//
//        if (!InternetConnection.checkConnection(this)) {
//            ctOffline.setVisibility(View.VISIBLE);
//            clLoading.setVisibility(View.GONE);
//            mNestedScrollView.setVisibility(View.GONE);
//            mNestedScrollView.setSmoothScrollingEnabled(false);
//            mNestedScrollView.setScrollingEnabled(false);
//        }
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                if (InternetConnection.checkConnection(mContext)) {
//                    isCalled = true;
//                    getAppData();
//                }
//                return null;
//            }
//
//        }.execute();
//
//        ((ImageView) findViewById(R.id.iv_first)).setImageResource(R.drawable.ad_ic_dot_select);
//        ((ImageView) findViewById(R.id.iv_first)).setColorFilter(ContextCompat.getColor(mContext, R.color.colorPick));
//
//        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//                if (position == 0) {
//
//                    ((ImageView) findViewById(R.id.iv_first)).setImageResource(R.drawable.ad_ic_dot_select);
//                    ((ImageView) findViewById(R.id.iv_first)).setColorFilter(ContextCompat.getColor(mContext, R.color.colorPick));
//                    ((ImageView) findViewById(R.id.iv_second)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_second)).clearColorFilter();
//                    ((ImageView) findViewById(R.id.iv_last)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_last)).clearColorFilter();
//                } else if (position == 1) {
//                    ((ImageView) findViewById(R.id.iv_first)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_first)).clearColorFilter();
//                    ((ImageView) findViewById(R.id.iv_second)).setImageResource(R.drawable.ad_ic_dot_select);
//                    ((ImageView) findViewById(R.id.iv_second)).setColorFilter(ContextCompat.getColor(mContext, R.color.colorPick));
//                    ((ImageView) findViewById(R.id.iv_last)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_last)).clearColorFilter();
//                } else {
//                    ((ImageView) findViewById(R.id.iv_first)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_first)).clearColorFilter();
//                    ((ImageView) findViewById(R.id.iv_second)).setImageResource(R.drawable.ad_ic_dot);
//                    ((ImageView) findViewById(R.id.iv_second)).clearColorFilter();
//                    ((ImageView) findViewById(R.id.iv_last)).setImageResource(R.drawable.ad_ic_dot_select);
//                    ((ImageView) findViewById(R.id.iv_last)).setColorFilter(ContextCompat.getColor(mContext, R.color.colorPick));
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//
//
//        /*slider.setClipChildren(false);
//        slider.setClipToPadding(false);
//        int margin = (int) getResources().getDimension(R.dimen.d_margin_large);//16dp
//        final int padding = (int) getResources().getDimension(R.dimen.d_margin_ex_large);//18dp
//        int total = margin + padding;
//        slider.setPageMargin(margin);
//        slider.setPadding(padding * 2, 0, padding * 2, 0);
//        slider.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                if (slider.getCurrentItem() == 0) {
//                    page.setTranslationX(-(padding));
//                } else if (slider.getCurrentItem() == sliderAdepter.getCount() - 1) {
//                    page.setTranslationX(padding);
//                } else {
//                    page.setTranslationX(0);
//                }
//            }
//        });*/
//
//        //getDummyData();
//
//
//        if (mSharedPreferences.getBoolean("isShowSpotlight", true)) {
//            mNestedScrollView.setEnabled(false);
//        }
//
//
//        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                if (scrollY > oldScrollY) {
//                    Log.i(TAG, "Scroll DOWN");
//                    if ((getStatusBarHeight() / 2.5) < scrollY) {
//                        imgBtnBack.setVisibility(View.INVISIBLE);
//                        imgBtnShare.setVisibility(View.INVISIBLE);
//                        findViewById(R.id.txtToolbarTitle).setVisibility(View.INVISIBLE);
//                    }
//                }
//                if (scrollY < oldScrollY) {
//                    Log.i(TAG, "Scroll UP");
//                    imgBtnBack.setVisibility(View.VISIBLE);
//                    imgBtnShare.setVisibility(View.VISIBLE);
//                    findViewById(R.id.txtToolbarTitle).setVisibility(View.VISIBLE);
//
//                }
//
//                if (scrollY == 0) {
//                    Log.i(TAG, "TOP SCROLL");
//                }
//
//                if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {
//                    Log.i(TAG, "BOTTOM SCROLL");
//                }
//            }
//        });
//
//       /* mNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//
//            *//*    if (mNestedScrollView.getScrollY() > oldScrollY) {
//                    Log.i(TAG, "Scroll DOWN");
//                }
//                if (mNestedScrollView.getScrollY() <= oldScrollY) {
//                    Log.i(TAG, "Scroll UP");
//                }*//*
//
//                Log.d(TAG, "onScrollChanged: " + mNestedScrollView.getScrollY() + " = "  );
//
//
//                if ((getStatusBarHeight() / 2.5) < mNestedScrollView.getScrollY()) {
//                    imgBtnBack.setVisibility(View.INVISIBLE);
//                    imgBtnShare.setVisibility(View.INVISIBLE);
//                    findViewById(R.id.txtToolbarTitle).setVisibility(View.INVISIBLE);
//                }
//
//                if (mNestedScrollView.getScrollY() <= oldScrollY) {
//                    Log.i(TAG, "Scroll UP");
//                    imgBtnBack.setVisibility(View.VISIBLE);
//                    imgBtnShare.setVisibility(View.VISIBLE);
//                    findViewById(R.id.txtToolbarTitle).setVisibility(View.VISIBLE);
//                }
//
//                View view = (View) mNestedScrollView.getChildAt(mNestedScrollView.getChildCount() - 1);
//
//                int diff = (view.getBottom() - (mNestedScrollView.getHeight() + mNestedScrollView.getScrollY()));
//
//                if (diff == 0) {
//                    Log.d(TAG, "onScrollChanged: ");
//                }
//
//                oldScrollX = mNestedScrollView.getScrollX();
//                oldScrollY = mNestedScrollView.getScrollY();
//            }
//        });*/
//    }
//
//    private void transparentStatusAndNavigation() {
//        //make full transparent statusBar
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            );
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void setWhiteNavigationBar() {
//        Window window = getWindow();
//        if (window != null) {
//            DisplayMetrics metrics = new DisplayMetrics();
//            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            GradientDrawable dimDrawable = new GradientDrawable();
//            // ...customize your dim effect here
//            GradientDrawable navigationBarDrawable = new GradientDrawable();
//            navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
//            navigationBarDrawable.setColor(Color.WHITE);
//            Drawable[] layers = new Drawable[]{dimDrawable, navigationBarDrawable};
//
//            LayerDrawable windowBackground = new LayerDrawable(layers);
//            windowBackground.setLayerInsetTop(1, metrics.heightPixels);
//            window.setBackgroundDrawable(windowBackground);
//            //int flags = getWindow().getDecorView().getSystemUiVisibility();
//            int flags = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
//            window.getDecorView().setSystemUiVisibility(flags);
//        }
//    }
//
//    private void setWindowFlag(final int bits, boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
//
//    private void getDummyData() {
//        mList.add(null);
//        mList.add(null);
//        mList.add(null);
//        mList.add(null);
//        mList.add(null);
//        mList.add(null);
//
//        mNewRelease.add(null);
//        mNewRelease.add(null);
//        mNewRelease.add(null);
//    }
//
//    private void getAppData() {
//
//        Log.d(TAG, "getAppData: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mNestedScrollView.setSmoothScrollingEnabled(false);
//                mNestedScrollView.setScrollingEnabled(false);
//                ctOffline.setVisibility(View.GONE);
//                ctError.setVisibility(View.GONE);
//                clLoading.setVisibility(View.VISIBLE);
//                mNestedScrollView.setVisibility(View.VISIBLE);
//                mNestedScrollView.setScrollingEnabled(false);
//
//            }
//        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Call<ResponseModel> call = Api.getClient().getAllApp();
//            call.enqueue(new Callback<ResponseModel>() {
//                @Override
//                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                    Log.d(TAG, "onResponse: ");
//
//                    mList.clear();
//                    mNewRelease.clear();
//                    ResponseModel model = response.body();
//
//                    if (model != null && model.getData() != null) {
//
//                        mList = model.getData();
//                        if (mOriginallist == null)
//                            mOriginallist = new ArrayList<>();
//                        mOriginallist.clear();
//                        mOriginallist.addAll(model.getData());
//
//
//                        for (int i = 0; i < mList.size(); i++) {
//                            AppModel appModel = mList.get(i);
//                            if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                                mList.remove(appModel);
//                                break;
//                            }
//                        }
//
//                        for (int i = 0; i < mOriginallist.size(); i++) {
//                            AppModel appModel = mOriginallist.get(i);
//                            if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                                mOriginallist.remove(appModel);
//                                break;
//                            }
//                        }
//
//
//                        mNewRelease.clear();
//                        for (int i = 0; i < mList.size(); i++) {
//                            //mAppDateList.add(new AppDateModel(mList.get(i).getId(), mList.get(i).getApp_updated(), "", ""));
//                            if (mList.get(i).is_trending())
//                                mNewRelease.add(mList.get(i));
//                        }
//
//                        removeTrendingApp(mList, 0);
//
//                        if (mNewRelease.size() == 2) {
//                            mNewRelease.add(mList.get(0));
//                            mList.remove(0);
//                        }
//
//                        updateUI();
//                        //new ValidTask().execute();
//                        // new GetVersionCode().execute(i);
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mNestedScrollView.setSmoothScrollingEnabled(true);
//                                clLoading.setVisibility(View.GONE);
//                                ctOffline.setVisibility(View.GONE);
//                                ctError.setVisibility(View.VISIBLE);
//                                mNestedScrollView.setVisibility(View.GONE);
//                            }
//                        });
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseModel> call, Throwable t) {
//                    Log.d(TAG, "onFailure: " + t.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            mNestedScrollView.setSmoothScrollingEnabled(true);
//                            clLoading.setVisibility(View.GONE);
//                            ctOffline.setVisibility(View.GONE);
//                            ctError.setVisibility(View.GONE);
//                            mNestedScrollView.setVisibility(View.GONE);
//                            if (!InternetConnection.checkConnection(mContext))
//                                ctOffline.setVisibility(View.VISIBLE);
//                            else
//                                ctError.setVisibility(View.VISIBLE);
//                        }
//                    });
//                }
//            });
//        } else {
//            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//
//            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, new NativeHelper().getAppBaseUrl() + "more_app", null,
//                    new com.android.volley.Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            if (response != null) {
//                                try {
//                                    ResponseModel model = new Gson().fromJson(response.toString(), ResponseModel.class);
//                                    mList.clear();
//                                    mNewRelease.clear();
//                                    if (model != null && model.getData() != null) {
//
//                                        mList = model.getData();
//                                        if (mOriginallist == null)
//                                            mOriginallist = new ArrayList<>();
//                                        mOriginallist.clear();
//                                        mOriginallist.addAll(model.getData());
//
//
//                                        for (int i = 0; i < mList.size(); i++) {
//                                            AppModel appModel = mList.get(i);
//                                            if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                                                mList.remove(appModel);
//                                                break;
//                                            }
//                                        }
//
//                                        for (int i = 0; i < mOriginallist.size(); i++) {
//                                            AppModel appModel = mOriginallist.get(i);
//                                            if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                                                mOriginallist.remove(appModel);
//                                                break;
//                                            }
//                                        }
//
//
//                                        mNewRelease.clear();
//                                        for (int i = 0; i < mList.size(); i++) {
//                                            //mAppDateList.add(new AppDateModel(mList.get(i).getId(), mList.get(i).getApp_updated(), "", ""));
//                                            if (mList.get(i).is_trending())
//                                                mNewRelease.add(mList.get(i));
//                                        }
//
//                                        removeTrendingApp(mList, 0);
//
//                                        if (mNewRelease.size() == 2) {
//                                            mNewRelease.add(mList.get(0));
//                                            mList.remove(0);
//                                        }
//
//                                        updateUI();
//                                        //new ValidTask().execute();
//                                        // new GetVersionCode().execute(i);
//                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mNestedScrollView.setSmoothScrollingEnabled(true);
//                                                clLoading.setVisibility(View.GONE);
//                                                ctOffline.setVisibility(View.GONE);
//                                                ctError.setVisibility(View.VISIBLE);
//                                                mNestedScrollView.setVisibility(View.GONE);
//                                            }
//                                        });
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mNestedScrollView.setSmoothScrollingEnabled(true);
//                                        clLoading.setVisibility(View.GONE);
//                                        ctOffline.setVisibility(View.GONE);
//                                        ctError.setVisibility(View.VISIBLE);
//                                        mNestedScrollView.setVisibility(View.GONE);
//                                    }
//                                });
//                            }
//                        }
//                    }, new com.android.volley.Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.e("Error: ", error.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mNestedScrollView.setSmoothScrollingEnabled(true);
//                            clLoading.setVisibility(View.GONE);
//                            ctOffline.setVisibility(View.GONE);
//                            ctError.setVisibility(View.VISIBLE);
//                            mNestedScrollView.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
//        }
//        /*Call<ResponseModel> call = Api.getClient().getAllApp();
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                Log.d(TAG, "onResponse: ");
//
//                mList.clear();
//                mNewRelease.clear();
//                ResponseModel model = response.body();
//
//                if (model != null && model.getData() != null) {
//
//                    mList = model.getData();
//                    if (mOriginallist == null)
//                        mOriginallist = new ArrayList<>();
//                    mOriginallist.clear();
//                    mOriginallist.addAll(model.getData());
//
//
//                    for (int i = 0; i < mList.size(); i++) {
//                        AppModel appModel = mList.get(i);
//                        if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                            mList.remove(appModel);
//                            break;
//                        }
//                    }
//
//                    for (int i = 0; i < mOriginallist.size(); i++) {
//                        AppModel appModel = mOriginallist.get(i);
//                        if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                            mOriginallist.remove(appModel);
//                            break;
//                        }
//                    }
//
//
//                    mNewRelease.clear();
//                    for (int i = 0; i < mList.size(); i++) {
//                        //mAppDateList.add(new AppDateModel(mList.get(i).getId(), mList.get(i).getApp_updated(), "", ""));
//                        if (mList.get(i).is_trending())
//                            mNewRelease.add(mList.get(i));
//                    }
//
//                    removeTrendingApp(mList,0);
//
//                    if (mNewRelease.size() == 2) {
//                        mNewRelease.add(mList.get(0));
//                        mList.remove(0);
//                    }
//
//                    updateUI();
//                    //new ValidTask().execute();
//                    // new GetVersionCode().execute(i);
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mNestedScrollView.setSmoothScrollingEnabled(true);
//                            clLoading.setVisibility(View.GONE);
//                            ctOffline.setVisibility(View.GONE);
//                            ctError.setVisibility(View.VISIBLE);
//                            mNestedScrollView.setVisibility(View.GONE);
//                        }
//                    });
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        mNestedScrollView.setSmoothScrollingEnabled(true);
//                        clLoading.setVisibility(View.GONE);
//                        ctOffline.setVisibility(View.GONE);
//                        ctError.setVisibility(View.GONE);
//                        mNestedScrollView.setVisibility(View.GONE);
//                        if (!InternetConnection.checkConnection(mContext))
//                            ctOffline.setVisibility(View.VISIBLE);
//                        else
//                            ctError.setVisibility(View.VISIBLE);
//                    }
//                });
//            }
//        });*/
//    }
//
//    private void removeTrendingApp(ArrayList<AppModel> mList, int i) {
//        Log.d(TAG, "removeTrendingApp: " + mList.size() + " = " + i);
//        if (mList.get(i).is_trending()) {
//            mList.remove(i);
//            i--;
//        }
//        i++;
//        if (i == mList.size() - 1) return;
//        removeTrendingApp(mList, i);
//    }
//
//    private void updateUI() {
//        mAppDateList.clear();
//        //newAdpeter.addApp(mNewRelease);
//
//        //adepter.addApp(mList);
//        mNestedScrollView.setScrollingEnabled(true);
//
//        Toolbar toolbar = findViewById(R.id.toolBar);
//        AppBarLayout.LayoutParams params =
//                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
//
//        toolbar.setLayoutParams(params);
//
//        clLoading.setVisibility(View.GONE);
//
//        mAppListRv.setLayoutManager(new GridLayoutManager(mContext, 3));
//        mAppListRv.setItemAnimator(new DefaultItemAnimator());
//        adepter = new MoreAppAdepter(mContext, mList, MoreAppActivity.this, Color.BLACK, MoreAppActivity.this);
//        //mAppListRv.setNestedScrollingEnabled(false);
//        mAppListRv.setAdapter(adepter);
//
//        mNewReleaseList.setLayoutManager(new GridLayoutManager(mContext, 3));
//        mNewReleaseList.setItemAnimator(new DefaultItemAnimator());
//        newAdpeter = new MoreAppAdepter(mContext, mNewRelease, MoreAppActivity.this, Color.BLACK, MoreAppActivity.this);
//        //mNewReleaseList.setNestedScrollingEnabled(false);
//        mNewReleaseList.setAdapter(newAdpeter);
//
//        sliderAdepter = new SliderAdepter(getSupportFragmentManager(), mOriginallist, false);
//        slider.setAdapter(sliderAdepter);
//        startAutoSlider();
//    }
//
//    private void startAutoSlider() {
//
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                currentPage++;
//                if (currentPage >= sliderAdepter.getCount()) {
//                    currentPage = 0;
//                }
//                slider.setCurrentItem(currentPage);
//                if (handler != null && runnable != null) {
//                    handler.postDelayed(runnable, 5000);
//                }
//            }
//        };
//
//        handler.postDelayed(runnable, 5000);
//
//    }
//
//    @Override
//    public void onAppClick(AppModel model) {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getApp_link())));
//    }
//
//    @Override
//    public void onAppLongClick(AppModel model) {
//
//        showViewPagerDialog(model);
//    }
//
//    private void showViewPagerDialog(AppModel model) {
//
//        FullscreenDialog dialog = new FullscreenDialog(model);
//        dialog.setCancelable(true);
//        dialog.show(getSupportFragmentManager(), "Dialog");
//
//
//     /*   ViewGroup viewGroup = findViewById(android.R.id.content);
//
//        //then we will inflate the custom alert dialog xml that we created
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_image_viewer_dialog, viewGroup, false);
//
//
//        //Now we need an AlertDialog.Builder object
//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.full_screen_dialog);
//
//        //setting the view of the builder to our custom view that we already inflated
//        builder.setView(dialogView);
//
//        ImageButton imgBtnClose = dialogView.findViewById(R.id.imgBtnClose);
//        imgBtnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog = builder.create();
//        alertDialog.show();*/
//    }
//
//
//    public class Receiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, "onReceive: ads ");
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    if (InternetConnection.checkConnection(mContext) && !isCalled) {
//                        getAppData();
//                    } else {
//                        isCalled = false;
//                    }
//                    return null;
//                }
//
//            }.execute();
//        }
//    }
//
//    /*private class GetVersionCode extends AsyncTask<Integer, String, String> {
//        @Override
//        protected String doInBackground(Integer... integers) {
//
//            String newVersion = null;
//            try {
//                ListIterator<Element> elementListIterator = Jsoup.connect("https://play.google.com/store/apps/details?id=" + mList.get(integers[0]).getPackage_name() + "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select(".hAyfc .htlgb")
//                        .listIterator();
//
//                String data = "";
//                while (elementListIterator.hasNext()) {
//                    String str = elementListIterator.next().ownText();
//                    Log.d(TAG, "doInBackground: " + str);
//                    if (!str.trim().equals("")) {
//                        data += str + "!";
//                    }
//                }
//
//
//                Log.d(TAG, "doInBackground: " + data);
//                String[] appInfo = data.split("!");
//                mAppDateList.add(new AppDateModel(mList.get(integers[0]).getId(), appInfo[0], appInfo[1], appInfo[2]));
//                return newVersion;
//            } catch (Exception e) {
//                return newVersion;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String onlineVersion) {
//            super.onPostExecute(onlineVersion);
//            Log.d("update", "Current version  " + mAppDateList.size());
//           *//* if (mList.size() == mAppDateList.size()) {
//                new ValidTask().execute();
//            }*//*
//
//        }
//    }
//
//    public class ValidTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            //dataValidation();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Log.d(TAG, "onPostExecute: ");
//            mAppDateList.clear();
//            //newAdpeter.addApp(mNewRelease);
//
//            //adepter.addApp(mList);
//            mNestedScrollView.setScrollingEnabled(true);
//
//            Toolbar toolbar = findViewById(R.id.toolBar);
//            AppBarLayout.LayoutParams params =
//                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
//
//            toolbar.setLayoutParams(params);
//
//            clLoading.setVisibility(View.GONE);
//
//            mAppListRv.setLayoutManager(new GridLayoutManager(mContext, 3));
//            mAppListRv.setItemAnimator(new DefaultItemAnimator());
//            adepter = new MoreAppAdepter(mContext, mList, MoreAppActivity.this, Color.BLACK, MoreAppActivity.this);
//            //mAppListRv.setNestedScrollingEnabled(false);
//            mAppListRv.setAdapter(adepter);
//
//            mNewReleaseList.setLayoutManager(new GridLayoutManager(mContext, 3));
//            mNewReleaseList.setItemAnimator(new DefaultItemAnimator());
//            newAdpeter = new MoreAppAdepter(mContext, mNewRelease, MoreAppActivity.this, Color.BLACK, MoreAppActivity.this);
//            //mNewReleaseList.setNestedScrollingEnabled(false);
//            mNewReleaseList.setAdapter(newAdpeter);
//
//           *//* mNewReleaseList.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (mSharedPreferences.getBoolean("isShowSpotlight", true)) {
//                        mNestedScrollView.setEnabled(false);
//                        View view = mNewReleaseList.getLayoutManager().findViewByPosition(1);
//                        final int[] originalPos = new int[2];
//                        if (view == null) {
//                            while (view == null) {
//                                view = mNewReleaseList.getLayoutManager().findViewByPosition(1);
//                            }
//                        }
//                        view.getLocationInWindow(originalPos);
//                        final int x = originalPos[0] + view.getWidth() / 2;
//                        final int y = originalPos[1] + view.getHeight() / 2 - 50;
//
//                        Log.d(TAG, "onPostExecute: " + x + " = " + y);
//
//                        final SimpleTarget simpleTarget = new SimpleTarget.Builder(MoreAppActivity.this)
//                                .setPoint(x, y)
//                                .setShape(new Circle(200f)) // or RoundedRectangle()
//                                .setTitle("Les't Start")
//                                .setDescription("Long Press to open Images")
//                                .setOverlayPoint(originalPos[0] - 50, originalPos[1] + view.getHeight() + 10)
//                                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
//                                    @Override
//                                    public void onStarted(SimpleTarget target) {
//                                        // do something
//
//                                    }
//
//                                    @Override
//                                    public void onEnded(SimpleTarget target) {
//                                        // do something
//                                    }
//                                })
//                                .build();
//
//
//                        final Spotlight spotlight = Spotlight.with(MoreAppActivity.this)
//                                .setOverlayColor(R.color.background)
//                                .setDuration(1000L)
//                                .setAnimation(new DecelerateInterpolator(2f))
//                                .setTargets(simpleTarget)
//                                .setClosedOnTouchedOutside(false)
//                                .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
//                                    @Override
//                                    public void onStarted() {
//                                        mNestedScrollView.setEnabled(true);
//                                    }
//
//                                    @Override
//                                    public void onEnded() {
//                                    }
//                                });
//                        spotlight.start();
//
//
//                        simpleTarget.getOverlay().setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                                float x1 = motionEvent.getX() - x;
//                                float y1 = motionEvent.getY() - y;
//                                float distance_pow_2 = x1 * x1 + y1 * y1;
//                                if (distance_pow_2 <= (200f) * (200f)) {
//                                    spotlight.closeSpotlight();
//                                    onAppLongClick(mNewRelease.get(1));
//                                    mEditor.putBoolean("isShowSpotlight", false);
//                                    mEditor.commit();
//
//                                }
//
//                                return false;
//                            }
//                        });
//                    }
//                }
//            });*//*
//
//
//            Log.d(TAG, "run: ");
//            sliderAdepter = new SliderAdepter(getSupportFragmentManager(), mList, false);
//            slider.setAdapter(sliderAdepter);
//            startAutoSlider();
//
//
//        }
//    }
//
//    private void dataValidation() {
//
//        try {
//            Log.d(TAG, "dataValidation: ");
//            for (int i = 0; i < mAppDateList.size(); i++) {
//                AppDateModel model = mAppDateList.get(i);
//                String data = model.getDate().replace(",", "");
//                String[] dateArray = data.split(" ");
//                int month = getMonth(dateArray[0]);
//                String date = dateArray[1] + "-" + String.valueOf(month) + "-" + dateArray[2];
//                model.setDate(date);
//
//                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//                try {
//                    Date date1 = (Date) formatter.parse(date);
//                    model.setmDate(date1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            Log.d(TAG, "dataValidation: ");
//
//            Collections.sort(mAppDateList, new Comparator<AppDateModel>() {
//                @Override
//                public int compare(AppDateModel appDateModel, AppDateModel t1) {
//                    return t1.getmDate().compareTo(appDateModel.getmDate());
//                }
//            });
//            Log.d(TAG, "dataValidation: ");
//
//            int[] indexs = new int[3];
//            mNewRelease.clear();
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < mList.size(); j++) {
//                    if (mList.get(j).getId() == mAppDateList.get(i).getAppId()) {
//                        mNewRelease.add(mList.get(j));
//                        indexs[i] = j;
//                        mList.remove(j);
//                        break;
//                    }
//                }
//            }
//
//            Log.d(TAG, "dataValidation: ");
//           *//* mList.remove(indexs[0]);
//            mList.remove(indexs[1]);
//            mList.remove(indexs[2]);*//*
//
//        } catch (Exception e) {
//            e.printStackTrace();
//           *//* new AsyncTask<Void,Void,Void>(){
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    getAppData();
//                    return null;
//                }
//            }.execute();*//*
//        }
//    }*/
//
//
//    private int getMonth(String month) {
//        switch (month.toLowerCase()) {
//            case "january":
//                return 1;
//            case "february":
//                return 2;
//            case "march":
//                return 3;
//            case "april":
//                return 4;
//            case "may":
//                return 5;
//            case "june":
//                return 6;
//            case "july":
//                return 7;
//            case "august":
//                return 8;
//            case "september":
//                return 9;
//            case "october":
//                return 10;
//            case "november":
//                return 11;
//            case "december":
//                return 12;
//            default:
//                return 0;
//
//        }
//    }
//
//    private class GetImageUrl extends AsyncTask<Void, String, String> {
//
//        private AppModel appModel;
//
//        public GetImageUrl(AppModel appModel) {
//            this.appModel = appModel;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            String newVersion = null;
//            try {
//                newVersion = Jsoup.connect(appModel.getApp_link())
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select(".Q4vdJd")
//                        .html();
//
//
//                Document doc = Jsoup.parse(newVersion);
//                //select the divs
//                Elements divs = doc.select("img");
//
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int height = displayMetrics.heightPixels;
//                int width = displayMetrics.widthPixels;
//                ArrayList<String> mArray = new ArrayList<>();
//                for (Element e : divs) {
//                    if (e.attr("src").startsWith("https")) {
//                        String url = e.attr("src");
//                        url = getNewUrl(url, width, height);
//
//                        mArray.add(url);
//                        Log.d(TAG, "doInBackground: as " + url);
//                    }
//                    if (e.attr("data-src").startsWith("https")) {
//                        String url = e.attr("data-src");
//                        url = getNewUrl(url, width, height);
//
//                        mArray.add(url);
//                        Log.d(TAG, "doInBackground: as " + url);
//                    }
//                }
//                appModel.setmUrls(mArray);
//                return newVersion;
//            } catch (Exception e) {
//                return newVersion;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String onlineVersion) {
//            super.onPostExecute(onlineVersion);
//
//
//        }
//    }
//
//    private String getNewUrl(String url, int width, int height) {
//        String[] urlArr = url.split("=");
//        String strwh = urlArr[urlArr.length - 1];
//        String[] wh = strwh.split("-");
//
//        String str = url.replace(wh[0], "w" + String.valueOf(width));
//        str = str.replace(wh[1], "w" + String.valueOf(height));
//
//        return str;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(mNetworkReceiver);
//        unregisterReceiver(receiver);
//    }
//
//}
