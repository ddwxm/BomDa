package com.demo.somnus.bomda.view.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.somnus.bomda.BomDaApplication;
import com.demo.somnus.bomda.R;

/**
 * Created by Somnus on 2018/4/4.
 * 查看图片Activity
 */

public class ViewImageActivity extends AppCompatActivity {

    private CoordinatorLayout clRootView;
    private ImageView imvContent;
    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        BomDaApplication.toastShow(this, "正在查看大图, 下滑返回", Toast.LENGTH_SHORT);

        clRootView = (CoordinatorLayout) findViewById(R.id.cl);
        imvContent = (ImageView) findViewById(R.id.imv_content);
        behavior = BottomSheetBehavior.from(imvContent);
        behavior.setBottomSheetCallback(callback);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");
        String background = intent.getStringExtra("type");
        boolean isQQ = intent.getBooleanExtra("isQQ",false);
        if (background.equals("background")){
            if (isQQ){
                Glide.with(getApplicationContext())
                        .load(R.mipmap.mine_blue_bg)
                        .error(R.drawable.ic_image_problem_white_88dp)
                        .into(imvContent);
            } else {
                Glide.with(getApplicationContext())
                        .load(imagePath)
                        .error(R.drawable.ic_image_problem_white_88dp)
                        .into(imvContent);
            }
        } else {
            Glide.with(getApplicationContext())
                    .load(imagePath)
                    .error(R.drawable.ic_image_problem_white_88dp)
                    .into(imvContent);
        }
        animationShowBottomSheet();
    }

    @Override
    public void finish() {
        animationHideBottomSheet();
    }

    /**
     * 动画展开 bottom sheet
     */
    private void animationShowBottomSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                animChangeBgAlpha(true, animation.getDuration());
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imvContent.startAnimation(animation);
    }

    /**
     * 动画关闭 bottom sheet
     */
    private void animationHideBottomSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animChangeBgAlpha(false, animation.getDuration());
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imvContent.setVisibility(View.GONE);
                ViewImageActivity.super.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imvContent.startAnimation(animation);
    }

    /**
     * 动画改变窗口背景透明度
     * @param open true 代表需要渐变至全不透明，false 代表需要渐变至全透明
     */
    private void animChangeBgAlpha(boolean open, long duration) {
        ValueAnimator valueAnimator;
        if (open) {
            valueAnimator = ValueAnimator.ofInt(0, 255).setDuration(duration);
        } else {
            valueAnimator = ValueAnimator.ofInt(255, 0).setDuration(duration);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackgroundAlpha((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置界面顶层 layout 的背景透明度
     * @param alpha 透明度 0 ~ 255
     */
    private void setBackgroundAlpha(int alpha) {
        clRootView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }


    /**
     * 生成一个 bottom sheet callback
     */
    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {}

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            setBackgroundAlpha(Math.round(255 * slideOffset));
            if (slideOffset == 0f) {
                ViewImageActivity.super.finish();
            }
        }
    };
}
