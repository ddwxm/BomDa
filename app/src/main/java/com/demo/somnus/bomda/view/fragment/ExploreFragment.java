package com.demo.somnus.bomda.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.ExploreAdapter;
import com.demo.somnus.bomda.presenter.ExplorePresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IExplorePresenter;
import com.demo.somnus.bomda.view.IView.IExploreFragment;
import com.demo.somnus.bomda.view.activity.MainActivity;
import com.demo.somnus.bomda.view.activity.PostTeaseActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.github.clans.fab.FloatingActionButton;


/**
 * Created by Somnus on 2018/4/4.
 * 探索Fragment
 */

public class ExploreFragment extends Fragment implements IExploreFragment
        ,View.OnClickListener {
    private View rootView;
    private ExploreAdapter exploreAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton menu_dynamic,menu_market;

    private SlidingTabLayout slidingTabLayout;
    private IExplorePresenter iExplorePresenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iExplorePresenter = new ExplorePresenter(this);
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        initWidget(rootView);
        initClick();

        return rootView;
    }

    private void initWidget(View view){
        exploreAdapter = new ExploreAdapter(getChildFragmentManager(),getContext());
        mViewPager = (ViewPager) view.findViewById(R.id.explore_viewPager);
        slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.explore_vpi);

        mViewPager.setAdapter(exploreAdapter);
        mViewPager.setCurrentItem(0);
        slidingTabLayout.setViewPager(mViewPager);

        menu_dynamic = (FloatingActionButton) view.findViewById(R.id.menu_dynamic);
        menu_market = (FloatingActionButton) view.findViewById(R.id.menu_market);
    }

    private void initClick(){
        menu_dynamic.setOnClickListener(this);
        menu_market.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_dynamic:
                ((MainActivity) getActivity()).postTease();
                break;
            case R.id.menu_market:
                break;
        }
    }
}
