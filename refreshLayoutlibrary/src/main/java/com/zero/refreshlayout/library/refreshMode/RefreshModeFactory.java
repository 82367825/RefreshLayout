package com.zero.refreshlayout.library.refreshMode;

import com.zero.refreshlayout.library.RefreshLayout;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class RefreshModeFactory {
    
    public static AbsRefreshMode createRefreshMode(RefreshMode refreshMode,
                                                   RefreshLayout refreshLayout) {
        AbsRefreshMode absRefreshMode = null;
        switch (refreshMode) {
            case LINEAR:
                absRefreshMode = new RefreshModeLinear(refreshLayout);
                break;
            case COVER_LAYOUT_FRONT:
                absRefreshMode = new RefreshModeLayoutFront(refreshLayout);
                break;
            case COVER_LAYOUT_BEHIND:
                absRefreshMode = new RefreshModeLayoutBehind(refreshLayout);
                break;
        }
        return absRefreshMode;
    }
    
    
}
