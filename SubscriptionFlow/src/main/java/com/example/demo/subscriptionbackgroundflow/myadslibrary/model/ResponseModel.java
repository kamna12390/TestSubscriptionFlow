package com.example.demo.subscriptionbackgroundflow.myadslibrary.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<AppModel> data;

    public ResponseModel(String status, String message, ArrayList<AppModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<AppModel> getData() {
        return data;
    }
}
