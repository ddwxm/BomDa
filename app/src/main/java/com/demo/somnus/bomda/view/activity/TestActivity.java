package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Treasure;
import com.demo.somnus.bomda.view.IView.ITestActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Somnus on 2018/4/5.
 * 考试Activity
 */

public class TestActivity extends AppCompatActivity implements ITestActivity {
    private AVLoadingIndicatorView test_loading;
    private LinearLayout noData;
    private RecyclerView test_re;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 2:
                    test_loading.hide();
                    noData.setVisibility(View.VISIBLE);
                    test_re.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
        initData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        test_loading = (AVLoadingIndicatorView) findViewById(R.id.test_loading);
        test_loading.show();
        noData = (LinearLayout) findViewById(R.id.test_noData);
        test_re = (RecyclerView) findViewById(R.id.test_re);
    }

    private void initData(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg =Message.obtain();
                msg.what = 2;   //标志消息的标志
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask,2000);
    }

}
