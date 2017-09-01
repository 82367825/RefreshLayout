package com.zero.refreshlayout.library.footer;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @author linzewu
 * @date 2017/8/31
 */

public class TextFooterView extends AbsFooterView {

    private TextView mTextView;
    
    public TextFooterView(Context context) {
        super(context);
        mTextView = new TextView(context);
        mTextView.setTextColor(0xFF000000);
        mTextView.setTextSize(20);
        mTextView.setPadding(20, 20, 20, 20);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setText("上拉加载更多");
    }
    
    @Override
    public View getContentView() {
        return mTextView;
    }

    @Override
    public void onPullUp(float fraction) {
        mTextView.setText("上拉加载更多");
    }

    @Override
    public void onFinishPullUp(float fraction) {

    }

    @Override
    public void onLoadMore() {
        mTextView.setText("加载更多...");
    }
}
