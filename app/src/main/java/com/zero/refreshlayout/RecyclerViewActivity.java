package com.zero.refreshlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.refreshlayout.library.RefreshLayout;
import com.zero.refreshlayout.library.RefreshListener;
import com.zero.refreshlayout.library.footer.TextFooterView;
import com.zero.refreshlayout.library.header.TextHeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class RecyclerViewActivity extends Activity {
    
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        
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
                        mStringList.add(String.valueOf(mStringList.size() + 1));
                        mStringList.add(String.valueOf(mStringList.size() + 1));
                        mStringList.add(String.valueOf(mStringList.size() + 1));
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });


        mStringList = new ArrayList<>();
        mStringList.add("1");
        mStringList.add("2");
        mStringList.add("3");
        
        mAdapter = new RecyclerAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        
    }
    
    
    
    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    
    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(50, 50, 50, 50);
            textView.setTextSize(25);
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }
    
}
