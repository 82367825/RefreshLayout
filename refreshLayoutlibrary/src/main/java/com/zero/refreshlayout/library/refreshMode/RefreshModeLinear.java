package com.zero.refreshlayout.library.refreshMode;

import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.RefreshLayout;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class RefreshModeLinear extends AbsRefreshMode {
    
    private FrameLayout.LayoutParams mHeaderLayoutParams;
    private FrameLayout.LayoutParams mFooterLayoutParams;
    private FrameLayout.LayoutParams mContentLayoutParams;
    
    public RefreshModeLinear(RefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    protected void initLayout() {
        /* 添加HeaderView */
        mHeaderLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mRefreshLayout.getHeaderViewHeight());
        mHeaderLayoutParams.gravity = Gravity.TOP;
        mHeaderLayoutParams.topMargin = -mHeaderLayoutParams.height;
        mRefreshLayout.addView(mRefreshLayout.getAbsHeaderView(), mHeaderLayoutParams);

        /* 添加FooterView */
        mFooterLayoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mRefreshLayout.getFooterViewHeight());
        mFooterLayoutParams.gravity = Gravity.BOTTOM;
        mFooterLayoutParams.bottomMargin = -mFooterLayoutParams.height;
        mRefreshLayout.addView(mRefreshLayout.getAbsFooterView(), mFooterLayoutParams);


        mContentLayoutParams = (FrameLayout.LayoutParams) mRefreshLayout.getContentView().getLayoutParams();
    }

    @Override
    public void moveHeaderView(float distance) {
        if (distance > 0) {
            if (mRefreshLayout.getHeaderViewHeight() > Math.abs(distance)) {
                /* HeaderView下移 */
                mHeaderLayoutParams.topMargin = (int) (-mRefreshLayout.getHeaderViewHeight() + distance);
                mRefreshLayout.getAbsHeaderView().setLayoutParams(mHeaderLayoutParams);
                mRefreshLayout.getAbsHeaderView().onPullDown(distance / mRefreshLayout.getHeaderViewHeight());
            } else {
                mRefreshLayout.getAbsHeaderView().onPullDown(1f);
            }
            if (mRefreshLayout.getHeaderViewMaxPullDistance() > Math.abs(distance)) {
                /* ContentView下移 */
                mContentLayoutParams.topMargin = (int) distance;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            }
        }
    }

    @Override
    public void moveFooterView(float distance) {
        if (distance < 0) {
            if (mRefreshLayout.getFooterViewHeight() > Math.abs(distance)) {
                /* FooterView上移 */
                mFooterLayoutParams.bottomMargin = (int) (-mRefreshLayout.getFooterViewHeight() + distance);
                mRefreshLayout.getAbsFooterView().setLayoutParams(mFooterLayoutParams);
                mRefreshLayout.getAbsFooterView().onPullUp(distance / mRefreshLayout.getHeaderViewHeight());
            } else {
                mRefreshLayout.getAbsFooterView().onPullUp(1f);
            }
            if (mRefreshLayout.getFooterViewMaxPullDistance() > Math.abs(distance)) {
                /* ContentView上移 */
                mContentLayoutParams.bottomMargin = (int) distance;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            }
        }
    }

    @Override
    public void moveToRefresh() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mContentLayoutParams.topMargin, 
                mRefreshLayout.getHeaderViewHeight());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mContentLayoutParams.topMargin = value;
                mRefreshLayout.getAbsHeaderView().setLayoutParams(mContentLayoutParams);
            }
        });
        mRefreshLayout.getAbsHeaderView().onRefreshing();
    }

    @Override
    public void moveToLoadMore() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mContentLayoutParams.bottomMargin, 
                mRefreshLayout.getFooterViewHeight());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mContentLayoutParams.bottomMargin = value;
                mRefreshLayout.getAbsFooterView().setLayoutParams(mContentLayoutParams);
            }
        });
    }

    @Override
    public void finishHeaderView(float distance) {
        if (distance > 0) {
            final int startValueHeaderView = mHeaderLayoutParams.topMargin;
            final int startValueContentView = mContentLayoutParams.topMargin;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (int) animation.getAnimatedValue();
                    mContentLayoutParams.topMargin = (int) 
                            (startValueContentView + (0 - startValueContentView) * value);
                    mRefreshLayout.setLayoutParams(mContentLayoutParams);
                    mHeaderLayoutParams.topMargin = (int) 
                            (startValueHeaderView + (-mHeaderLayoutParams.height - startValueHeaderView) * value);
                    mRefreshLayout.setLayoutParams(mHeaderLayoutParams);
                }
            });
        }
    }

    @Override
    public void finishFooterView(float distance) {
        if (distance < 0) {
            final int startValueFooterView = mFooterLayoutParams.bottomMargin;
            final int startValueContentView = mContentLayoutParams.bottomMargin;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (int) animation.getAnimatedValue();
                    mContentLayoutParams.bottomMargin = (int)
                            (startValueContentView + (0 - startValueContentView) * value);
                    mRefreshLayout.setLayoutParams(mContentLayoutParams);
                    mHeaderLayoutParams.bottomMargin = (int)
                            (startValueFooterView + (-mHeaderLayoutParams.height - startValueFooterView) * value);
                    mRefreshLayout.setLayoutParams(mFooterLayoutParams);
                }
            });
        }
    }
}
