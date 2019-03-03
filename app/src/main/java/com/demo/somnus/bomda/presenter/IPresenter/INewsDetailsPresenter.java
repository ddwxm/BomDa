package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

/**
 * Created by Somnus on 2018/4/4.
 * 新闻详情Presenter接口
 */

public interface INewsDetailsPresenter {

    /**
     * 获取新闻详情
     * @param url
     * @param context
     */
    void getDetails(String url, Context context);
}
