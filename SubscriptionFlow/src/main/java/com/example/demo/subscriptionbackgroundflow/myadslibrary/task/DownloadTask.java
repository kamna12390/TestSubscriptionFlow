package com.example.demo.subscriptionbackgroundflow.myadslibrary.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "DownloadTask";

    private ProgressDialog mPDialog;
    private Context mContext;
    private PowerManager.WakeLock mWakeLock;
    private File mTargetFile;

    private OnDownloadCompleteListener mListener;

    public interface OnDownloadCompleteListener {
        void onDownloadCompalete();
    }

    //Constructor parameters :
    // @context (current Activity)
    // @targetFile (File object to write,it will be overwritten if exist)
    // @dialogMessage (message of the ProgresDialog)
    public DownloadTask(Context context, File targetFile, String dialogMessage) {
        this.mContext = context;
        this.mTargetFile = targetFile;
        mPDialog = new ProgressDialog(context);

        mPDialog.setMessage(dialogMessage);
        mPDialog.setIndeterminate(true);
        mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPDialog.setCancelable(false);
        // reference to instance to use inside listener
        final DownloadTask me = this;
        mPDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                me.cancel(true);
            }
        });
        Log.i("DownloadTask", "Constructor done");
    }

    public void setListener(OnDownloadCompleteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }
            Log.i("DownloadTask", "Response " + connection.getResponseCode());

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = 9805095;//connection.getContentLength();

            Log.d("LOG", "doInBackground: " + connection.getHeaderField("content-length"));

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(mTargetFile, false);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    Log.i("DownloadTask", "Cancelled");
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();

        mPDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mPDialog.setIndeterminate(false);
        mPDialog.setMax(100);
        mPDialog.setProgress(progress[0]);

    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("DownloadTask", "Work Done! PostExecute");
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();

        }
        if (mPDialog != null && mContext != null && !((Activity) mContext).isFinishing())
            mPDialog.dismiss();

        if (result != null) {
            if (InternetConnection.checkConnection(mContext)) {
                Toast.makeText(mContext, "Download Error Please try again ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Please Turn on Internet and try again", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (mTargetFile.length() >= 9805095) {
                Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();

                try {
                    File file = new File(mContext.getExternalFilesDir(null) + "/ffmpeg");
                    if (!file.exists())
                        file.mkdir();
                    unzip(mTargetFile, file);

                    Log.d("LOG", "onPostExecute: " + mTargetFile.length());
                    if (mTargetFile.exists()) {
                        Log.d(TAG, "onPostExecute: delete ");
                        mTargetFile.delete();
                    } else {
                        Log.d(TAG, "onPostExecute: delete error ");
                    }

                    Log.d("LOG", "onPostExecute: " + file.listFiles()[file.listFiles().length - 1].getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mListener != null) {
                    mListener.onDownloadCompalete();
                }
            } else {
                if (InternetConnection.checkConnection(mContext)) {
                    Toast.makeText(mContext, "Download Error Please try again ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Download Error Please Turn on Internet and try again", Toast.LENGTH_SHORT).show();
                }
            }
        }



    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }
}