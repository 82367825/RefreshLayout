package com.zero.refreshlayout.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
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
    
    private static final boolean DEFAULT_HEADER_VIEW_ENABLE = true;
    private static final boolean DEFAULT_FOOTER_VIEW_ENABLE = true;
    private static final float DEFAULT_DRAG_OBSTRUCTION = 0.75f;
    private boolean mIsHeaderViewEnable = DEFAULT_HEADER_VIEW_ENABLE;
    private boolean mIsFooterViewEnable = DEFAULT_FOOTER_VIEW_ENABLE;
    private int mHeaderViewHeight;
    private int mFooterViewHeight;
    private int mHeaderViewPullDistance;
    private int mFooterViewPullDistance;
    private int mHeaderViewMaxPullDistance;
    private int mFooterViewMaxPullDistance;

    private float mDragObstruction = DEFAULT_DRAG_OBSTRUCTION;
    private AbsHeaderView mAbsHeaderView;
    private AbsFooterView mAbsFooterView;
    private RefreshMode mRefreshMode = RefreshMode.LINEAR;
    private AbsRefreshMode mAbsRefreshMode;
    
    private View mContentView;
    
    private boolean mIsLayoutInit;
    private boolean mIsBeingDownDragged;
    private boolean mIsBeingUpDragged;
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
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (getChildCount() == 0 || getChildCount() >= 2) {
            throw new IllegalStateException("RefreshLayout should get only one child view.");
        }
        mContentView = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!mIsLayoutInit) {
            initLayout();
            mIsLayoutInit = true;
        }
    }
    
    private void initLayout() {
        if (mIsHeaderViewEnable) {
            if (getAbsHeaderView() == null) {
                throw new IllegalStateException("Please set HeaderView for RefreshLayout" +
                        ", or invoke RefreshLayout#setHeaderViewEnable(false)");
            }
            if (getHeaderViewHeight() == 0) {
                getAbsHeaderView().getContentView().measure(0, 0);
                setHeaderViewHeight(getAbsHeaderView().getContentView().getMeasuredHeight());
            }
            if (getHeaderViewPullDistance() == 0) {
                setHeaderViewPullDistance(getHeaderViewHeight());
            }
            if (getHeaderViewMaxPullDistance() == 0) {
                setHeaderViewMaxPullDistance((int) (getHeaderViewHeight() * 2.5f));
            }
        }
        if (mIsFooterViewEnable) {
            if (getAbsFooterView() == null) {
                throw new IllegalStateException("Please set FooterView for RefreshLayout" + 
                        ", or invoke RefreshLayout#setFooterViewEnable(false)");
            }
            if (getFooterViewHeight() == 0) {
                getAbsFooterView().getContentView().measure(0, 0);
                setFooterViewHeight(getAbsFooterView().getContentView().getMeasuredHeight());
            }
            if (getFooterViewPullDistance() == 0) {
                setFooterViewPullDistance(getFooterViewHeight());
            }
            if (getFooterViewMaxPullDistance() == 0) {
                setFooterViewMaxPullDistance((int) (getFooterViewHeight() * 2.5f));
            }
        }
        mAbsRefreshMode = RefreshModeFactory.createRefreshMode(mRefreshMode, this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDownDragged = false;
                mIsBeingUpDragged = false;
                mInitialDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY();
                startDragging(y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDownDragged = false;
                mIsBeingUpDragged = false;
                break;
        }
        return mIsBeingDownDragged || mIsBeingUpDragged;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDownDragged = false;
                mIsBeingUpDragged = false;
                mInitialDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY();
                startDragging(y);
                float moveDistance = (ev.getY() - mInitialDownY) * mDragObstruction;
                if (mIsBeingDownDragged && moveDistance >= 0) {
                    mAbsRefreshMode.moveHeaderView(moveDistance);
                } else if (mIsBeingUpDragged && moveDistance <= 0) {
                    mAbsRefreshMode.moveFooterView(moveDistance);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float resultDistance = (ev.getY() - mInitialDownY) * mDragObstruction;
                if (mIsBeingDownDragged) {
                    if (Math.abs(resultDistance) > mHeaderViewPullDistance) {
                        mAbsRefreshMode.moveToRefresh();
                        if (mRefreshListener != null) {
                            mRefreshListener.onRefresh();
                        }
                    } else {
                        mAbsRefreshMode.finishHeaderView();
                    }
                } else if (mIsBeingUpDragged) {
                    if (Math.abs(resultDistance) > mFooterViewPullDistance) {
                        mAbsRefreshMode.moveToLoadMore();
                        if (mRefreshListener != null) {
                            mRefreshListener.onLoadMore();
                        }
                    } else {
                        mAbsRefreshMode.finishFooterView();
                    }
                }
                break;
        }
        return true;
    }
    
    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if (Math.abs(yDiff) > mTouchSlop 
                && !mIsBeingUpDragged && !mIsBeingDownDragged && !mAbsRefreshMode.isAnimRunning()) {
            if (yDiff > 0) {
                if (mIsHeaderViewEnable && !RefreshScrollUtil.canChildPullDown(mContentView)) {
                    mIsBeingDownDragged = true;
                }
            } else {
                if (mIsFooterViewEnable && !RefreshScrollUtil.canChildPullUp(mContentView)) {
                    mIsBeingUpDragged = true;
                }
            }
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
        mAbsRefreshMode.finishHeaderView();
    }

    @Override
    public void finishLoadMore() {
        mAbsRefreshMode.finishFooterView();
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

    /* 暂时不开放 */
    protected int getHeaderViewPullDistance() {
        return mHeaderViewPullDistance;
    }

    public void setHeaderViewPullDistance(int headerViewPullDistance) {
        mHeaderViewPullDistance = headerViewPullDistance;
    }
    
    /* 暂时不开放 */
    protected int getFooterViewPullDistance() {
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
    
    public void setDragObstruction(float dragObstruction) {
        mDragObstruction = dragObstruction;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public RefreshListener getRefreshListener() {
        return mRefreshListener;
    }
    
}
