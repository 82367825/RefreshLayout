package com.zero.refreshlayout.library;

import com.zero.refreshlayout.library.footer.AbsFooterView;
import com.zero.refreshlayout.library.header.AbsHeaderView;
import com.zero.refreshlayout.library.refreshMode.RefreshMode;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public interface IRefreshLayout {
    
    void setRefreshMode(RefreshMode refreshMode);
    
    void setHeaderView(AbsHeaderView headerView);
    
    void setFooterView(AbsFooterView footerView);
    
    void finishRefresh();
    
    void finishLoadMore();
    
}
