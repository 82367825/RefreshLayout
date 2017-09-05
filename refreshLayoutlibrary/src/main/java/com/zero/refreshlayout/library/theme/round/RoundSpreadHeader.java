package com.zero.refreshlayout.library.theme.round;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.RefreshScrollUtil;
import com.zero.refreshlayout.library.header.AbsHeaderView;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class RoundSpreadHeader extends AbsHeaderView {

    private FrameLayout mFrameLayout;
    private RoundSpreadView mRoundSpreadView;
    
    public RoundSpreadHeader(Context context) {
        super(context);
        mRoundSpreadView = new RoundSpreadView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, RefreshScrollUtil.dip2px(context, 85));
        layoutParams.gravity = Gravity.CENTER;
        mFrameLayout = new FrameLayout(context);
        mFrameLayout.addView(mRoundSpreadView, layoutParams);
    }

    @Override
    public View getContentView() {
        return mFrameLayout;
    }

    @Override
    public void onPullDown(float fraction) {

    }

    @Override
    public void onFinishPullDown(float fraction) {
        mRoundSpreadView.stopAnim();
    }

    @Override
    public void onRefreshing() {
        mRoundSpreadView.startAnim();
    }
}
