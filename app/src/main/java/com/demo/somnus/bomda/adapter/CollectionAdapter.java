package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.somnus.bomda.view.fragment.CMarketFragment;
import com.demo.somnus.bomda.view.fragment.CTeaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/11.
 * 收藏ViewPager适配器
 */

public class CollectionAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String[] tableTitle = new String[] {"树洞", "二手市场"};
    private Context mContext;
    private List<Fragment> mFragmentTab;
    private CTeaseFragment cTeaseFragment; // 树洞
    private CMarketFragment cMarketFragment; //跳骚市场

    public CollectionAdapter(FragmentManager fragmentManager, Context context) {
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

        cTeaseFragment = new CTeaseFragment();
        cMarketFragment = new CMarketFragment();

        mFragmentTab = new ArrayList<Fragment>();
        mFragmentTab.add(cTeaseFragment);
        mFragmentTab.add(cMarketFragment);

    }
}
