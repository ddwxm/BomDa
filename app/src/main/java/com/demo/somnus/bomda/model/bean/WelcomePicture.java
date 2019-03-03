package com.demo.somnus.bomda.model.bean;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/4/4.
 * 欢迎图片Bean
 */

public class WelcomePicture extends BmobObject {

    // 图片
    private BmobFile picture;

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getPictureURL(Context context) {
        return picture.getFileUrl();
    }
}
