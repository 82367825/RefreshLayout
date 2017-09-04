package com.zero.refreshlayout.library.theme;

import android.content.Context;
import android.view.View;

import com.zero.refreshlayout.library.header.AbsHeaderView;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class CopyHupuHeader extends AbsHeaderView {
    
    public CopyHupuHeader(Context context) {
        super(context);
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void onPullDown(float fraction) {

    }

    @Override
    public void onFinishPullDown(float fraction) {

    }

    @Override
    public void onRefreshing() {

    }
}
