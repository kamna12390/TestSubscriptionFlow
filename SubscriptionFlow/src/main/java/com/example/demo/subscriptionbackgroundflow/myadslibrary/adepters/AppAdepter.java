package com.example.demo.subscriptionbackgroundflow.myadslibrary.adepters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.example.demo.subscriptionbackgroundflow.R;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.AppModel;
import com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets.SquareImageView;

import java.util.ArrayList;


public class AppAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AppModel> mList;
    private OnAppClickListener mListener;

    private int textColor = 0;

    private boolean isAnim;

    public interface OnAppClickListener {
        void onAppClick(String uri);
    }

    public AppAdepter(Context mContext, ArrayList<AppModel> mList, OnAppClickListener mListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;

        isAnim = true;
    }

    public AppAdepter(Context mContext, ArrayList<AppModel> mList, OnAppClickListener mListener, int textColor) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;
        this.textColor = textColor;

        isAnim = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_app, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AppViewHolder) {

            final AppModel model = mList.get(position);

            if (model != null) {

                //((AppViewHolder) holder).imgAppIcon.setBackground(null);
                ((AppViewHolder) holder).txtAppName.setVisibility(View.VISIBLE);
                ((AppViewHolder) holder).mCLRetry.setVisibility(View.GONE);

                //load app icon
                Glide.with(mContext)
                        .load(model.getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                ((AppViewHolder) holder).progressBar.setVisibility(View.GONE);
                                ((AppViewHolder) holder).mCLRetry.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ((AppViewHolder) holder).imgAdLabel.setVisibility(View.VISIBLE);
                                //((AppViewHolder) holder).shimmerView.hideShimmer();
                                //((AppViewHolder) holder).shimmerView.stopShimmer();
                                ((AppViewHolder) holder).mCLRetry.setVisibility(View.GONE);
                                ((AppViewHolder) holder).progressBar.setVisibility(View.GONE);

                                return false;
                            }
                        }).into(((AppViewHolder) holder).imgAppIcon);

                //set app name
                ((AppViewHolder) holder).txtAppName.setText(model.getName());

                ((AppViewHolder) holder).mCLRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AppViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                        ((AppViewHolder) holder).mCLRetry.setVisibility(View.GONE);
                        Glide.with(mContext)
                                .load(model.getImage())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        ((AppViewHolder) holder).progressBar.setVisibility(View.GONE);
                                        ((AppViewHolder) holder).mCLRetry.setVisibility(View.VISIBLE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        ((AppViewHolder) holder).imgAdLabel.setVisibility(View.VISIBLE);
                                        //((AppViewHolder) holder).shimmerView.hideShimmer();
                                        //((AppViewHolder) holder).shimmerView.stopShimmer();
                                        ((AppViewHolder) holder).mCLRetry.setVisibility(View.GONE);
                                        ((AppViewHolder) holder).progressBar.setVisibility(View.GONE);

                                        return false;
                                    }
                                }).into(((AppViewHolder) holder).imgAppIcon);
                    }
                });

                //handle Click Event of App Icon
                ((AppViewHolder) holder).imgAppIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onAppClick(model.getApp_link());
                    }
                });

                if (textColor != 0) {
                    ((AppViewHolder) holder).txtAppName.setTextColor(textColor);
                }
            }

            if (isAnim) {
                ((AppViewHolder) holder).imgAdLabel.setVisibility(View.GONE);
                ((AppViewHolder) holder).txtAppName.setVisibility(View.GONE);
                //((AppViewHolder) holder).shimmerView.showShimmer(true);
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

    public int getId() {
        return mList.get(0).getPosition();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        private SquareImageView imgAppIcon, imgAdLabel;
        private TextView txtAppName;
        private ProgressBar progressBar;
        //private ShimmerFrameLayout shimmerView;
        private CardView mCardView;

        private ConstraintLayout mCLRetry;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAppIcon = itemView.findViewById(R.id.imgAppIcon);
            txtAppName = itemView.findViewById(R.id.appName);
            imgAdLabel = itemView.findViewById(R.id.ad_lable);
            progressBar = itemView.findViewById(R.id.progressBar);
            mCLRetry = itemView.findViewById(R.id.cl_retry);
            //mCardView = itemView.findViewById(R.id.cardView);
            //shimmerView = itemView.findViewById(R.id.shimmerView);
        }
    }

    public void addData(ArrayList<AppModel> appModels) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallBack(appModels, mList));
        diffResult.dispatchUpdatesTo(this);
        mList.clear();
        mList.addAll(appModels);
    }

    public class MyDiffUtilCallBack extends DiffUtil.Callback {
        ArrayList<AppModel> newList;
        ArrayList<AppModel> oldList;

        public MyDiffUtilCallBack(ArrayList<AppModel> newList, ArrayList<AppModel> oldList) {
            this.newList = newList;
            this.oldList = oldList;
        }

        @Override
        public int getOldListSize() {
            return oldList != null ? oldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return newList != null ? newList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return newList.get(newItemPosition).getName() == oldList.get(oldItemPosition).getName();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            int result = newList.get(newItemPosition).getApp_link().compareTo(oldList.get(oldItemPosition).getApp_link());
            return result == 0;
        }

        /*@Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {

            Model newModel = newList.get(newItemPosition);
            Model oldModel = oldList.get(oldItemPosition);

            Bundle diff = new Bundle();

            if (newModel.price != (oldModel.price)) {
                diff.putInt("price", newModel.price);
            }
            if (diff.size() == 0) {
                return null;
            }
            return diff;
            //return super.getChangePayload(oldItemPosition, newItemPosition);
        }*/
    }
}
