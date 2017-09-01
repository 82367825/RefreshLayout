package com.zero.refreshlayout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.footer.TextFooterView;
import com.zero.refreshlayout.library.header.TextHeaderView;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class MainActivity extends Activity {
    
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setHeaderView(new TextHeaderView(this));
        refreshLayout.setFooterView(new TextFooterView(this));
        refreshLayout.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
        
    }
}
