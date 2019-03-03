package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/1/20.
 * 失物招领图片实体Bean
 */

public class HomingPicture extends BmobObject {
    private Homing homing;
    private BmobFile picture;

    public Homing getHoming() {
        return homing;
    }

    public void setHoming(Homing homing) {
        this.homing = homing;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }
}
