package com.zero.refreshlayout;

import android.os.Handler;
import android.os.Looper;

/**
 * @author linzewu
 * @date 2017/9/4
 */

public class MainThreadHandler {
    
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    
    public static void execute(Runnable runnable) {
        sHandler.post(runnable);
    }
    
    public static void execute(Runnable runnable, long delayTime) {
        sHandler.postDelayed(runnable, delayTime);
    }
    
}
