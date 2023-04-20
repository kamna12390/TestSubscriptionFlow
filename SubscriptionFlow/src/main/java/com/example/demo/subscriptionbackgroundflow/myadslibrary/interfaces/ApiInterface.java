package com.example.demo.subscriptionbackgroundflow.myadslibrary.interfaces;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.Response;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("more_app")
    Call<ResponseModel> getAllApp();

    @GET("application/679")
    Call<Response> getAllAppNew();

}
