package com.demo.somnus.bomda.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by Somnus on 2018/4/12.
 * 预览界面工具类
 */

public class PreviewPicturesViewPagerUtil extends ViewPager {
    public PreviewPicturesViewPagerUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageViewTouch) {
            return ((ImageViewTouch) v).canScroll(dx) || super.canScroll(v, checkV, dx, x, y);
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}