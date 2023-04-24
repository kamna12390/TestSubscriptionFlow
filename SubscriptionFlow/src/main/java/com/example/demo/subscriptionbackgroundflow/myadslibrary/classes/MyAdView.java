package com.example.demo.subscriptionbackgroundflow.myadslibrary.classes;

import android.content.Context;
import android.content.Intent;

public class MyAdView {

    public static MyAdView getInstance(){
        return new MyAdView();
    }

//    public Intent getMoreAppIntent(Context context){
//        Intent intent = new Intent(context, NewMoreAppActivity.class);
//        return intent;
//    }
}
