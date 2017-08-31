package com.zero.refreshlayout.library.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public abstract class AbsHeaderView extends View implements IAbsHeaderView {

    public AbsHeaderView(Context context) {
        super(context);
    }

    public AbsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
