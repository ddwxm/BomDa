package com.demo.somnus.bomda.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.SiftTeaseAdapter;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.presenter.IPresenter.ISiftPresenter;
import com.demo.somnus.bomda.presenter.SiftPresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.view.IView.ISiftFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 精选Fragment
 */

public class SiftFragment extends Fragment implements ISiftFragment
        ,SwipeRefreshLayout.OnRefreshListener {
    private ISiftPresenter iSiftPresenter;
    private SiftTeaseAdapter siftTeaseAdapter;
    private View rootView;
    private RecyclerView sift_tease,sift_market;
    private AVLoadingIndicatorView mSift_loading;
    private SwipeRefreshLayout mSift_refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iSiftPresenter = new SiftPresenter(this);
        rootView = inflater.inflate(R.layout.fragment_sift, container, false);
        initWidget(rootView);
        initClick();
        return rootView;
    }

    private void initWidget(View view){
        mSift_loading = (AVLoadingIndicatorView) view.findViewById(R.id.sift_loading);
        mSift_refresh = (SwipeRefreshLayout) view.findViewById(R.id.sift_refresh);
        mSift_refresh.setOnRefreshListener(this);
        mSift_refresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mSift_refresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorCyan));
        sift_tease = (RecyclerView) view.findViewById(R.id.sift_tease);
        sift_tease.setLayoutManager(new LinearLayoutManager(getActivity()));
        sift_tease.setItemAnimator(new DefaultItemAnimator());
        siftTeaseAdapter = new SiftTeaseAdapter(sift_tease,getContext());
        sift_tease.setAdapter(siftTeaseAdapter);

        mSift_loading.show();
        sift_tease.setVisibility(View.GONE);
        iSiftPresenter.query_Tease(true);
    }

    private void initClick(){

    }

    @Override
    public void loadSiftTeaseList(List<TeaseSpecific> teaseSpecifics) {
        Log.e("teaseSpecifics"+ ConstantsUtil.TAG_SIZE,teaseSpecifics.size()+"");
        for (TeaseSpecific teaseSpecific: teaseSpecifics){
            Log.e("teaseSpecific",teaseSpecific.getTease().getContent());
        }
        siftTeaseAdapter.setTeaseList(teaseSpecifics);
        mSift_loading.hide();
        sift_tease.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {

    }
}
