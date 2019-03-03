package com.demo.somnus.bomda.view.IView;

/**
 * Created by Somnus on 2018/4/4.
 * 登录接口
 */

public interface ILoginActivity {

    /**
     * 密码登录成功
     */
    void loginPasswordSuccess();

    /**
     * 验证码登录成功
     */
    void loginCodeSuccess();

    /**
     * 登录失败
     * @param s 失败原因
     * @param code 错误码
     */
    void loginFail(String s, int code);

    /**
     * 短信验证码发送成功
     */
    void sendSMSSuccess(String type);

    /**
     * 短信验证码发送失败
     * @param s 失败原因
     * @param i 错误码
     */
    void sendSMSSFail(String s, int i);

    /**
     * 没有注册过
     */
    void notLogin();

    /**
     * 注册过
     */
    void isLogin();

}
