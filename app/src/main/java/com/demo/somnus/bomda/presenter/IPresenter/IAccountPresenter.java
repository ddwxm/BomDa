package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.QQInfo;
import com.demo.somnus.bomda.model.bean.User;

/**
 * Created by Somnus on 2018/4/4.
 * 账号管理Presenter接口
 */

public interface IAccountPresenter {

    /**
     * 查询当前账号的黑名单
     * @param context
     * @param founder
     */
    void search(Context context, User founder);

    /**
     * 绑定QQ
     * @param qqInfo
     */
    void qqBinding(QQInfo qqInfo);
}
