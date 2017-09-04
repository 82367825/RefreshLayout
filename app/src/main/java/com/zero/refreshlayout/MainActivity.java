package com.zero.refreshlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, ListViewActivity.class));
        
    }
}
