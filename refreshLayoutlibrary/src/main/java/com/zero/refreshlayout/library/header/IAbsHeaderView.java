package com.zero.refreshlayout.library.header;

import android.view.View;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public interface IAbsHeaderView {
    
    View getContentView();
    
    void onPullDown(float fraction);
    
    void onFinishPullDown(float fraction);
    
    void onRefreshing();
    
}
