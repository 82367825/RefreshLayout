package com.zero.refreshlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.footer.TextFooterView;
import com.zero.refreshlayout.library.header.TextHeaderView;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class ScrollViewActivity extends Activity {

    
    private RefreshLayout mRefreshLayout;
    private LinearLayout mLinearLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

        mLinearLayout = (LinearLayout) findViewById(R.id.content_layout);
        
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setHeaderView(new TextHeaderView(this));
        mRefreshLayout.setFooterView(new TextFooterView(this));
        mRefreshLayout.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                MainThreadHandler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                MainThreadHandler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishLoadMore();
                        TextView textView = new TextView(ScrollViewActivity.this);
                        textView.setText("TextView");
                        textView.setPadding(50, 50, 50, 50);
                        textView.setGravity(Gravity.CENTER);
                        mLinearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup
                                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }, 2000);
            }
        });
    }
    
    
    
}
