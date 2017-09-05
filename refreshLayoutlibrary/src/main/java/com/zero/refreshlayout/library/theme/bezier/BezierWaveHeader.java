package com.zero.refreshlayout.library.theme.bezier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.RefreshScrollUtil;
import com.zero.refreshlayout.library.header.AbsHeaderView;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class BezierWaveHeader extends AbsHeaderView {
    
    private FrameLayout mFrameLayout;
    private BezierWaveView mBezierWaveView;
    
    public BezierWaveHeader(Context context) {
        super(context);
        mBezierWaveView = new BezierWaveView(context);
        mBezierWaveView.setDirection(1);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, RefreshScrollUtil.dip2px(context, 85));
        mFrameLayout = new FrameLayout(context);
        mFrameLayout.addView(mBezierWaveView, layoutParams);
    }

    @Override
    public View getContentView() {
        return mFrameLayout;
    }

    @Override
    public void onPullDown(float fraction) {
        mBezierWaveView.onRefreshOrLoadMore(fraction);
    }

    @Override
    public void onFinishPullDown(float fraction) {
        mBezierWaveView.onRefreshOrLoadMore(fraction);
    }

    @Override
    public void onRefreshing() {
        mBezierWaveView.onRefreshIngOrLoadMoreing();
    }
}
