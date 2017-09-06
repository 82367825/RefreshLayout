package com.zero.refreshlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ListViewActivity extends Activity {

    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private ListViewAdapter mListViewAdapter;
    private List<String> mStringList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

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
                        mListViewAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        mStringList = new ArrayList<>();
        mStringList.add("1");
        mStringList.add("2");
        mStringList.add("3");
        mListViewAdapter = new ListViewAdapter(mStringList);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, "click item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        
    }
    
    private class ListViewAdapter extends BaseAdapter {

        private List<String> mStringList;
        
        public ListViewAdapter(List<String> stringList) {
            mStringList = stringList;
        }
        
        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return mStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(50, 50, 50, 50);
            textView.setTextSize(25);
            textView.setText(mStringList.get(position));
            return textView;
        }
    }
    
}
