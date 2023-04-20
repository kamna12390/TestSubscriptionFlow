package com.example.demo.subscriptionbackgroundflow.myadslibrary.task;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;

import java.io.File;
import java.io.IOException;

public class DownloadFFmpeg {

    private static final String TAG = "DownloadFFmpeg";

    private Context mContext;

    private OnDownloaded mListener;

    public interface OnDownloaded{
        void onDownload();
    }

    public DownloadFFmpeg(Context mContext, OnDownloaded mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    public static DownloadFFmpeg getInstance(Context context) {
        return new DownloadFFmpeg(context);
    }

    public DownloadFFmpeg(Context context) {
        this.mContext = context;
    }

    public void startDownload() {

        if (!InternetConnection.checkConnection(mContext)) {
            Toast.makeText(mContext, "Please Turn on Internet and try again", Toast.LENGTH_SHORT).show();
            return;
        }

        File dri = new File(mContext.getExternalFilesDir(null) + "/ffmpeg");

        if (!dri.exists()) {
            dri.mkdir();
        }

        Log.d(TAG, "onCreate: " + mContext.getExternalFilesDir(null) + "/ffmpeg");
        File file = new File(dri, "ffmpeg.zip");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1 = new File(mContext.getExternalFilesDir(null) + "/ffmpeg/ffmpeg");
        if (!file1.exists()) {
            DownloadTask downloadTask = new DownloadTask(mContext, file, "Please Wait...");
            downloadTask.setListener(new DownloadTask.OnDownloadCompleteListener() {
                @Override
                public void onDownloadCompalete() {
                    if (mListener != null) {
                        mListener.onDownload();
                    }
                }
            });
            downloadTask.execute("https://fuunly.com/auto_wallpaper_changer/ffmpeg.zip");
        } else {
            Log.d(TAG, "onCreate: isExist ");
        }
    }

    public boolean isFileExist(){
        File file1 = new File(mContext.getExternalFilesDir(null) + "/ffmpeg/ffmpeg");
        return file1.exists();
    }

}
