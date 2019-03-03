package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

/**
 * Created by Somnus on 2018/4/4.
 * 注册Presenter接口
 */

public interface IRegisterPresenter {

    /**
     * 请求短信验证码
     * @param context
     * @param phone 手机号码
     */
    void sendSMS(Context context, String phone);

    /**
     * 注册
     * @param username 用户名(手机号码)
     * @param password 密码
     * @param code 验证码
     */
    void register(String username, String password, String code);
}
