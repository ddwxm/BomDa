package com.demo.somnus.bomda.model.bean;

import java.util.List;

/**
 * Created by Somnus on 2018/2/22.
 * 新闻详情实体Bean
 */

public class NewsDetails {

    private String title;
    private List<String > content;
    private String time;
    private String source;
    private String graphics[][];
    private String tit[];
    private String url[];

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

    public String[][] getGraphics() {
        return graphics;
    }

    public void setGraphics(String[][] graphics) {
        this.graphics = graphics;
    }

    public String[] getTit() {
        return tit;
    }

    public void setTit(String[] tit) {
        this.tit = tit;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
