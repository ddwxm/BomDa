package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

/**
 * Created by Somnus on 2018/4/5.
 * 校内公告详情Presenter接口
 */

public interface INoticeDetailsPresenter {

    /**
     * 校内公告详情
     * @param context
     * @param href
     */
    void details(Context context, String href);
}
