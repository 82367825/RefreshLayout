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

public class RefreshModeLinear extends AbsRefreshMode {

    private FrameLayout.LayoutParams mHeaderLayoutParams;
    private FrameLayout.LayoutParams mFooterLayoutParams;
    private FrameLayout.LayoutParams mContentLayoutParams;

    private boolean mIsAnimRunning = false;

    public RefreshModeLinear(RefreshLayout refreshLayout) {
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
            if (mRefreshLayout.getHeaderViewHeight() > Math.abs(distance)) {
                /* HeaderView下移 */
                mHeaderLayoutParams.topMargin = (int) (-mRefreshLayout.getHeaderViewHeight() + distance);
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
                mRefreshLayout.getAbsHeaderView().onPullDown(distance / mRefreshLayout.getHeaderViewHeight());
            } else {
                mHeaderLayoutParams.topMargin = 0;
                mRefreshLayout.getAbsHeaderView().getContentView().setLayoutParams(mHeaderLayoutParams);
                mRefreshLayout.getAbsHeaderView().onPullDown(1f);
            }
            if (mRefreshLayout.getHeaderViewMaxPullDistance() > Math.abs(distance)) {
                /* ContentView下移 */
                mContentLayoutParams.topMargin = (int) distance;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            } else {
                mContentLayoutParams.topMargin = mRefreshLayout.getHeaderViewMaxPullDistance();
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            }
            mRefreshLayout.requestLayout();
        }
    }

    @Override
    public void moveFooterView(float distance) {
        if (distance <= 0) {
            if (mRefreshLayout.getFooterViewHeight() > Math.abs(distance)) {
                /* FooterView上移 */
                mFooterLayoutParams.bottomMargin = (int) (-mRefreshLayout.getFooterViewHeight() +
                        Math.abs(distance));
                mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
                mRefreshLayout.getAbsFooterView().onPullUp(Math.abs(distance) / mRefreshLayout
                        .getFooterViewHeight());
            } else {
                mFooterLayoutParams.bottomMargin = 0;
                mRefreshLayout.getAbsFooterView().getContentView().setLayoutParams(mFooterLayoutParams);
                mRefreshLayout.getAbsFooterView().onPullUp(1f);
            }
            if (mRefreshLayout.getFooterViewMaxPullDistance() > Math.abs(distance)) {
                /* ContentView上移 */
                mContentLayoutParams.bottomMargin = (int) Math.abs(distance);
                mContentLayoutParams.topMargin = -mContentLayoutParams.bottomMargin;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            } else {
                mContentLayoutParams.bottomMargin = mRefreshLayout.getFooterViewMaxPullDistance();
                mContentLayoutParams.topMargin = -mContentLayoutParams.bottomMargin;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
            }
            mRefreshLayout.requestLayout();
        }
    }

    @Override
    public void moveToRefresh() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mContentLayoutParams.topMargin, mRefreshLayout.getHeaderViewHeight());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mContentLayoutParams.topMargin = value;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
                mRefreshLayout.requestLayout();
            }
        });
        valueAnimator.start();
        mRefreshLayout.getAbsHeaderView().onRefreshing();
        mIsAnimRunning = true;
    }

    @Override
    public void moveToLoadMore() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mContentLayoutParams.bottomMargin, mRefreshLayout.getFooterViewHeight());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mContentLayoutParams.bottomMargin = value;
                mContentLayoutParams.topMargin = -mContentLayoutParams.bottomMargin;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
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
        final int startValueContentView = mContentLayoutParams.topMargin;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mContentLayoutParams.topMargin = (int) (startValueContentView + (0 - startValueContentView) * value);
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
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
        final int startValueContentView = mContentLayoutParams.bottomMargin;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mContentLayoutParams.bottomMargin = (int) (startValueContentView + (0 - startValueContentView) * value);
                mContentLayoutParams.topMargin = - mContentLayoutParams.bottomMargin;
                mRefreshLayout.getContentView().setLayoutParams(mContentLayoutParams);
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
