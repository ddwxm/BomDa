package com.demo.somnus.bomda.view.IView;

/**
 * Created by Somnus on 2018/4/4.
 * 注册接口
 */

public interface IRegisterActivity {

    /**
     * 短信验证码发送成功
     */
    void sendSMSSuccess();

    /**
     * 短信验证码发送失败
     * @param s 失败原因
     * @param i 错误码
     */
    void sendSMSSFail(String s, int i);

    /**
     * 注册成功
     */
    void registerSuccess();

    /**
     * 注册失败
     * @param s 失败原因
     * @param i 错误码
     */
    void registerFail(String s, int i);
}
