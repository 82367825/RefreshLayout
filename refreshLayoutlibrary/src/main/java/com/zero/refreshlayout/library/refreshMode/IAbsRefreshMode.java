package com.zero.refreshlayout.library.refreshMode;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public interface IAbsRefreshMode {
    
    boolean isAnimRunning();
    
    void moveHeaderView(float distance);
    
    void moveFooterView(float distance);
    
    void moveToRefresh();
    
    void moveToLoadMore();
    
    void finishHeaderView(float distance);
    
    void finishFooterView(float distance);
    
    void destroy();
    
}
