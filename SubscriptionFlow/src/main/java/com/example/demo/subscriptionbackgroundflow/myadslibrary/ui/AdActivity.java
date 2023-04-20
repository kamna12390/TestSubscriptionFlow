package com.example.demo.subscriptionbackgroundflow.myadslibrary.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.Api.Api;
import com.example.demo.subscriptionbackgroundflow.R;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.AppAdepter;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.classes.GridSpacingItemDecoration;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.ResponseModel;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.receiver.NetworkChangeReceiver;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.GridDividerDecoration;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.GridDividerItemDecoration;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdActivity extends AppCompatActivity implements AppAdepter.OnAppClickListener {

    //Constant
    private static final String TAG = "AdActivity";

    private Context mContext;

    //widgets
    private ImageView mImageBackground, mLetStart;
    private RecyclerView mAppListRv;
    private TextView txtStart;
    private ConstraintLayout mInternetDisable, mError;
    private ImageView animation;
    private ProgressBar mProgressBar;

    //object
    private ArrayList<AppModel> mAppList;
    private AppAdepter adepter;

    private Receiver receiver;
    private NetworkChangeReceiver mNetworkReceiver;

    //var
    private boolean isBack = true;
    private boolean isCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        mContext = AdActivity.this;

        //init Receiver
        receiver = new Receiver();
        mNetworkReceiver = new NetworkChangeReceiver();

        registerReceiver(mNetworkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(receiver, new IntentFilter("com.example.demo.subscriptionbackgroundflow.myadslibrary.custom"));

        initView();

        initListener();

        initAction();

        //Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_roate);
        //animation.startAnimation(aniRotate);
    }


    @Override
    protected void onStart() {
        super.onStart();
        txtStart.setEnabled(true);
    }

    private void initView() {
        mImageBackground = findViewById(R.id.half_image);
        mAppListRv = findViewById(R.id.appList);
        txtStart = findViewById(R.id.txtStart);
        mInternetDisable = findViewById(R.id.ctInternetDisable);
        mError = findViewById(R.id.ctFetchError);
        mProgressBar = findViewById(R.id.progressBar1);
    }


    private void initListener() {

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtStart.setEnabled(false);
                startHome();
            }
        });

        findViewById(R.id.txtRetry1).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                if (InternetConnection.checkConnection(mContext)) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            getAppData();
                            return null;
                        }
                    }.execute();
                } else {
                    mError.setVisibility(View.GONE);
                    mAppListRv.setVisibility(View.INVISIBLE);
                    mInternetDisable.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Please turn on internet.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

        findViewById(R.id.txtRetry).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                if (InternetConnection.checkConnection(mContext)) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            getAppData();
                            return null;
                        }
                    }.execute();
                } else {
                    mAppListRv.setVisibility(View.INVISIBLE);
                    mError.setVisibility(View.GONE);
                    mInternetDisable.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Please turn on internet.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }

    private void startHome() {
        try {
            startActivity(new Intent(AdActivity.this, Class.forName("com.crop.photo.image.resize.cut.tools.activitys.HomeActivity")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start(View view) {
        startHome();
    }

    @SuppressLint("StaticFieldLeak")
    private void initAction() {
        ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_text);
        shimmerText.startShimmerAnimation();


        //init App List
        mAppList = new ArrayList<>();

        //load background image
       /* Glide.with(mContext)
                .load(R.drawable.half_image)
                .into(mImageBackground);*/

        //init Recycler view
        mAppListRv.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAppListRv.setItemAnimator(new DefaultItemAnimator());
        int spanCount = 3; // 3 columns
        int spacing = 25; // 50px
        boolean includeEdge = true;
        mAppListRv.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(20),dpToPx(2) ,true));
        //mAppListRv.addItemDecoration(new GridDividerDecoration(this));

        //get Dummy data for animation
        //getDummyData();
        mProgressBar.setVisibility(View.VISIBLE);

        //init Adepter
        //adepter = new AppAdepter(mContext, mAppList, AdActivity.this);

        //set Adepter to recycler view
        mAppListRv.setAdapter(adepter);

        //get data from api
        if (InternetConnection.checkConnection(mContext)) {
            isCalled = true;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    getAppData();
                    return null;
                }
            }.execute();

        } else {
            findViewById(R.id.txtMoreAppsLabel).setVisibility(View.GONE);
            mAppListRv.setVisibility(View.GONE);
            mInternetDisable.setVisibility(View.VISIBLE);
        }
        mImageBackground.animate().scaleX(1.2f).scaleY(1.2f).withEndAction(runnable).setDuration(800);
        //animation
        //animation.animate().rotationBy(360).withEndAction(runnable).setDuration(1500);
       /* RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setDuration(800);
        rotate.setInterpolator(new LinearInterpolator());
        animation.startAnimation(rotate);*/
        /*ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                animation.setRotation((Float) anim.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();*/
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            mImageBackground.animate().scaleX(1.2f).scaleY(1.2f).withEndAction(runnable).setDuration(800);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mImageBackground.animate().scaleX(1f).scaleY(1f).withEndAction(runnable1).setDuration(800);
        }
    };

    private void getDummyData() {
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
        mAppList.add(null);
    }

    private void getAppData() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
                mAppListRv.setVisibility(View.VISIBLE);
                mInternetDisable.setVisibility(View.GONE);
                mError.setVisibility(View.GONE);
            }
        });

        Call<ResponseModel> call = Api.getClient().getAllApp();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                findViewById(R.id.txtMoreAppsLabel).setVisibility(View.VISIBLE);
                ResponseModel model = response.body();
                mProgressBar.setVisibility(View.GONE);

                if (model != null && model.getData() != null) {

                    mAppList = model.getData();

                    for (int i = 0; i < mAppList.size(); i++) {
                        AppModel appModel = mAppList.get(i);
                        if (appModel.getApp_link().contains("com.crop.photo.image.resize.cut.tools")) {
                            mAppList.remove(appModel);
                            break;
                        }
                    }

                   /* Collections.sort(mAppList, new Comparator<AppModel>() {
                        @Override
                        public int compare(AppModel o1, AppModel o2) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                            try {
                                return dateFormat.parse(o2.getApp_updated()).compareTo(dateFormat.parse(o1.getApp_updated()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });*/

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //adepter.addApp(mAppList);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.txtMoreAppsLabel).setVisibility(View.GONE);
                            mAppListRv.setVisibility(View.GONE);
                            mInternetDisable.setVisibility(View.GONE);
                            mError.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + " + ");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mProgressBar.setVisibility(View.GONE);
                        findViewById(R.id.txtMoreAppsLabel).setVisibility(View.GONE);
                        mAppListRv.setVisibility(View.GONE);
                        mInternetDisable.setVisibility(View.GONE);
                        mError.setVisibility(View.GONE);
                        Log.d(TAG, "run: "+InternetConnection.checkConnection(mContext));
                        if(!InternetConnection.checkConnection(mContext))
                            mInternetDisable.setVisibility(View.VISIBLE);
                        else
                            mError.setVisibility(View.VISIBLE);

                    }
                });
            }
        });
    }

    @Override
    public void onAppClick(String uri) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    public class Receiver extends BroadcastReceiver {

        @SuppressLint("StaticFieldLeak")
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ads ");
            if (InternetConnection.checkConnection(context) && !isCalled) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        getAppData();
                        return null;
                    }
                }.execute();
            } else {
                isCalled = false;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkReceiver);
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            isBack = false;
            Toast.makeText(mContext, "Press again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
