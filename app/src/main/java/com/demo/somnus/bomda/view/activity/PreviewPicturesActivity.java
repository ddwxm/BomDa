package com.demo.somnus.bomda.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import com.demo.somnus.bomda.adapter.PreviewPicturesVPAdapter;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.util.PictureParcelableUtil;
import com.demo.somnus.bomda.util.PreviewPicturesViewPagerUtil;
import com.demo.somnus.bomda.view.fragment.PreviewPictureFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/4/12.
 * 图片预览界面
 */

public class PreviewPicturesActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener{

    public static final String EXTRA_PICTURES = "Pictures";
    public static final String EXTRA_CURRENT_INDEX = "CurrentIndex";

    private PreviewPicturesViewPagerUtil viewPager;
    private PreviewPicturesVPAdapter adapter;
    private TextView tvCount;

    private int previousPosition = -1; //上一个 pager 的 position

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Slide()); //设置一个exit transition
        getWindow().setExitTransition(new Slide());
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        setContentView(R.layout.activity_preview_pictures);
        initWidget();
        Intent intent = getIntent();
        List<PictureParcelableUtil> pictureParcelables = intent.getParcelableArrayListExtra(EXTRA_PICTURES);
        List<BmobFile> pictures = getPictures(pictureParcelables);
        int currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0);
        adapter.setPictures(pictures);
        viewPager.setCurrentItem(currentIndex);
        previousPosition = currentIndex;
        tvCount.setText((currentIndex+1)+"/"+pictures.size());
    }

    private void initWidget() {
        tvCount = (TextView) findViewById(R.id.tv_count);
        viewPager = (PreviewPicturesViewPagerUtil) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        adapter = new PreviewPicturesVPAdapter(getSupportFragmentManager(), null);
        viewPager.setAdapter(adapter);
    }

    private List<BmobFile> getPictures(List<PictureParcelableUtil> pictureParcelables) {
        List<BmobFile> pictures = new ArrayList<>();
        for (PictureParcelableUtil pictureParcelable : pictureParcelables) {
            pictures.add(pictureParcelable.getPicture());
        }
        return pictures;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tvCount.setText((position+1)+"/"+adapter.getCount());
        if (previousPosition != -1 && previousPosition != position) {
            Object object = adapter.instantiateItem(viewPager, previousPosition);
            ((PreviewPictureFragment) object).resetView();
        }
        previousPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
