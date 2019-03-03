package com.demo.somnus.bomda.util;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.Course;
import com.demo.somnus.bomda.model.bean.Schedule;


/**
 * Created by Somnus on 2018/4/4.
 * 课程管理工具类
 */

public class ScheduleUtil {

    private static final int[][] location = {
            {1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},
            {2,1},{2,2},{2,3},{2,4},{2,5},{2,6},{2,7},
            {3,1},{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},
            {4,1},{4,2},{4,3},{4,4},{4,5},{4,6},{4,7},
            {5,1},{5,2},{5,3},{5,4},{5,5},{5,6},{5,7},
            {6,1},{6,2},{6,3},{6,4},{6,5},{6,6},{6,7}};

    private ScheduleUtil(Context context){
        super();
    }

    public static int[][] getLocation(Course course){
        String day = course.getDay();
        String time = course.getTime();

        return location;
    }

    public static int changeDay(String day){
        int what = 0;
        switch (day){
            case "周一":
                what = 1;
                break;
            case "周二":
                what = 2;
                break;
            case "周三":
                what = 3;
                break;
            case "周四":
                what =4;
                break;
            case "周五":
                what = 5;
                break;
            case "周六":
                what = 6;
                break;
            case "周日":
                what = 7;
                break;
        }
        return what;
    }

    public static int changeTime(String time){
        int section = 0;
        switch (time){
            case "1,2":
                section = 1;
                break;
            case "3,4":
                section = 2;
                break;
            case "5,6":
                section = 3;
                break;
            case "7,8":
                section = 4;
                break;
            case "9,10":
                section = 5;
                break;
            case "9,10,11":
                section = 6;
                break;
        }
        return section;
    }
}
