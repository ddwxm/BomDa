package com.demo.somnus.bomda.model.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Somnus on 2018/2/27.
 * 树洞数据实体Bean
 */

public class Tease extends BmobObject {

    // 作者
    private User author;
    // 内容
    private String content;
    // 发表地点
    private String address;
    // 图片
    private List<BmobFile> pictures;
    // 是否精选
    private Boolean isSift;
    // 赞数量
    private BmobRelation praise;
    // 收藏数量
    private BmobRelation collection;
    // 类型
    private String type;

    private Tease() {}

    public static Tease newTease() {
        Tease tease = new Tease();
        tease.content = "";
        tease.pictures = new ArrayList<>();
        tease.type = "正常";
        tease.isSift = false;
        return tease;
    }

    public static Tease nullTease() {
        return new Tease();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public List<BmobFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<BmobFile> pictures) {
        this.pictures = pictures;
    }

    public Boolean getSift() {
        return isSift;
    }

    public void setSift(Boolean sift) {
        isSift = sift;
    }

    public BmobRelation getPraise() {
        return praise;
    }

    public void setPraise(BmobRelation praise) {
        this.praise = praise;
    }

    public BmobRelation getCollection() {
        return collection;
    }

    public void setCollection(BmobRelation collection) {
        this.collection = collection;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
