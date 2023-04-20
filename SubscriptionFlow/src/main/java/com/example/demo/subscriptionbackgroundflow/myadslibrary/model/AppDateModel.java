package com.example.demo.subscriptionbackgroundflow.myadslibrary.model;

import java.util.Date;

public class AppDateModel {

    private int appId;
    private String date;
    private String size;
    private String download;
    private Date mDate;

    public AppDateModel(int appId, String date, String size, String download) {
        this.appId = appId;
        this.date = date;
        this.size = size;
        this.download = download;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
