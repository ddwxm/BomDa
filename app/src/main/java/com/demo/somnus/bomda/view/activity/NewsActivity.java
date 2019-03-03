package com.demo.somnus.bomda.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.NewsAdapter;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.presenter.IPresenter.ISchoolNewsPresenter;
import com.demo.somnus.bomda.presenter.NewsPresenter;
import com.demo.somnus.bomda.view.IView.ISchoolNewsActivity;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

/**
 * Created by Somnus on 2018/4/5.
 * 校内新闻Activity
 */

public class NewsActivity extends AppCompatActivity implements ISchoolNewsActivity {
    private ISchoolNewsPresenter iNewsPresenter;
    private RecyclerView news_recycler;
    private NewsAdapter newsAdapter;
    private PullToRefreshView pullToRefreshView;
    private static final int REFRESH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNewsPresenter = new NewsPresenter(this);
        setContentView(R.layout.activity_news);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.news_refresh);
        news_recycler = (RecyclerView) findViewById(R.id.news_recycler);
        news_recycler.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(news_recycler,this);
        news_recycler.setAdapter(newsAdapter);
        iNewsPresenter.getNews(this);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                        iNewsPresenter.getNews(NewsActivity.this);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    @Override
    public void onLoadNewsListSuccess(List<News> newsList) {
        newsAdapter.setNewsList(newsList);
    }

    @Override
    public void RefreshSuccess() {
        pullToRefreshView.setRefreshing(false);
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
