package com.zero.refreshlayout.library.header;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @author linzewu
 * @date 2017/8/31
 */

public class TextHeaderView extends AbsHeaderView {

    private TextView mTextView;
    
    public TextHeaderView(Context context) {
        super(context);
        mTextView = new TextView(context);
        mTextView.setBackgroundColor(0xFFFFFFFF);
        mTextView.setTextColor(0xFF000000);
        mTextView.setTextSize(20);
        mTextView.setPadding(50, 50, 50, 50);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setText("下拉刷新");
    }

    @Override
    public View getContentView() {
        return mTextView;
    }

    @Override
    public void onPullDown(float fraction) {
        mTextView.setText("下拉刷新");
    }

    @Override
    public void onFinishPullDown(float fraction) {
        
    }

    @Override
    public void onRefreshing() {
        mTextView.setText("刷新...");
    }
}
