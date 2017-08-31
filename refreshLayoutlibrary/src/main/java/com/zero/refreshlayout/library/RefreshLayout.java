package com.zero.refreshlayout.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.zero.refreshlayout.library.footer.AbsFooterView;
import com.zero.refreshlayout.library.header.AbsHeaderView;
import com.zero.refreshlayout.library.refreshMode.AbsRefreshMode;
import com.zero.refreshlayout.library.refreshMode.RefreshMode;
import com.zero.refreshlayout.library.refreshMode.RefreshModeFactory;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public class RefreshLayout extends FrameLayout implements IRefreshLayout {
    
    private boolean mIsHeaderViewEnable;
    private boolean mIsFooterViewEnable;
    private int mHeaderViewHeight;
    private int mFooterViewHeight;
    private int mHeaderViewPullDistance;
    private int mFooterViewPullDistance;
    private int mHeaderViewMaxPullDistance;
    private int mFooterViewMaxPullDistance;

    private float mDragObstruction = 0.75f;
    private AbsHeaderView mAbsHeaderView;
    private AbsFooterView mAbsFooterView;
    private RefreshMode mRefreshMode;
    private AbsRefreshMode mAbsRefreshMode;
    
    private View mContentView;
    
    private boolean mIsBeingDragged;
    private float mTouchSlop;
    private float mInitialDownY;
    
    private RefreshListener mRefreshListener;
    
    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0 || getChildCount() >= 2) {
//            throw new Exception("");
        }
        if (mIsHeaderViewEnable && mAbsHeaderView == null) {
            return ;
        }
        if (mIsFooterViewEnable && mAbsFooterView == null) {
            return ;
        }
        if (getChildCount() == 1) {
            mContentView = getChildAt(0);
        }
        if (getHeaderViewHeight() == 0) {
            getAbsHeaderView().measure(0, 0);
            setHeaderViewHeight(getAbsHeaderView().getMeasuredHeight());
        }
        if (getFooterViewHeight() == 0) {
            getAbsFooterView().measure(0, 0);
            setFooterViewHeight(getAbsFooterView().getMeasuredHeight());
        }
        if (getHeaderViewPullDistance() == 0) {
            setHeaderViewPullDistance(getHeaderViewHeight());
        }
        if (getFooterViewPullDistance() == 0) {
            setFooterViewPullDistance(getFooterViewHeight());
        }
        if (getHeaderViewMaxPullDistance() == 0) {
            setHeaderViewMaxPullDistance((int) (getHeaderViewHeight() * 2.5f));
        }
        if (getFooterViewMaxPullDistance() == 0) {
            setFooterViewMaxPullDistance((int) (getFooterViewHeight() * 2.5f));
        }
        mAbsRefreshMode = RefreshModeFactory.createRefreshMode(mRefreshMode, this);
    }
    
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (RefreshScrollUtil.canChildPullUp(mContentView) || 
                RefreshScrollUtil.canChildPullDown(mContentView)) {
            return false;
        }
        
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                mInitialDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY();
                startDragging(y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                break;
        }
        return mIsBeingDragged;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (RefreshScrollUtil.canChildPullUp(mContentView) || 
                RefreshScrollUtil.canChildPullDown(mContentView)) {
            return false;
        }
        
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY();
                startDragging(y);
                if (mIsBeingDragged) {
                    final float moveDistance = (getY() - mInitialDownY) * mDragObstruction;
                    if (moveDistance > 0) {
                        mAbsRefreshMode.moveHeaderView(moveDistance);
                    } else {
                        mAbsRefreshMode.moveFooterView(moveDistance);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    final float moveDistance = (getY() - mInitialDownY) * mDragObstruction;
                    mIsBeingDragged = false;
                    if (moveDistance > 0 ) {
                        if (Math.abs(moveDistance) > mHeaderViewPullDistance) {
                            mAbsRefreshMode.moveToRefresh();
                        } else {
                            mAbsRefreshMode.finishHeaderView(moveDistance);
                        }
                    } else {
                        if (Math.abs(moveDistance) > mFooterViewPullDistance) {
                            mAbsRefreshMode.moveToLoadMore();
                        } else {
                            mAbsRefreshMode.finishFooterView(moveDistance);   
                        }
                    }
                }
                return false;
        }
        
        return true;
    }
    
    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if (Math.abs(yDiff) > mTouchSlop && !mIsBeingDragged) {
            mIsBeingDragged = true;
        }
    }
    
    @Override
    public void setRefreshMode(RefreshMode refreshMode) {
        mRefreshMode = refreshMode;
    }

    @Override
    public void setHeaderView(AbsHeaderView headerView) {
        mAbsHeaderView = headerView;
    }
    
    @Override
    public void setFooterView(AbsFooterView footerView) {
        mAbsFooterView = footerView;
    }

    @Override
    public void finishRefresh() {
        mAbsRefreshMode.finishHeaderView(1);
    }

    @Override
    public void finishLoadMore() {
        mAbsRefreshMode.finishFooterView(-1);
    }

    public AbsHeaderView getAbsHeaderView() {
        return mAbsHeaderView;
    }

    public AbsFooterView getAbsFooterView() {
        return mAbsFooterView;
    }

    public int getHeaderViewHeight() {
        return mHeaderViewHeight;
    }

    public void setHeaderViewHeight(int headerViewHeight) {
        mHeaderViewHeight = headerViewHeight;
    }

    public int getFooterViewHeight() {
        return mFooterViewHeight;
    }

    public void setFooterViewHeight(int footerViewHeight) {
        mFooterViewHeight = footerViewHeight;
    }

    public void setHeaderViewEnable(boolean headerViewEnable) {
        mIsHeaderViewEnable = headerViewEnable;
    }

    public void setFooterViewEnable(boolean footerViewEnable) {
        mIsFooterViewEnable = footerViewEnable;
    }

    public int getHeaderViewPullDistance() {
        return mHeaderViewPullDistance;
    }

    public void setHeaderViewPullDistance(int headerViewPullDistance) {
        mHeaderViewPullDistance = headerViewPullDistance;
    }

    public int getFooterViewPullDistance() {
        return mFooterViewPullDistance;
    }

    public void setFooterViewPullDistance(int footerViewPullDistance) {
        mFooterViewPullDistance = footerViewPullDistance;
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public View getContentView() {
        return mContentView;
    }

    public int getHeaderViewMaxPullDistance() {
        return mHeaderViewMaxPullDistance;
    }

    public void setHeaderViewMaxPullDistance(int headerViewMaxPullDistance) {
        mHeaderViewMaxPullDistance = headerViewMaxPullDistance;
    }

    public int getFooterViewMaxPullDistance() {
        return mFooterViewMaxPullDistance;
    }

    public void setFooterViewMaxPullDistance(int footerViewMaxPullDistance) {
        mFooterViewMaxPullDistance = footerViewMaxPullDistance;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public RefreshListener getRefreshListener() {
        return mRefreshListener;
    }
}
