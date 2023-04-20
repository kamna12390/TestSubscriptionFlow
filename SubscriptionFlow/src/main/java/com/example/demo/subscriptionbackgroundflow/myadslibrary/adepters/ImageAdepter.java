package com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo.subscriptionbackgroundflow.myadslibrary.fargments.ImageFragment;

import java.util.ArrayList;

public class ImageAdepter extends FragmentStatePagerAdapter {

    private ArrayList<String> mList;

    public ImageAdepter(FragmentManager fm, ArrayList<String> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.getInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
