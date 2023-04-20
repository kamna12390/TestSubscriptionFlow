package com.example.demo.subscriptionbackgroundflow.myadslibrary.shared_oref;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {

    private static final String TAG = "MySharedPref";
    Context mContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String ADS_REMOVED="AdsRemoved";

    public MySharedPref(Context mContext) {
        this.mContext = mContext;
        sharedPreferences=this.mContext.getSharedPreferences("my_pref",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }


    public void setLanguage(String language){
        editor.putString("language",language);
        editor.commit();
    }

    public String getLanguage(){
        return sharedPreferences.getString("language","English");
    }


}
