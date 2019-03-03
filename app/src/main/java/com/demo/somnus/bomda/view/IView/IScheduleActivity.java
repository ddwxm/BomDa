package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.Course;
import com.demo.somnus.bomda.model.bean.Schedule;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 课表接口
 */

public interface IScheduleActivity {

    /**
     *  加载课表
     * @param courseList 课表列表
     */
    void loadScheduleList(List<Course> courseList);

    /**
     * 没有课程
     */
    void noClass();

    /**
     * 请求失败
     * @param msg
     * @param code
     */
    void queryFail(String msg,int code);
}
