package com.demo.somnus.bomda.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.CollectionAdapter;
import com.demo.somnus.bomda.presenter.CollectPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.ICollectPresenter;
import com.demo.somnus.bomda.view.IView.ICollectActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Somnus on 2018/4/11.
 * 收藏Activity
 */

public class CollectActivity extends AppCompatActivity implements ICollectActivity
        ,View.OnClickListener {
    private ICollectPresenter iCollectPresenter;
    private CollectionAdapter collectionAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().statusBarColor(R.color.colorPrimary).init();
        iCollectPresenter = new CollectPresenter(this) ;
        setContentView(R.layout.activity_collection);

        init();
        initClick();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 控件初始化
     */
    private void init(){;
        toolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        this.setSupportActionBar(toolbar);
        collectionAdapter = new CollectionAdapter(getSupportFragmentManager(),CollectActivity.this);
        mViewPager = (ViewPager) findViewById(R.id.collect_viewPager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.collect_vpi);

        mViewPager.setAdapter(collectionAdapter);
        mViewPager.setCurrentItem(0);
        slidingTabLayout.setViewPager(mViewPager);
    }

    private void initClick(){
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
