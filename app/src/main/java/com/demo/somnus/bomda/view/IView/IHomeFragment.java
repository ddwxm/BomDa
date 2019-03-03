package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.Activities;
import com.demo.somnus.bomda.model.bean.Homing;
import com.demo.somnus.bomda.model.bean.Shuffling;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 首页接口
 */

public interface IHomeFragment {
    /**
     * 轮播图片
     * @param shufflingList 图片列表
     */
    void onLoadShufflingListSuccess(List<Shuffling> shufflingList);

    /**
     * 活动
     * @param activitiesList 活动列表
     */
    void onLoadActivityListSuccess(List<Activities> activitiesList);

    /**
     * 失物招领
     * @param homingList 失物招领
     */
    void onLoadHomingListSuccess(List<Homing> homingList);

    /**
     * 传递学生信息
     * @param studentNum 学号
     * @param password 密码
     * @param type 类别
     */
    void sendInfo(String studentNum, String password, int type);

    /**
     * 没有绑定
     */
    void notBinding();
}
