package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.somnus.bomda.view.fragment.MarketFragment;
import com.demo.somnus.bomda.view.fragment.SiftFragment;
import com.demo.somnus.bomda.view.fragment.TeaseFragment;
import com.demo.somnus.bomda.view.fragment.TopicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 探索ViewPager适配器
 */

public class ExploreAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private String[] tableTitle = new String[] {"精选", "树洞", "跳骚市场","专题"};
    private Context mContext;
    private List<Fragment> mFragmentTab;
    private SiftFragment siftFragment; // 精选
    private TeaseFragment teaseFragment; // 树洞
    private MarketFragment marketFragment; //跳骚市场
    private TopicFragment topicFragment; // 专题

    public ExploreAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
        initFragmentTab();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }

    private void initFragmentTab() {

        siftFragment = new SiftFragment();
        teaseFragment = new TeaseFragment();
        marketFragment = new MarketFragment();
        topicFragment = new TopicFragment();

        mFragmentTab = new ArrayList<Fragment>();
        mFragmentTab.add(siftFragment);
        mFragmentTab.add(teaseFragment);
        mFragmentTab.add(marketFragment);
        mFragmentTab.add(topicFragment);

    }
}

