# RefreshLayout
下拉刷新框架



>引入方式：
```
com.zero.refreshlayout.library:RefreshLayout:1.0.0
```

## 1 基本代码调用
```
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
                    }
                }, 2000);
            }
        });

```


## 2 下拉视图形式多样化

```

```

## 3 HeaderView, FooterView定制
