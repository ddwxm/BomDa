package com.demo.somnus.bomda.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.MarketAdapter;
import com.demo.somnus.bomda.adapter.TeaseAdapter;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.model.bean.Treasure;
import com.demo.somnus.bomda.presenter.IPresenter.IMarketPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.ITeasePresenter;
import com.demo.somnus.bomda.presenter.MarketPresenter;
import com.demo.somnus.bomda.presenter.TeasePresenter;
import com.demo.somnus.bomda.view.IView.IMarketFragment;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 二手市场Fragment
 */

public class MarketFragment extends Fragment implements IMarketFragment
        ,SwipeRefreshLayout.OnRefreshListener {
    private IMarketPresenter iMarketPresenter;
    private View rootView;
    private SwipeRefreshLayout fragment_market_refresh;
    private RecyclerView fragment_market_rv;
    private MarketAdapter marketAdapter;
    private AVLoadingIndicatorView market_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iMarketPresenter = new MarketPresenter(this);
        rootView = inflater.inflate(R.layout.fragment_market, container, false);
        initWidget(rootView);
        initClick();
        return rootView;
    }

    private void initWidget(View view){
        fragment_market_refresh = (SwipeRefreshLayout) view.findViewById(R.id.fragment_market_refresh);
        fragment_market_rv = (RecyclerView) view.findViewById(R.id.fragment_market_rv);
        marketAdapter = new MarketAdapter(fragment_market_rv,getContext());
        fragment_market_rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        fragment_market_rv.setAdapter(marketAdapter);
        market_loading = (AVLoadingIndicatorView) view.findViewById(R.id.market_loading);
        market_loading.show();
        iMarketPresenter.queryMarket();
    }

    private void initClick(){

    }

    @Override
    public void loadSiftMarketList(List<Treasure> treasureList) {
        if (fragment_market_refresh.isRefreshing()){
            fragment_market_refresh.setRefreshing(false);
        }
        marketAdapter.setMarketList(treasureList);
        market_loading.hide();
    }

    @Override
    public void onRefresh() {
        iMarketPresenter.queryMarket();
    }
}
