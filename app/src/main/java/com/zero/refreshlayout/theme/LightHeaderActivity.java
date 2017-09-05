package com.zero.refreshlayout.theme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zero.refreshlayout.MainThreadHandler;
import com.zero.refreshlayout.R;
import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.footer.TextFooterView;
import com.zero.refreshlayout.library.header.TextHeaderView;
import com.zero.refreshlayout.library.refreshMode.RefreshMode;
import com.zero.refreshlayout.library.theme.LightHeader;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class LightHeaderActivity extends Activity {

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
        mRefreshLayout.setHeaderView(new LightHeader(this));
        mRefreshLayout.setFooterView(new TextFooterView(this));
        mRefreshLayout.setRefreshMode(RefreshMode.COVER_LAYOUT_FRONT);
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
