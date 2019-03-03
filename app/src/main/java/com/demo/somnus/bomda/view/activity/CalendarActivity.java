package com.demo.somnus.bomda.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.demo.somnus.bomda.R;

/**
 * Created by Somnus on 2018/5/12.
 * 校历Activity
 */

public class CalendarActivity extends AppCompatActivity {
    private RelativeLayout calendar_first,calendar_two;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        calendar_first = (RelativeLayout) findViewById(R.id.calendar_first);
        calendar_two = (RelativeLayout) findViewById(R.id.calendar_second);
        calendar_first.setVisibility(View.VISIBLE);
        calendar_two.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.activity_calendar_one:
                calendar_first.setVisibility(View.VISIBLE);
                calendar_two.setVisibility(View.GONE);
                break;
            case R.id.activity_calendar_two:
                calendar_two.setVisibility(View.VISIBLE);
                calendar_first.setVisibility(View.GONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
