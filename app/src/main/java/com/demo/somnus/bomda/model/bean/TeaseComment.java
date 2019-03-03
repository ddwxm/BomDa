package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2018/2/28.
 * 树洞评论Bean
 */

public class TeaseComment extends BmobObject {

    // 树洞
    private Tease tease;
    // 评论
    private String content;
    // 作者
    private User author;

    public Tease getTease() {
        return tease;
    }

    public void setTease(Tease tease) {
        this.tease = tease;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
