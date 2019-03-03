package com.demo.somnus.bomda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.demo.somnus.bomda.view.fragment.PreviewPictureFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/4/12.
 * 图片ViewPager适配器
 */

public class PreviewPicturesVPAdapter extends FragmentPagerAdapter {

    private List<BmobFile> pictures;
    private OnPrimaryItemSetListener mListener;

    public PreviewPicturesVPAdapter(FragmentManager manager, OnPrimaryItemSetListener listener) {
        super(manager);
        pictures = new ArrayList<>();
        mListener = listener;
    }

    public void setPictures(List<BmobFile> pictures) {
        this.pictures = new ArrayList<>(pictures);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return PreviewPictureFragment.newInstance(pictures.get (position));
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (mListener != null) {
            mListener.onPrimaryItemSet(position);
        }
    }

    public BmobFile getPicture(int position) {
        return pictures.get(position);
    }


    interface OnPrimaryItemSetListener {

        void onPrimaryItemSet(int position);
    }

}

