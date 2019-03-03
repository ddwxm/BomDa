package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2018/3/21.
 * 关注Bean
 */

public class Focus extends BmobObject {
    // 关注人
    private User focuser;
    // 被关注人
    private User user;

    public User getFocuser() {
        return focuser;
    }

    public void setFocuser(User focuser) {
        this.focuser = focuser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
