package com.demo.somnus.bomda.presenter.IPresenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.User;

/**
 * Created by Somnus on 2018/4/4.
 * 成绩Presenter接口
 */

public interface IGradePresenter {

    /**
     *
     * 第一次尝试获取
     * @param type
     * @param year
     * @param semester
     */
    void tryGet(String url,String mainHref,String type, String year, String semester);

    /**
     *
     * 获取成绩
     * @param url 网址
     * @param studentNum 学号
     * @param password 密码
     * @param type 类别 (历年成绩学年学期为空)
     * @param year 学年
     * @param semester 学期
     */
    void getGrade(String url,String studentNum, String password, String code,String type, String year, String semester);

    /**
     * 获取用户缓存成绩
     * @param user
     * @param type
     * @param year
     * @param semester
     */
    void tryDatabase(User user,String type,String year,String semester,Context context);
}
