package com.zero.refreshlayout.library.footer;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public interface IAbsFooterView {

    void onPullUp(float fraction);

    void onFinishPullUp(float fraction);

    void onLoadMore();
    
}
