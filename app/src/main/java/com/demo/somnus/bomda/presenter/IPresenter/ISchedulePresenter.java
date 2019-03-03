package com.demo.somnus.bomda.presenter.IPresenter;


import com.demo.somnus.bomda.model.bean.User;

/**
 * Created by Somnus on 2018/4/4.
 * 课表Presenter接口
 */

public interface ISchedulePresenter {

    /**
     * 获取当前用户课表
     * @param user
     */
    void getSchedule(User user,String xnd,String xqd);

    /**
     * 查询课表
     * @param url 课表链接
     * @param mainHref
     * @param xnd 学年
     * @param xqd 学期
     */
    void querySchedule(String url,String mainHref,String xnd,String xqd);
}
