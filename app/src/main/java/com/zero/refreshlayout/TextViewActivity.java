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

public class TextViewActivity extends Activity {

    private TextView mTextView;
    private RefreshLayout mRefreshLayout;
    
    private StringBuilder mStringBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        mStringBuilder = new StringBuilder();
        mStringBuilder.append("1");

        mTextView = (TextView) findViewById(R.id.text_view);
        mTextView.setText(mStringBuilder.toString());

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
                        mStringBuilder = new StringBuilder("1");
                        mTextView.setText(mStringBuilder.toString());
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                MainThreadHandler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishLoadMore();
                        mStringBuilder.append("1");
                        mTextView.setText(mStringBuilder.toString());
                    }
                }, 2000);
            }
        });
        
    }
}
