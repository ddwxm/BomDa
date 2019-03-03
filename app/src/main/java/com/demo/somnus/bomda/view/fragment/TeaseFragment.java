package com.demo.somnus.bomda.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.TeaseAdapter;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.presenter.IPresenter.ITeasePresenter;
import com.demo.somnus.bomda.presenter.TeasePresenter;
import com.demo.somnus.bomda.view.IView.ITeaseFragment;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 树洞Fragment
 */

public class TeaseFragment extends Fragment implements ITeaseFragment {
    private View rootView;
    private ITeasePresenter iTeasePresenter;
    private SwipeRefreshLayout fragment_tease_refresh;
    private RecyclerView fragment_tease_rv;
    private TeaseAdapter teaseAdapter;
    private AVLoadingIndicatorView tease_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iTeasePresenter = new TeasePresenter(this);
        rootView = inflater.inflate(R.layout.fragment_tease, container, false);
        initWidget(rootView);
        initClick();
        return rootView;
    }

    private void initWidget(View view){
        fragment_tease_refresh = (SwipeRefreshLayout) view.findViewById(R.id.fragment_tease_refresh);
        fragment_tease_rv = (RecyclerView) view.findViewById(R.id.fragment_tease_rv);
        teaseAdapter = new TeaseAdapter(fragment_tease_rv,getContext());
        fragment_tease_rv.setAdapter(teaseAdapter);
        fragment_tease_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragment_tease_rv.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
        snapHelperTop.attachToRecyclerView(fragment_tease_rv);
        tease_loading = (AVLoadingIndicatorView) view.findViewById(R.id.tease_loading);
        iTeasePresenter.query_Tease();
    }

    private void initClick(){

    }

    public void like(Tease tease){
        iTeasePresenter.like(tease);
    }

    @Override
    public void loadTeaseList(List<TeaseSpecific> teaseSpecifics) {
        teaseAdapter.setTeaseList(teaseSpecifics);
        tease_loading.hide();
    }
}
