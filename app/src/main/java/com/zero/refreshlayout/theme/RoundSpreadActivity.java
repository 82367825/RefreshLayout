package com.zero.refreshlayout.theme;

import android.app.Activity;
import android.os.Bundle;

import com.zero.refreshlayout.MainThreadHandler;
import com.zero.refreshlayout.R;
import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.theme.round.RoundSpreadFooter;
import com.zero.refreshlayout.library.theme.round.RoundSpreadHeader;
import com.zero.refreshlayout.library.theme.square.SquareSpreadFooter;
import com.zero.refreshlayout.library.theme.square.SquareSpreadHeader;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class RoundSpreadActivity extends Activity {

    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_round_spread);

        mRefreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setHeaderView(new RoundSpreadHeader(this));
        mRefreshLayout.setFooterView(new RoundSpreadFooter(this));
        mRefreshLayout.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                MainThreadHandler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefresh();
                    }
                }, 10000);
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
