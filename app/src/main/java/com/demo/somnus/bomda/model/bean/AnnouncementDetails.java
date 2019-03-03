package com.demo.somnus.bomda.model.bean;

import java.util.List;

/**
 * Created by Somnus on 2018/1/23.
 * 公告详情实体Bean
 */

public class AnnouncementDetails {
    private String title;
    private List<String > content;
    private String time;
    private String source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
