package com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.demo.subscriptionbackgroundflow.R;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.model.AppModel;

import java.util.ArrayList;

public class MoreAppAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AppModel> mList;
    private OnAppClickListener mListener;
    private OnAppLongClickListener mListenerLongClick;

    private int textColor = 0;

    private boolean isAnim;

    public interface OnAppClickListener {
        void onAppClick(AppModel model);
    }

    public interface OnAppLongClickListener {
        void onAppLongClick(AppModel model);
    }

    public MoreAppAdepter(Context mContext, ArrayList<AppModel> mList, OnAppClickListener mListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;

        isAnim = true;
    }

    public MoreAppAdepter(Context mContext, ArrayList<AppModel> mList, OnAppClickListener mListener, int textColor, OnAppLongClickListener mListenerLongClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;
        this.textColor = textColor;
        this.mListenerLongClick = mListenerLongClick;

        isAnim = true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_more_app_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AppViewHolder) {

            final AppModel model = mList.get(position);

            if (model != null) {

                //((AppViewHolder) holder).imgAppIcon.setBackground(null);
                //((AppViewHolder) holder).txtAppName.setBackground(null);
                //((AppViewHolder) holder).imgAppIcon.setBackground(null);
                //((AppViewHolder) holder).txtAd.setVisibility(View.VISIBLE);

                //load app icon
                Glide.with(mContext)
                        .load(model.getImage())
                        .override(500, 500)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                //((AppViewHolder) holder).mProgressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                //((AppViewHolder) holder).shimmerView.hideShimmer();
                                //((AppViewHolder) holder).shimmerView.stopShimmer();
                                //((AppViewHolder) holder).imgAppIcon.setBackground(null);
                                //((AppViewHolder) holder).mProgressBar.setVisibility(View.GONE);
                                //((AppViewHolder) holder).materialCardView.setCardElevation(0);


                                return false;
                            }
                        }).into(((AppViewHolder) holder).imgAppIcon);


                ((AppViewHolder) holder).btnInstall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onAppClick(model);
                    }
                });

                //set app name
                ((AppViewHolder) holder).txtAppName.setText(model.getName());

                //handle Click Event of App Icon
                ((AppViewHolder) holder).imgAppIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onAppClick(model);
                    }
                });

                //handle Long click Event of App
                /*((AppViewHolder) holder).imgAppIcon.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mListenerLongClick.onAppLongClick(model);
                        return false;
                    }
                });*/

                if (textColor != 0) {
                    ((AppViewHolder) holder).txtAppName.setTextColor(textColor);
                }
            }

            if (isAnim) {
                //((AppViewHolder) holder).shimmerView.showShimmer(true);
            } else {
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addApp(ArrayList<AppModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
        isAnim = false;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAppIcon,imgBackground;
        private TextView txtAppName, txtAd;
        private ConstraintLayout mLayout;
        private Button btnInstall;
        //private ShimmerFrameLayout shimmerView;
        private CardView materialCardView;
        private ProgressBar mProgressBar;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAppIcon = itemView.findViewById(R.id.imgAppIcon);
            txtAppName = itemView.findViewById(R.id.txtAppName);
            mLayout = itemView.findViewById(R.id.constraintLayout3);
            btnInstall = itemView.findViewById(R.id.btnInstall);
            //mProgressBar = itemView.findViewById(R.id.progressBar4);

            // shimmerView = itemView.findViewById(R.id.shimmerView);
            //txtAd = itemView.findViewById(R.id.txtAd);
            //materialCardView = itemView.findViewById(R.id.cardView);
        }
    }


}
