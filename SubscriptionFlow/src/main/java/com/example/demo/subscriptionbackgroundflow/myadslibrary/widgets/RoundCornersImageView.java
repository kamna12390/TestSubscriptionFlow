package com.example.demo.subscriptionbackgroundflow.myadslibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.demo.subscriptionbackgroundflow.R;


public class RoundCornersImageView extends AppCompatImageView {

    private int radius;

    public RoundCornersImageView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornersImageView);
        radius = array.getInt(R.styleable.RoundCornersImageView_CornerRadius, 0);
        array.recycle();

    }

    public RoundCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornersImageView);
        radius = array.getInt(R.styleable.RoundCornersImageView_CornerRadius, 0);
        array.recycle();
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 0f;//getContext().getResources().getDimension(R.dimen.conrnerRadius);
        Path path = new Path();
        RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        //path.addCircle(this.getWidth()/2,this.getHeight()/2,200,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
