package com.zero.refreshlayout.library.refreshMode;

import com.zero.refreshlayout.library.RefreshLayout;


/**
 * @author linzewu
 * @date 2017/8/30
 */

public abstract class AbsRefreshMode implements IAbsRefreshMode {

    protected static final int ANIM_TIME_FINISH_REFRESH = 500;
    protected static final int ANIM_TIME_FINISH_LOAD_MORE = 500;
    protected static final int ANIM_TIME_MOVE_TO_REFRESH = 500;
    protected static final int ANIM_TIME_MOVE_TO_LOAD_MORE = 500;
    
    protected RefreshLayout mRefreshLayout;
    
    public AbsRefreshMode(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        initLayout();
    }
    
    protected abstract void initLayout();


    @Override
    public void destroy() {
        mRefreshLayout = null;
    }
}
