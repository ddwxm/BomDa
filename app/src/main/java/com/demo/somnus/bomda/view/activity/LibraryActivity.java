package com.demo.somnus.bomda.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.LibraryAdapter;
import com.demo.somnus.bomda.adapter.NewsAdapter;
import com.demo.somnus.bomda.model.bean.Library;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.presenter.IPresenter.ILibraryPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.ISchoolNewsPresenter;
import com.demo.somnus.bomda.presenter.LibraryPresenter;
import com.demo.somnus.bomda.view.IView.ILibraryActivity;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

/**
 * Created by Somnus on 2018/4/15.
 * 图书馆Activity
 */

public class LibraryActivity extends AppCompatActivity implements ILibraryActivity {
    private ILibraryPresenter iLibraryPresenter;
    private RecyclerView library_recycler;
    private LibraryAdapter libraryAdapter;
    private PullToRefreshView pullToRefreshView;
    private static final int REFRESH_DELAY = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iLibraryPresenter = new LibraryPresenter(this);
        setContentView(R.layout.activity_library);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.news_refresh);
        library_recycler = (RecyclerView) findViewById(R.id.library_recycler);
        library_recycler.setLayoutManager(new LinearLayoutManager(this));
        libraryAdapter = new LibraryAdapter(library_recycler,this);
        library_recycler.setAdapter(libraryAdapter);
        iLibraryPresenter.getNews(this);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                        iLibraryPresenter.getNews(LibraryActivity.this);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    @Override
    public void loadLibraryList(List<Library> libraries) {
        libraryAdapter.setNewsList(libraries);
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
