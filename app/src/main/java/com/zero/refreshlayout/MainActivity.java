package com.zero.refreshlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zero.refreshlayout.theme.BezierWaveActivity;
import com.zero.refreshlayout.theme.LightHeaderActivity;
import com.zero.refreshlayout.theme.RoundSpreadActivity;
import com.zero.refreshlayout.theme.SquareSpreadActivity;

/**
 * @author linzewu
 * @date 2017/8/30
 */

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_listview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));   
            }
        });
        
        findViewById(R.id.button_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));    
            }
        });
        
        findViewById(R.id.button_scrollview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollViewActivity.class));
            }
        });
        
        findViewById(R.id.button_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextViewActivity.class));
            }
        });
        
        findViewById(R.id.button_theme_light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LightHeaderActivity.class));                
            }
        });
        
        findViewById(R.id.button_theme_square_spread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SquareSpreadActivity.class));
            }
        });

        findViewById(R.id.button_theme_round_spread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoundSpreadActivity.class));
            }
        });
        
        findViewById(R.id.button_theme_bezier_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BezierWaveActivity.class));
            }
        });
    }
}
