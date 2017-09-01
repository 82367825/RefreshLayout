package com.zero.refreshlayout.library.footer;

import android.view.View;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public interface IAbsFooterView {
    
    View getContentView();

    void onPullUp(float fraction);

    void onFinishPullUp(float fraction);

    void onLoadMore();
    
}
