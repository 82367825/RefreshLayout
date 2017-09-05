package com.zero.refreshlayout.library.theme;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zero.refreshlayout.library.R;
import com.zero.refreshlayout.library.header.AbsHeaderView;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class LightHeader extends AbsHeaderView {
    
    private View mContentView;
    private ImageView mImageView;
    
    private ValueAnimator mValueAnimator;
    
    public LightHeader(Context context) {
        super(context);
        mContentView = LayoutInflater.from(context).inflate(R.layout.refresh_layout_light, null);
        mImageView = (ImageView) mContentView.findViewById(R.id.refresh_loading_image);
    }

    @Override
    public View getContentView() {
        return mContentView;
    }

    @Override
    public void onPullDown(float fraction) {
        
    }

    @Override
    public void onFinishPullDown(float fraction) {
        mImageView.setImageResource(R.drawable.loading_pulltorefresh_light);
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    public void onRefreshing() {
        mImageView.setImageResource(R.drawable.loading_pulltorefresh_light2);
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(700);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mImageView.setAlpha(value);
            }
        });
        mValueAnimator.start();
    }
}
