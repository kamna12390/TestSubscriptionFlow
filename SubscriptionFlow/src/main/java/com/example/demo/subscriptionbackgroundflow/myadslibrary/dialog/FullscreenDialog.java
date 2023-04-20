package com.example.demo.subscriptionbackgroundflow.myadslibrary.dialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.demo.subscriptionbackgroundflow.R;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters.ImageAdepter;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets.MyViewPager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FullscreenDialog extends DialogFragment {

    private static final String TAG = "FullscreenDialog";

    private FullscreenDialog mDialog;

    private ImageButton imgBtnClose;
    private MyViewPager myViewPager;
    private ProgressBar mProgressBar;

    private ArrayList<String> mList;


    private AppModel appModel;

    public FullscreenDialog(AppModel appModel) {
        this.appModel = appModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_image_viewer_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBtnClose = view.findViewById(R.id.imgBtnClose);
        myViewPager = view.findViewById(R.id.imageViewPager);
        mProgressBar = view.findViewById(R.id.progressBar);


        mDialog = this;
        mList = new ArrayList<>();

        //handel Button Close event
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        if (appModel.getmUrls() == null)
            new GetImageUrl().execute();
        else{
            mProgressBar.setVisibility(View.GONE);
            mList = appModel.getmUrls();
            if(mList.size() == 0){
                mDialog.dismiss();
                appModel.setmUrls(null);
            }
            ImageAdepter imageAdepter = new ImageAdepter(getChildFragmentManager(), mList);
            myViewPager.setAdapter(imageAdepter);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        this.getDialog().getWindow().setLayout(width, height);

    }

    private class GetImageUrl extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect(appModel.getApp_link())
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".Q4vdJd")
                        .html();


                Document doc = Jsoup.parse(newVersion);
                //select the divs
                Elements divs = doc.select("img");
                mList.clear();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                ArrayList<String> mArray = new ArrayList<>();
                for (Element e : divs) {
                    if (e.attr("src").startsWith("https")) {
                        String url = e.attr("src");
                        url = getNewUrl(url, width, height);
                        mList.add(url);
                        mArray.add(url);


                    }else {
                        if (e.attr("data-src").startsWith("https")) {
                            String url = e.attr("data-src");
                            url = getNewUrl(url, width, height);
                            mList.add(url);
                            mArray.add(url);

                        }
                    }
                }
                appModel.setmUrls(mArray);
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            mProgressBar.setVisibility(View.GONE);
            if(mList.size() > 0) {
                ImageAdepter imageAdepter = new ImageAdepter(getChildFragmentManager(), mList);
                myViewPager.setAdapter(imageAdepter);
            }else{

                if(getContext() != null  && !InternetConnection.checkConnection(getContext())){

                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }

        }
    }

    private String getNewUrl(String url, int width, int height) {
        String[] urlArr = url.split("=");
        String strwh = urlArr[urlArr.length - 1];
        String[] wh = strwh.split("-");

        String str = url.replace(wh[0], "w" + String.valueOf(width));
        str = str.replace(wh[1], "w" + String.valueOf(height));

        return str;
    }

}
