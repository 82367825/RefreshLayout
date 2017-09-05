package com.zero.refreshlayout.library.theme.bezier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.RefreshScrollUtil;
import com.zero.refreshlayout.library.footer.AbsFooterView;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class BezierWaveFooter extends AbsFooterView {

    private FrameLayout mFrameLayout;
    private BezierWaveView mBezierWaveView;
    
    public BezierWaveFooter(Context context) {
        super(context);
        mBezierWaveView = new BezierWaveView(context);
        mBezierWaveView.setDirection(0);
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
    public void onPullUp(float fraction) {
        mBezierWaveView.onRefreshOrLoadMore(fraction);
    }

    @Override
    public void onFinishPullUp(float fraction) {
        mBezierWaveView.onRefreshOrLoadMore(fraction);
    }

    @Override
    public void onLoadMore() {
        mBezierWaveView.onRefreshIngOrLoadMoreing();
    }
}
