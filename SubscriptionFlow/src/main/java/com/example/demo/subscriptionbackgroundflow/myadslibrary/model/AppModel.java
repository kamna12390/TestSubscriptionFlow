package com.example.demo.subscriptionbackgroundflow.myadslibrary.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AppModel {

    @SerializedName("position")
    private int position;

    @SerializedName("name")
    private String name;

    @SerializedName("app_link")
    private String app_link;

    @SerializedName("image")
    private String image;

    @SerializedName("is_trending")
    private boolean is_trending;

    private ArrayList<String> mUrls;

    public AppModel(int position, String name, String app_link, String image, boolean is_trending) {
        this.position = position;
        this.name = name;
        this.app_link = app_link;
        this.image = image;
        this.is_trending = is_trending;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp_link() {
        return app_link;
    }

    public void setApp_link(String app_link) {
        this.app_link = app_link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean is_trending() {
        return is_trending;
    }

    public void settranding(boolean is_trending) {
        this.is_trending = is_trending;
    }

    public ArrayList<String> getmUrls() {
        return mUrls;
    }

    public void setmUrls(ArrayList<String> mUrls) {
        this.mUrls = mUrls;
    }
}
