package com.demo.somnus.bomda.presenter.IPresenter;

import com.demo.somnus.bomda.model.bean.User;

/**
 * Created by Somnus on 2018/4/4.
 * 我的Presenter接口
 */

public interface IMinePresenter {

    /**
     * 获取动态数
     * @param user 用户
     */
    void getNum(User user);
}
