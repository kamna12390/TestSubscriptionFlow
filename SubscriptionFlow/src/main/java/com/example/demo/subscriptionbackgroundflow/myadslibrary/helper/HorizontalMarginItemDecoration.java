package com.example.demo.subscriptionbackgroundflow.myadslibrary.helper;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalMarginItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int horizontalMarginInDp;

    public HorizontalMarginItemDecoration(Context context, @DimenRes int horizontalMarginInDp) {
        this.context = context;
        this.horizontalMarginInDp = (int) context.getResources().getDimension(horizontalMarginInDp);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = horizontalMarginInDp;
        outRect.left = horizontalMarginInDp;
    }
}
