package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.InfoNum;

/**
 * Created by Somnus on 2018/4/4.
 * 欢迎接口
 */

public interface IWelcomeActivity {
    /**
     * 当图片获取完成时调用
     * @param pictureURL 图片的 URL 地址
     */
    void showWelcomePicture(String pictureURL);

    /**
     * 传递定位数据
     * @param latitude 纬度
     * @param longitude 经度
     * @param district 地区
     * @param city 城市
     * @param street 街道
     * @param address 地址
     */
    void sendLocationInfo(double latitude, double longitude, String district, String city, String street, String address);

    /**
     * 传递天气
     * @param temperature 温度
     * @param skycon 天气图标
     * @param server_time 请求时间
     * @param aqi 空气质量
     */
    void sendWeather(double temperature, String skycon, long server_time, Double aqi);

    /**
     * 个人信息
     * @param infoNum 个人信息
     */
    void num(InfoNum infoNum);
}
