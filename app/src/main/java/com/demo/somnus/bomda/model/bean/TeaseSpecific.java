package com.demo.somnus.bomda.model.bean;

import java.util.List;

/**
 * Created by Somnus on 2018/2/27.
 * 树洞具体类
 */

public class TeaseSpecific {
    // 赞数量
    private Integer praise;
    // 收藏数量
    private Integer collection;
    // 评论数量
    private Integer comment;
    // 评论
    private List<TeaseComment> teaseComment;

    private Tease tease;

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public List<TeaseComment> getTeaseComment() {
        return teaseComment;
    }

    public void setTeaseComment(List<TeaseComment> teaseComment) {
        this.teaseComment = teaseComment;
    }

    public Tease getTease() {
        return tease;
    }

    public void setTease(Tease tease) {
        this.tease = tease;
    }
}
