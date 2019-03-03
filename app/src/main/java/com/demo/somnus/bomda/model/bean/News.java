package com.demo.somnus.bomda.model.bean;

/**
 * Created by Somnus on 2018/1/22.
 * 新闻实体Bean
 */

public class News {
    private String title;
    private String date;
    private String link;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
