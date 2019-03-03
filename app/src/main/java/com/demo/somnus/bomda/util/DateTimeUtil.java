package com.demo.somnus.bomda.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Somnus on 2018/4/4.
 * 时间管理工具类
 */

public final class DateTimeUtil {
    private static SimpleDateFormat simpleDateFormat;
    public final long ONE_DAY_MIL = 1000 * 60 * 60 * 24;
    private static String TEAR_MONTH_DAY = "yyyy-MM-dd";
    private static String TIME_DAY_HOUR = "MM-dd HH:mm:ss";
    private static String TIME = "yyyy-MM-dd HH:mm:ss";
    private static String TIME_KK = "yyyy-MM-dd KK:mm:ss";
    private static String TIME_DAY = "dd";
    private static String TIME_HOUR = "HH";
    private static String TIME_MIN = "mm";
    private static String TIME_SEC = "ss";
    private static String TIME_YEAR = "yyyy";
    private static String TIME_MONTH = "MM";
    private static final int Day = 0;
    private static final int Hour = 1;
    private static final int Min = 2;
    private static final int Sec = 3;
    private static final int Year = 4;
    private static final int Month = 5;
    private static String hour = "小时前";
    private static String min = "分钟前";
    private static String sec = "秒前";
    private static int input_Day;
    private static String outPut;


    private DateTimeUtil() {}

    public static String compare(String date){
        if (isSameDay(date)){
            outPut = Time_Difference(date);
        } else {
            outPut = Time_Change(date);
        }
        return outPut;
    }

    /**
     * 获取当前时间 天数/小时数/分数/秒数
     * @param time_style
     *   Day -- TIME_DAY  天数
     *   Hour -- TIME_HOUR  小时数
     *   Min -- TIME_MIN  分数
     *   Sec -- TIME_SEC  秒数
     * @return Integer.parseInt(simpleDateFormat.format(curDate))
     */
    private static int Current_Time(int time_style){
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        switch (time_style){
            case Day:
                simpleDateFormat= new SimpleDateFormat(TIME_DAY);
                break;
            case Hour:
                simpleDateFormat = new SimpleDateFormat(TIME_HOUR);
                break;
            case Min:
                simpleDateFormat = new SimpleDateFormat(TIME_MIN);
                break;
            case Sec:
                simpleDateFormat = new SimpleDateFormat(TIME_SEC);
                break;
        }
        return Integer.parseInt(simpleDateFormat.format(curDate));
    }

    /**
     * 转化时间格式 同年份去除年份
     * @param date
     * @return input_Day
     */
    private static String Time_Change(String date){
        DateFormat formatter=new SimpleDateFormat(TIME);
        Date date1 = null;
        try {
            date1 = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpleDateFormat= new SimpleDateFormat(TIME_DAY_HOUR);
        return simpleDateFormat.format(date1);
    }

    /**
     * 获取输入时间 天数/小时数/分数/秒数
     * @param time_style
     *   Day -- TIME_DAY  天数
     *   Hour -- TIME_HOUR  小时数
     *   Min -- TIME_MIN  分数
     *   Sec -- TIME_SEC  秒数
     * @return input_Day
     */
    public static int Input_Time(String date, int time_style){
        DateFormat formatter=new SimpleDateFormat(TIME_KK);
        Date date1=null;
        try {
            date1=formatter.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTime(date1);
        switch (time_style){
            case Day:
                input_Day = calendar.get(Calendar.DAY_OF_MONTH);
                break;
            case Hour:
                input_Day = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case Min:
                input_Day = calendar.get(Calendar.MINUTE);
                break;
            case Sec:
                input_Day = calendar.get(Calendar.SECOND);
                break;
            case Year:
                input_Day = calendar.get(Calendar.YEAR);
                break;
            case Month:
                input_Day = calendar.get(Calendar.MONTH);
                break;
        }
        return input_Day;
    }

    /**
     * 获取规范格式
     * @param time
     * @return
     */
    public static int getBirth(String time,int type){
        int num = 0;
        switch (type){
            case Day:
                num = Integer.parseInt(time.substring(0,4));
                break;
            case Year:
                num = Integer.parseInt(time.substring(5,7));
                break;
            case Month:
                num = Integer.parseInt(time.substring(8));
                break;
        }
        return num;
    }

    public static int formatting(String time,int type){
        int num = 0;
        DateFormat formatter=new SimpleDateFormat(TEAR_MONTH_DAY);
        Date date1=null;
        try {
            date1=formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTime(date1);
        switch (type){
            case Day:
                input_Day = calendar.get(Calendar.DAY_OF_MONTH);
                break;
            case Hour:
                input_Day = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case Min:
                input_Day = calendar.get(Calendar.MINUTE);
                break;
            case Sec:
                input_Day = calendar.get(Calendar.SECOND);
                break;
            case Year:
                input_Day = calendar.get(Calendar.YEAR);
                break;
            case Month:
                input_Day = calendar.get(Calendar.MONTH);
                break;
        }
        return input_Day;
    }

    /**
     * 判断两个时间是否同一天
     * @param date
     * @return
     */
    private static boolean isSameDay(String date){
        return Input_Time(date,Day) == Current_Time(Day);
    }

    /**
     * 当同一天下判断时间差
     * @param date
     * @return
     */
    private static String Time_Difference(String date){
        // 判断当前时间与输入时间小时数是否相同
        if(Input_Time(date,Hour) == Current_Time(Hour)){
            // 小时数相同  判断当前时间与输入时间分数是否相同
            if (Input_Time(date,Min) == Current_Time(Min)){
                // 相同则输出秒数差
                outPut = Current_Time(Sec) - Input_Time(date,Sec)+sec;
            } else { // 不同则输出分数差
                outPut = Current_Time(Min) - Input_Time(date,Min)+min;
            }
        } else { // 不同则输出小时差 超过12小时显示日期
            if ((Current_Time(Hour) - Input_Time(date,Hour))>12){
                outPut = Time_Change(date);
            } else {
                outPut = Current_Time(Hour) - Input_Time(date,Hour)+hour;
            }
        }
        return outPut;
    }

}