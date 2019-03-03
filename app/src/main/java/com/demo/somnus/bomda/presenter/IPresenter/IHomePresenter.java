package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.User;


/**
 * Created by Somnus on 2018/4/4.
 * 首页Presenter接口
 */

public interface IHomePresenter {

    /**
     * 轮播图查询
     * @param context
     */
    void search_shuffling(Context context);

    /**
     * 活动查询
     * @param context
     */
    void search_activity(Context context);

    /**
     * 失物招领查询
     * @param context
     */
    void search_homing(Context context);

    /**
     * 是否绑定
     * @param context
     * @param user
     * @param code
     */
    void isBinding(Context context, User user, int code);
}
