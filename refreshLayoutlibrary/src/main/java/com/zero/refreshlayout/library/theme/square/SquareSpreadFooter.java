package com.zero.refreshlayout.library.theme.square;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.RefreshScrollUtil;
import com.zero.refreshlayout.library.footer.AbsFooterView;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class SquareSpreadFooter extends AbsFooterView {

    private final SquareSpreadView mSquareSpreadView;
    private final FrameLayout mFrameLayout;

    public SquareSpreadFooter(Context context) {
        super(context);
        mSquareSpreadView = new SquareSpreadView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, RefreshScrollUtil.dip2px(context, 85));
        layoutParams.gravity = Gravity.CENTER;
        mFrameLayout = new FrameLayout(context);
        mFrameLayout.addView(mSquareSpreadView, layoutParams);
    }

    @Override
    public View getContentView() {
        return mFrameLayout;
    }

    @Override
    public void onPullUp(float fraction) {

    }

    @Override
    public void onFinishPullUp(float fraction) {
        mSquareSpreadView.stopAnim();
    }

    @Override
    public void onLoadMore() {
        mSquareSpreadView.startAnim();
    }
}
