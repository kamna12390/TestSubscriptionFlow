package com.example.demo.subscriptionbackgroundflow.myadslibrary.classes;

import android.content.Context;
import android.content.Intent;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.ui.MoreAppActivity;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.ui.MoreAppActivityNew;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.ui.NewMoreAppActivity;

public class MyAdView {

    public static MyAdView getInstance(){
        return new MyAdView();
    }

    public Intent getMoreAppIntent(Context context){
        Intent intent = new Intent(context, NewMoreAppActivity.class);
        return intent;
    }
}
