package com.example.demo.subscriptionbackgroundflow.myadslibrary.fargments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.demo.subscriptionbackgroundflow.R;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets.BlurImageView;

public class FragmentSlider extends Fragment {

    private static final String TAG = "FragmentSlider";
    
    public static FragmentSlider getInstance(Bundle bundle){
        FragmentSlider fragmentSlider =new FragmentSlider();
        fragmentSlider.setArguments(bundle);
        return fragmentSlider;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_slider,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtAppName = view.findViewById(R.id.txtAppName);
        ImageButton btnDownload = view.findViewById(R.id.btnDownload);

        final BlurImageView imgBlur = view.findViewById(R.id.imgBlurImage);
        ImageView imgAppIcon = view.findViewById(R.id.imgAppIcon);

        final Bundle bundle = getArguments();

        if(bundle != null) {
            Log.d(TAG, "onViewCreated: "+bundle.getBoolean("isAnim"));
            if(!bundle.getBoolean("isAnim")) {

                view.findViewById(R.id.dummy_image_view).setVisibility(View.GONE);
                view.findViewById(R.id.txtAd).setVisibility(View.GONE);
                Glide.with(getContext())
                        .load(bundle.getString("full"))
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                                imgBlur.setmBitmap(bitmapDrawable.getBitmap());
                                imgBlur.setImageDrawable(resource);
                                imgBlur.setBlur(15);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });


                Glide.with(getContext())
                        .load(bundle.getString("icon"))
                        .into(imgAppIcon);

                txtAppName.setText(bundle.getString("name"));

                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString("link"))));
                    }
                });

                imgBlur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString("link"))));
                    }
                });
            }
        }
    }
}
