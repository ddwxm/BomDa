package com.demo.somnus.bomda.model.bean;

import java.io.Serializable;

/**
 * Created by Somnus on 2018/3/3.
 * 用户设置Bean
 */

public class AppData implements Serializable {

    private boolean provincial_traffic; // 省流量模式
    private boolean video_playback; // 视频自动播放
    private int theme_kind; // 主题皮肤
    private boolean Lux; // 自动更换日夜间模式
    private boolean new_message; // 新消息提醒
    private int prompt;
    private boolean vibration; //震动

    public boolean isProvincial_traffic() {
        return provincial_traffic;
    }

    public void setProvincial_traffic(boolean provincial_traffic) {
        this.provincial_traffic = provincial_traffic;
    }

    public boolean isVideo_playback() {
        return video_playback;
    }

    public void setVideo_playback(boolean video_playback) {
        this.video_playback = video_playback;
    }

    public int getTheme_kind() {
        return theme_kind;
    }

    public void setTheme_kind(int theme_kind) {
        this.theme_kind = theme_kind;
    }

    public boolean isLux() {
        return Lux;
    }

    public void setLux(boolean lux) {
        Lux = lux;
    }

    public boolean isNew_message() {
        return new_message;
    }

    public void setNew_message(boolean new_message) {
        this.new_message = new_message;
    }

    public int getPrompt() {
        return prompt;
    }

    public void setPrompt(int prompt) {
        this.prompt = prompt;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }
}