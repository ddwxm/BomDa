package com.demo.somnus.bomda.listener;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Created by Somnus on 2018/4/12.
 * 输入框输入监听
 */

public class EditTextScrollOnScrollView implements View.OnTouchListener {

    private EditText editText;
    private ViewGroup scrollView;

    public EditTextScrollOnScrollView(@NonNull EditText editText, @NonNull ViewGroup scrollView) {
        boolean a = scrollView instanceof ScrollView;
        Boolean b = scrollView instanceof NestedScrollView;
        if (a || b) {
            this.editText = editText;
            this.scrollView = scrollView;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (scrollView == null) {
            return false;
        }
        if (view.equals(editText)) {
            if (editText.canScrollVertically(0) || editText.canScrollVertically(-1)) {
                //如果可以上下滚动，则请求拦截
                scrollView.requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //抬起手指时还原
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }
            }
        }
        return false;
    }
}

