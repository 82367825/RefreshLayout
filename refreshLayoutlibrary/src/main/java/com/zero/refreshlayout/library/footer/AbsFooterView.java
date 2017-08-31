package com.zero.refreshlayout.library.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author linzewu
 * @date 2017/8/25
 */

public abstract class AbsFooterView extends View implements IAbsFooterView {

    public AbsFooterView(Context context) {
        super(context);
    }

    public AbsFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
