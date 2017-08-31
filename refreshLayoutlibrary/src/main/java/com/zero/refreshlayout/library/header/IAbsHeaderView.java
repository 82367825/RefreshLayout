package com.zero.refreshlayout.library.header;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public interface IAbsHeaderView {
    
    void onPullDown(float fraction);
    
    void onFinishPullDown(float fraction);
    
    void onRefreshing();
    
}
