package com.demo.somnus.bomda.presenter.IPresenter;

import com.demo.somnus.bomda.model.bean.User;

/**
 * Created by Somnus on 2018/5/7.
 * 个人信息Presenter接口
 */

public interface IPersonPresenter {

    /**
     * 查询关注与被关注
     * @param user
     */
    void getNum(User user);
}
