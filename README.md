
# 下拉刷新库RefreshLayout介绍

github地址：https://github.com/82367825/RefreshLayout

## 如何引入

在build.gradle文件中加入
```
compile 'com.zero.refreshlayout.library:RefreshLayout:1.0.0'
```

## 简单使用

首先是xml代码：

```
    <com.zero.refreshlayout.library.RefreshLayout
        android:id="@+id/refresh_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <TextView
            android:id="@+id/text_view"
            android:text="text"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
        
    </com.zero.refreshlayout.library.RefreshLayout>
```

在java代码设置相关的属性，并且设置监听器

```
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
                    }
                }, 2000);
            }
        });
```

更多的开放API：

>* void finishRefresh()
结束下拉刷新状态
>* void finishLoadMore()
结束上拉加载状态
>* void setHeaderViewHeight(int headerViewHeight)
设置HeaderView的高度,如果不设置的话，HeaderView默认为wrap_content
>* void setFooterViewHeight(int footerViewHeight)
设置FooterView的高度,如果不设置的话，FooterView默认为wrap_content
>* setHeaderViewMaxPullDistance(int headerViewMaxPullDistance)
设置下拉最大拉伸长度，如果不设置的话，默认为HeaderView高度的2.5倍
>* setFooterViewMaxPullDistance(int footerViewMaxPullDistance)
设置上拉最大拉伸长度，如果不设置的话，默认为FooterView高度的2.5倍
>* void setHeaderViewEnable(boolean headerViewEnable)
设置HeaderView是否可用
>* void setFooterViewEnable(boolean footerViewEnable) 
设置FooterView是否可用


## 个性化使用

### 1）如果你希望视图的布局样式不喜欢，这里暂时提供了另外两种布局样式。

如何切换布局样式，只需要调用
```
void setRefreshMode(RefreshMode refreshMode)
```

#### 默认样式 RefreshMode.LINEAR 

```
mRefreshLayout.setRefreshMode(RefreshMode.LINEAR);
```

![这里写图片描述](http://img.blog.csdn.net/20170907163209988?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvejgyMzY3ODI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


#### 覆盖样式 RefreshMode.COVER_LAYOUT_FRONT (布局覆盖在下拉控件之前)

```
mRefreshLayout.setRefreshMode(RefreshMode.COVER_LAYOUT_FRONT);
```
![这里写图片描述](http://img.blog.csdn.net/20170907163451311?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvejgyMzY3ODI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### 覆盖样式 RefreshMode.COVER_LAYOUT_BEHIND (布局被下拉控件覆盖)

```
mRefreshLayout.setRefreshMode(RefreshMode.COVER_LAYOUT_BEHIND);
```

![这里写图片描述](http://img.blog.csdn.net/20170907163520371?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvejgyMzY3ODI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 2) 最有必要的操作当然定制HeaderView以及FooterView

所有的HeaderView都必须继承AbsHeaderView, 所有的FooterView都必须继承AbsFooterView，我们看到，这里实现了一个TextHeaderView类

```
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

```

>* AbsHeaderView#onPullDown
   HeaderView正在被下拉时调用，fraction参数为HeaderView完全显示的比例
>* AbsHeaderView#onFinishPullDown
   HeaderView被松手弹回，fraction参数为HeaderView完全显示的比例
>* AbsHeaderView#onRefreshing
   HeaderView在处于刷新状态
   


### 3）如果我们只是想快速使用，可以使用现成的HeaderView以及FooterView, RefreshLayout暂时提供了几个样式（虽然看起来不咋地）

```
mRefreshLayout.setHeaderView(new BezierWaveHeader(this));
mRefreshLayout.setFooterView(new BezierWaveFooter(this));
```
![这里写图片描述](http://img.blog.csdn.net/20170907163608030?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvejgyMzY3ODI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```
mRefreshLayout.setHeaderView(new SquareSpreadHeader(this));
mRefreshLayout.setFooterView(new SquareSpreadFooter(this));
```
![这里写图片描述](http://img.blog.csdn.net/20170907163757753?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvejgyMzY3ODI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


### 更新历史

>*  v1.0.1  
修复bug




github地址：https://github.com/82367825/RefreshLayout
