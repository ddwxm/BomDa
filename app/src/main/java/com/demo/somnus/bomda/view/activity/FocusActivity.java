package com.demo.somnus.bomda.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.FocusAdapter;
import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.FocusPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IFocusPresenter;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.IView.IFocusActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.V;

/**
 * Created by Somnus on 2018/4/15.
 * 关注activity
 */

public class FocusActivity extends AppCompatActivity implements IFocusActivity
        ,SwipeRefreshLayout.OnRefreshListener {
    private IFocusPresenter iFocusPresenter;
    private SwipeRefreshLayout focus_refresh;
    private RecyclerView focus_recycler;
    private AVLoadingIndicatorView focus_loading;
    private LinearLayout focus_noData;
    private FocusAdapter focusAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iFocusPresenter = new FocusPresenter(this);
        setContentView(R.layout.activity_focus);

        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 控件初始化
     */
    private void init(){
        focus_refresh = (SwipeRefreshLayout) findViewById(R.id.focus_refresh);
        focus_refresh.setOnRefreshListener(this);
        focus_noData = (LinearLayout) findViewById(R.id.focus_noData);
        focus_noData.setVisibility(View.GONE);
        focus_recycler = (RecyclerView) findViewById(R.id.focus_recycler);
        focus_recycler.setLayoutManager(new LinearLayoutManager(this));
        focus_recycler.setItemAnimator(new DefaultItemAnimator());
        focus_recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        focusAdapter = new FocusAdapter(focus_recycler,FocusActivity.this);
        focus_recycler.setAdapter(focusAdapter);
        focus_loading = (AVLoadingIndicatorView) findViewById(R.id.focus_loading);
        iFocusPresenter.queryFocus(BmobUser.getCurrentUser(User.class));
        focus_loading.show();
    }

    @Override
    public void loadFocusListSuccess(List<Focus> focusList) {
        focusAdapter.setFocusList(focusList);
        Log.e("id",focusList.get(0).getUser().getObjectId());
        focus_loading.hide();
    }

    @Override
    public void loadFocusListFail(String msg, int code) {
        ToastUtil.showShort(FocusActivity.this,"出了点问题呢"+msg+code);
        focus_loading.hide();
    }

    @Override
    public void noFocus() {
        focus_loading.hide();
        focus_noData.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshSuccess() {
        if (focus_refresh.isRefreshing()){
            focus_refresh.setRefreshing(false);
            ToastUtil.showShort(FocusActivity.this,"刷新成功");
        }
    }

    @Override
    public void onRefresh() {
        iFocusPresenter.queryFocus(BmobUser.getCurrentUser(User.class));
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
