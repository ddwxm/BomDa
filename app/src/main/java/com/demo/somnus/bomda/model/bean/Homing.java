package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/1/20.
 * 失物招领实体Bean
 */

public class Homing extends BmobObject {
    // 寻物或者招领
    private String type;
    // 发布人
    private User release;
    // 内容
    private String content;
    // 地点
    private String address;
    // 相关图
    private BmobFile picture;
    // title
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getRelease() {
        return release;
    }

    public void setRelease(User release) {
        this.release = release;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
