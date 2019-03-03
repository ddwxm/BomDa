package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2018/1/17.
 * 黑名单实体Bean
 */

public class Blacklist extends BmobObject {

    private User founder;
    private User object;

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public User getObject() {
        return object;
    }

    public void setObject(User object) {
        this.object = object;
    }
}
