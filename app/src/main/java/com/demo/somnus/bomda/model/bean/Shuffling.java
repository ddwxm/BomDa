package com.demo.somnus.bomda.model.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/1/20.
 * 首页轮播图实体Bean
 */

public class Shuffling extends BmobObject {

    private BmobFile image;

    private User release;

    private String onClick;

    private String title;


    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public User getRelease() {
        return release;
    }

    public void setRelease(User release) {
        this.release = release;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
