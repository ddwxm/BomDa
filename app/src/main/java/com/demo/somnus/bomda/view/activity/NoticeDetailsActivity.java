package com.demo.somnus.bomda.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.AnnouncementDetails;
import com.demo.somnus.bomda.presenter.IPresenter.INoticeDetailsPresenter;
import com.demo.somnus.bomda.presenter.NoticeDetailsPresenter;
import com.demo.somnus.bomda.view.IView.INoticeDetailsActivity;


/**
 * Created by Somnus on 2018/4/5.
 * 校园公告详情Activity
 */

public class NoticeDetailsActivity extends AppCompatActivity implements INoticeDetailsActivity {

    private String href,title;
    private Toolbar toolbar;
    private TextView details_content,details_content_time,details_content_source;
    private INoticeDetailsPresenter iNoticeDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNoticeDetailsPresenter = new NoticeDetailsPresenter(this);
        setContentView(R.layout.activity_notice_details);
        initIntent();
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iNoticeDetailsPresenter.details(this,href);
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.announcement_toolbar);
        toolbar.setTitle(title);
        this.setSupportActionBar(toolbar);
        details_content = (TextView) findViewById(R.id.details_content);
        details_content_source = (TextView) findViewById(R.id.details_content_source);
        details_content_time = (TextView) findViewById(R.id.details_content_time);
    }

    private void initIntent(){
        Intent intent = getIntent();
        href = intent.getStringExtra("href");
        title = intent.getStringExtra("title");
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

    @Override
    public void NoticeDetailsLoadSuccess(AnnouncementDetails announcementDetails) {
        String content = "";
        for(int i = 0; i< announcementDetails.getContent().size(); i++){
            content += announcementDetails.getContent().get(i)+"\n";
        }
        details_content.setText(content);
        if (announcementDetails.getSource().equals("本站")){
            details_content_source.setText("海南师范大学");
        }
        details_content_time.setText(announcementDetails.getTime());
    }
}
