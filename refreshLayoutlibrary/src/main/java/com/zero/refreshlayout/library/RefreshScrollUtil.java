package com.zero.refreshlayout.library;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class RefreshScrollUtil {

    /**
     * 是否可以继续下拉
     * @param targetView
     * @return
     */
    public static boolean canChildPullDown(View targetView) {
        /* 模仿SwipeRefreshLayout */
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

    /**
     * 是否可以继续上拉
     * @param targetView
     * @return
     */
    public static boolean canChildPullUp(View targetView) {
        /* 模仿SwipeRefreshLayout */
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getAdapter().getCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(targetView, 1);
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, 1);
        }
    }
    
    public static int dip2px(Context context, float var1) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return (int) (var1 * var2 + 0.5F);
    }

    public static int px2dip(Context context, float var1) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return (int) (var1 / var2 + 0.5F);
    }
    
}
