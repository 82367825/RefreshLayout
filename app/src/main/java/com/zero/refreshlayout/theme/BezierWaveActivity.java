package com.zero.refreshlayout.theme;

import android.app.Activity;
import android.os.Bundle;

import com.zero.refreshlayout.MainThreadHandler;
import com.zero.refreshlayout.R;
import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.refreshMode.RefreshMode;
import com.zero.refreshlayout.library.theme.bezier.BezierWaveFooter;
import com.zero.refreshlayout.library.theme.bezier.BezierWaveHeader;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class BezierWaveActivity extends Activity {

    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_bezier_wave);

        mRefreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setHeaderView(new BezierWaveHeader(this));
        mRefreshLayout.setFooterView(new BezierWaveFooter(this));
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
                    }
                }, 2000);
            }
        });
    }
}
