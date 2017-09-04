package com.zero.refreshlayout.library.refreshMode;

import android.animation.Animator;
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

public class RefreshModeLayoutBehind extends AbsRefreshMode {

    private FrameLayout.LayoutParams mHeaderLayoutParams;
    private FrameLayout.LayoutParams mFooterLayoutParams;
    private FrameLayout.LayoutParams mContentLayoutParams;

    private boolean mIsAnimRunning = false;

    public RefreshModeLayoutBehind(RefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    protected void initLayout() {
        /* 添加HeaderView */
        mHeaderLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRefreshLayout.getHeaderViewHeight());
        mHeaderLayoutParams.gravity = Gravity.TOP;
        mHeaderLayoutParams.topMargin = -mHeaderLayoutParams.height;
        mRefreshLayout.addView(mRefreshLayout.getAbsHeaderView().getContentView(), mHeaderLayoutParams);

        /* 添加FooterView */
        mFooterLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRefreshLayout.getFooterViewHeight());
        mFooterLayoutParams.gravity = Gravity.BOTTOM;
        mFooterLayoutParams.bottomMargin = -mFooterLayoutParams.height;
        mRefreshLayout.addView(mRefreshLayout.getAbsFooterView().getContentView(), mFooterLayoutParams);
        
        mContentLayoutParams = (FrameLayout.LayoutParams) mRefreshLayout.getContentView().getLayoutParams();
    }

    @Override
    public boolean isAnimRunning() {
        return mIsAnimRunning;
    }

    @Override
    public void moveHeaderView(float distance) {
        if (distance >= 0) {
            if (mRefreshLayout.getHeaderViewMaxPullDistance() > Math.abs(distance)) {
                mHeaderLayoutParams.topMargin = (int) (-mRefreshLayout.getHeaderViewHeight() + 
                                        Math.abs(distance));
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
            } else {
                mHeaderLayoutParams.topMargin = (int) (-mRefreshLayout.getHeaderViewHeight() +
                        mRefreshLayout.getHeaderViewMaxPullDistance());
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
            }
            
            if (mRefreshLayout.getHeaderViewHeight() > Math.abs(distance)) {
                mRefreshLayout.getAbsHeaderView().onPullDown(Math.abs(distance) / mRefreshLayout
                        .getHeaderViewHeight());
            } else {
                mRefreshLayout.getAbsHeaderView().onPullDown(1f);
            }
            mRefreshLayout.requestLayout();
        }
    }

    @Override
    public void moveFooterView(float distance) {
        if (mRefreshLayout.getFooterViewMaxPullDistance() > Math.abs(distance)) {
            mFooterLayoutParams.bottomMargin = (int) (-mRefreshLayout.getFooterViewHeight() +
                    Math.abs(distance));
            mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
        } else {
            mFooterLayoutParams.bottomMargin = (int) (-mRefreshLayout.getFooterViewHeight() +
                    mRefreshLayout.getFooterViewMaxPullDistance());
            mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
        }

        if (mRefreshLayout.getFooterViewHeight() > Math.abs(distance)) {
            mRefreshLayout.getAbsFooterView().onPullUp(Math.abs(distance) / mRefreshLayout
                    .getFooterViewHeight());
        } else {
            mRefreshLayout.getAbsFooterView().onPullUp(1f);
        }
        mRefreshLayout.requestLayout();
    }

    @Override
    public void moveToRefresh() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mHeaderLayoutParams.topMargin, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mHeaderLayoutParams.topMargin = value;
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
                mRefreshLayout.requestLayout();
            }
        });
        valueAnimator.start();
        mRefreshLayout.getAbsHeaderView().onRefreshing();
        mIsAnimRunning = true;
    }

    @Override
    public void moveToLoadMore() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mFooterLayoutParams.bottomMargin, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mFooterLayoutParams.bottomMargin = value;
                mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
                mRefreshLayout.requestLayout();
            }
        });
        valueAnimator.start();
        mRefreshLayout.getAbsFooterView().onLoadMore();
        mIsAnimRunning = true;
    }

    @Override
    public void finishHeaderView() {
        mIsAnimRunning = true;
        final int startValueHeaderView = mHeaderLayoutParams.topMargin;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mHeaderLayoutParams.topMargin = (int) (startValueHeaderView + (-mHeaderLayoutParams.height - startValueHeaderView) * value);
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
                mRefreshLayout.getAbsHeaderView().onFinishPullDown(Math.abs(mHeaderLayoutParams.topMargin) / mRefreshLayout.getHeaderViewHeight());
                mRefreshLayout.requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @Override
    public void finishFooterView() {
        mIsAnimRunning = true;
        final int startValueFooterView = mFooterLayoutParams.bottomMargin;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mFooterLayoutParams.bottomMargin = (int) (startValueFooterView + (-mFooterLayoutParams.height - startValueFooterView) * value);
                mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
                mRefreshLayout.getAbsFooterView().onFinishPullUp(Math.abs(mFooterLayoutParams.bottomMargin) / mRefreshLayout.getFooterViewHeight());
                mRefreshLayout.requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
    
}
