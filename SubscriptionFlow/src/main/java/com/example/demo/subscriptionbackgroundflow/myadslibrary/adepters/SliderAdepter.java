package com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.fargments.FragmentSlider;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;

import java.util.ArrayList;
import java.util.Random;

public class SliderAdepter extends FragmentStatePagerAdapter {

    private static final String TAG = "SliderAdepter";
    private static final Random RANDOM = new Random();
    private ArrayList<AppModel> mList;
    private ArrayList<Integer> integers = new ArrayList<>();
    private int number;
    private boolean isAnim;

    public SliderAdepter(FragmentManager fm, ArrayList<AppModel> mList,boolean isAnim) {
        super(fm);
        this.mList = mList;
        this.isAnim = isAnim;
    }

    @Override
    public Fragment getItem(int position) {

        if (!isAnim) {

            if (position == 0) {
                number = 0;
                integers.clear();
            }

            Random r = new Random();
            int i1 = r.nextInt(mList.size() - 0) + 0;

            if (integers.contains(i1)) {
                int i = r.nextInt(mList.size() - 0) + 0;
                while (integers.contains(i)) {
                    i = r.nextInt(mList.size() - 0) + 0;
                }
                number = i;
                integers.add(i);
            } else {
                number = i1;
                integers.add(i1);
            }
        } else {
            number = position;
        }


        Log.d(TAG, "getItem: position = " + position + " number = " + number);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAnim",isAnim);
        if (!isAnim) {
            bundle.putString("name", mList.get(number).getName());
            bundle.putString("link", mList.get(number).getApp_link());
            bundle.putString("icon", mList.get(number).getImage());
            bundle.putString("full", mList.get(number).getImage());
        }
        return FragmentSlider.getInstance(bundle);

    }

    @Override
    public int getCount() {
        return 3;
    }



    public void setmList(ArrayList<AppModel> mList) {
        this.mList = mList;
        isAnim = false;
        notifyDataSetChanged();

    }

    /*public class SliderAdepter extends PagerAdapter {

    private Context mContext;
    private ArrayList<AppModel> mList;

    public SliderAdepter(Context mContext, ArrayList<AppModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_slider, container, false);

        TextView txtAppName = layout.findViewById(R.id.txtAppName);
        Button btnDownload = layout.findViewById(R.id.btnDownload);

        final BlurImageView imgBlur = layout.findViewById(R.id.imgBlurImage);
        ImageView imgAppIcon = layout.findViewById(R.id.imgAppIcon);

        Glide.with(mContext)
                .load(mList.get(position).getFull_thumb_image())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imgBlur.setBlur(15);
                        return false;
                    }
                })
                .into(imgBlur);


        Glide.with(mContext)
                .load(mList.get(position).getThumb_image())
                .into(imgAppIcon);

        txtAppName.setText(mList.get(position).getName());

        container.addView(layout);
        return layout;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.9f;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }*/
}
