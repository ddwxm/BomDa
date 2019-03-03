package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.User;


/**
 * Created by Somnus on 2018/4/4.
 * 欢迎Presenter接口
 */

public interface IWelcomePresenter {

    /**
     * 请求欢迎图片
     * @param context
     */
    void showWelcomePicture(Context context);

    /**
     * 获取天气
     * @param context
     */
    void getWeather(Context context);

    interface GetPictureURLListener {
        /**
         * 当完成时
         * @param success true 表示成功，false 表示失败。
         * @param str 表示 URL 或者错误信息。
         */
        void onDone(boolean success, String str);
    }

    /**
     * 获取动态数
     * @param user 用户
     */
    void getNum(User user);
}
