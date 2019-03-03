package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.NoticeAdapter;
import com.demo.somnus.bomda.model.bean.Announcement;
import com.demo.somnus.bomda.presenter.IPresenter.ISchoolNoticePresenter;
import com.demo.somnus.bomda.presenter.NoticePresenter;
import com.demo.somnus.bomda.view.IView.ISchoolNoticeActivity;

import java.util.List;

/**
 * Created by Somnus on 2018/4/5.
 * 校园公告Activity
 */

public class NoticeActivity extends AppCompatActivity implements ISchoolNoticeActivity
        ,SwipeRefreshLayout.OnRefreshListener{
    private ISchoolNoticePresenter iSchoolNoticePresenter;
    private SwipeRefreshLayout announcement_refresh;
    private RecyclerView announcement_recycler;
    private NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iSchoolNoticePresenter = new NoticePresenter(this);
        setContentView(R.layout.activity_notice);
        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        announcement_refresh = (SwipeRefreshLayout) findViewById(R.id.announcement_refresh);
        announcement_refresh.setColorSchemeResources(R.color.colorPrimary);
        announcement_refresh.setOnRefreshListener(this);
        announcement_recycler = (RecyclerView) findViewById(R.id.announcement_recycler);
        announcement_recycler.setLayoutManager(new LinearLayoutManager(this));
        noticeAdapter = new NoticeAdapter(announcement_recycler,this);
        announcement_recycler.setAdapter(noticeAdapter);
        iSchoolNoticePresenter.getNotice(this);
    }


    @Override
    public void onLoadNoticeListSuccess(List<Announcement> announcementList) {
        noticeAdapter.setAnnouncementList(announcementList);
    }

    @Override
    public void onRefresh() {
        if (announcement_refresh.isRefreshing()) {
            // 获取公告列表
            iSchoolNoticePresenter.getNotice(this);
        }
    }

    @SuppressLint("HandlerLeak")
    android.os.Handler handler= new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            announcement_refresh.setRefreshing(false);
        }
    };

    @Override
    public void RefreshSuccess() {
        Message ok = new Message();
        handler.sendMessage(ok);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }
}
