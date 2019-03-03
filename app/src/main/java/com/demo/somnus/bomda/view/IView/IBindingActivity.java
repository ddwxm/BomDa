package com.demo.somnus.bomda.view.IView;

/**
 * Created by Somnus on 2018/4/4.
 * 账号绑定接口
 */

public interface IBindingActivity {

    /**
     * 绑定成功
     */
    void bindingSuccess();

    /**
     * 绑定失败
     * @param s 失败原因
     * @param code 错误码
     */
    void bindingFail(String s, int code);
}
