package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/2/28.
 * 二手市场Bean
 */

public class Market extends BmobObject {
    private String label; //标签
    private String description; //介绍
    private String price; //价格
    private boolean complete; //是否已经完成交易，true 为是
    private String name; // 物品名
    private String link_qq; //联系方式，QQ
    private String link_tel; //联系方式，电话
    private User publisher; //发布人
    private BmobFile pic1; //图片
    private BmobFile pic2; //图片
    private BmobFile pic3; //图片
    private boolean knife; //是否可面议价格，true为可以面议价格
    private boolean type; //  true: 出售   false:求购
    private String kind; //种类  学习相关  生活用品  其他

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_qq() {
        return link_qq;
    }

    public void setLink_qq(String link_qq) {
        this.link_qq = link_qq;
    }

    public String getLink_tel() {
        return link_tel;
    }

    public void setLink_tel(String link_tel) {
        this.link_tel = link_tel;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public BmobFile getPic1() {
        return pic1;
    }

    public void setPic1(BmobFile pic1) {
        this.pic1 = pic1;
    }

    public BmobFile getPic2() {
        return pic2;
    }

    public void setPic2(BmobFile pic2) {
        this.pic2 = pic2;
    }

    public BmobFile getPic3() {
        return pic3;
    }

    public void setPic3(BmobFile pic3) {
        this.pic3 = pic3;
    }

    public boolean isKnife() {
        return knife;
    }

    public void setKnife(boolean knife) {
        this.knife = knife;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
