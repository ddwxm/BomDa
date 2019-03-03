package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.QQInfo;

/**
 * Created by Somnus on 2018/4/4.
 * 登录Presenter接口
 */

public interface ILoginPresenter {

    /**
     * 密码登录
     * @param context 上下文
     * @param phone 手机号码
     * @param password 密码
     */
    void loginPassword(Context context, String phone, String password);

    /**
     * 验证码登录
     * @param context 上下文
     * @param phone 手机号码
     * @param code 验证码
     */
    void loginCode(Context context, String phone, String code);

    /**
     * 请求验证码
     * @param context 上下文
     * @param phone 手机号码
     */
    void sendSMSCode(Context context, String phone,String type);

    /**
     * 验证手机号码
     * @param context 上下文
     * @param phone 手机号码
     * @param code 验证码
     * @param qqInfo qq信息
     */
    void validation(Context context,String phone,String code, QQInfo qqInfo);

    /**
     * 查询是否登录过
     * @param qqInfo qq信息
     */
    void query(QQInfo qqInfo);
}
