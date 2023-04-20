package com.example.demo.subscriptionbackgroundflow.myadslibrary.Api;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.interfaces.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String BASE_URL = "http://165.22.219.93/auto-wallpaper-changer/api/";


    public static ApiInterface getClient(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiInterface.class);
    }
}
