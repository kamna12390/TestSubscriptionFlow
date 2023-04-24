//package com.example.demo.subscriptionbackgroundflow.myadslibrary.ui;
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
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.google.gson.Gson;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.Api.Api;
//import com.example.demo.subscriptionbackgroundflow.R;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.MoreAppAdepter;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.dialog.FullscreenDialog;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.helper.NativeHelper;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppDateModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.ResponseModel;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.receiver.NetworkChangeReceiver;
//import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MoreAppActivityNew extends AppCompatActivity implements MoreAppAdepter.OnAppClickListener, MoreAppAdepter.OnAppLongClickListener {
//
//    private static final String TAG = "MoreAppActivityNew";
//
//
//    //widgets
//    private RecyclerView mAppListRv;
//    private ImageButton imgBtnBack, imgBtnShare;
//    private ImageView mImg1,mImg2,mImg3,mImg4,mImg5,mImg6;
//    private ConstraintLayout ctOffline, ctError, clLoading, mCLContainer;
//    //object
//    private Context mContext;
//    private MoreAppAdepter adepter;
//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
//    private Receiver receiver;
//    private NetworkChangeReceiver mNetworkReceiver;
//
//    //var
//    private ArrayList<AppModel> mList;
//    private ArrayList<AppModel> mOriginallist;
//
//    private ArrayList<AppDateModel> mAppDateList;
//    private boolean isCalled;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_more_app_new);
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
//    public void setImage() {
//        Log.d(TAG, "setImage: cl"+mCLContainer.getChildCount());
//
//        for (int i = 0; i < mCLContainer.getChildCount(); i++) {
//
//            ConstraintLayout cl = (ConstraintLayout) mCLContainer.getChildAt(i);
//            Log.d(TAG, "setImage: cl"+cl.getChildCount());
//
//            for (int j = 0; j < cl.getChildCount(); j++) {
//
//                CardView card = (CardView) cl.getChildAt(j);
//                Log.d(TAG, "setImage: card"+card.getChildCount());
//
//                for (int k = 0; k < card.getChildCount(); k++) {
//
//                    ImageView img = (ImageView) card.getChildAt(k);
//
//
//                    Glide.with(mContext)
//                            .load(mList.get(i).getImage())
//                            .override(500, 500)
//                            .listener(new RequestListener<Drawable>() {
//                                @Override
//                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//
//                                    return false;
//                                }
//                            }).into(img);
//
//                }
//
//            }
//        }
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
//        imgBtnBack.setOnClickListener(view -> onBackPressed());
//
//        imgBtnShare.setOnClickListener(view -> {
//            try {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Image Crop");
//                String shareMessage = "\nTry Image Crop for Crop Images and Videos with amazing features Download app now from given link\n\n";
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.crop.photo.image.resize.cut.tools&hl=en";
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                startActivity(Intent.createChooser(shareIntent, "Choose One"));
//            } catch (Exception e) {
//                //e.toString();
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
//        mAppListRv = findViewById(R.id.appList);
//        imgBtnBack = findViewById(R.id.imgBtnBack);
//        mImg1 = findViewById(R.id.mImg1);
//        mImg2 = findViewById(R.id.mImg2);
//        mImg3 = findViewById(R.id.mImg3);
//        mImg4 = findViewById(R.id.mImg4);
//        mImg5 = findViewById(R.id.mImg5);
//        mImg6 = findViewById(R.id.mImg6);
//        imgBtnShare = findViewById(R.id.imgBtnShare);
//        ctOffline = findViewById(R.id.ctOffline);
//        ctError = findViewById(R.id.ctError);
//        clLoading = findViewById(R.id.cl_loading);
//        mCLContainer = findViewById(R.id.mCLContainer);
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
//
//
//        ctOffline.setVisibility(View.GONE);
//        ctError.setVisibility(View.GONE);
//
////        Glide.with(this).load(R.drawable.ads_bg).override(400).into((ImageView) findViewById(R.id.imageBackground));
//
//        if (!InternetConnection.checkConnection(this)) {
//            ctOffline.setVisibility(View.VISIBLE);
//            clLoading.setVisibility(View.GONE);
//            mAppListRv.setVisibility(View.GONE);
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
//
//        if (mSharedPreferences.getBoolean("isShowSpotlight", true)) {
//            mAppListRv.setEnabled(false);
//        }
//
//
//    }
//
//
//    private void getAppData() {
//
//        Log.d(TAG, "getAppData: ");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ctOffline.setVisibility(View.GONE);
//                ctError.setVisibility(View.GONE);
//                clLoading.setVisibility(View.VISIBLE);
//                mAppListRv.setVisibility(View.GONE);
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
//
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
//
//
//                        }
//
//                        for (int i = 0; i < mOriginallist.size(); i++) {
//                            AppModel appModel = mOriginallist.get(i);
//                            if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
//                                mOriginallist.remove(appModel);
//                                break;
//                            }
//                            Log.d(TAG, "onResponse: "+mOriginallist.size());
//                            if (mOriginallist.size()>6){
//                                setImage();
//                                return;
//                            }
//                        }
//
//
//
//                        updateUI();
//
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                clLoading.setVisibility(View.GONE);
//                                ctOffline.setVisibility(View.GONE);
//                                ctError.setVisibility(View.VISIBLE);
//                                mAppListRv.setVisibility(View.GONE);
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
//                            clLoading.setVisibility(View.GONE);
//                            ctOffline.setVisibility(View.GONE);
//                            ctError.setVisibility(View.GONE);
//                            mAppListRv.setVisibility(View.GONE);
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
//
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
//                                        updateUI();
//                                        //new ValidTask().execute();
//                                        // new GetVersionCode().execute(i);
//                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                clLoading.setVisibility(View.GONE);
//                                                ctOffline.setVisibility(View.GONE);
//                                                ctError.setVisibility(View.VISIBLE);
//                                                mAppListRv.setVisibility(View.GONE);
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
//
//                                        clLoading.setVisibility(View.GONE);
//                                        ctOffline.setVisibility(View.GONE);
//                                        ctError.setVisibility(View.VISIBLE);
//                                        mAppListRv.setVisibility(View.GONE);
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
//
//                            clLoading.setVisibility(View.GONE);
//                            ctOffline.setVisibility(View.GONE);
//                            ctError.setVisibility(View.VISIBLE);
//                            mAppListRv.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
//        }
//
////        if (mList.size()>0){
////            setImage();
////        }else {
////            Toast.makeText(this,"null",Toast.LENGTH_LONG).show();
////        }
//
//    }
//
//
//    private void updateUI() {
//        mAppDateList.clear();
//
//        clLoading.setVisibility(View.GONE);
//
//        mAppListRv.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mAppListRv.setItemAnimator(new DefaultItemAnimator());
//        adepter = new MoreAppAdepter(mContext, mList, MoreAppActivityNew.this, Color.BLACK, MoreAppActivityNew.this);
//        mAppListRv.setAdapter(adepter);
//
//    }
//
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
//    }
//
//
//    public class Receiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, "onReceive: ads ");
//            if (InternetConnection.checkConnection(mContext) && !isCalled) {
//                getAppData();
//            } else {
//                isCalled = false;
//                clLoading.setVisibility(View.GONE);
//            }
//
//        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(mNetworkReceiver);
//        unregisterReceiver(receiver);
//    }
//
//}
