package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Somnus on 2017/12/30.
 * 用户实体Bean
 */

public class User extends BmobUser {

    private String studentId;
    private String studentPassword;
    private String studentName;
    private String nick;
    private String sex;
    private String birth;
    private BmobFile avatar;
    private BmobFile background;
    private int praise;
    // 签名
    private String signature;
    private Boolean isBinding;
    // 个人介绍
    private String introduction;
    private String address;

    // qq登录
    private Boolean qqLogin;
    private String qqavatar;
    private String qqId;

    // qq绑定
    private Boolean qqBinding;

    // 附近的人
    private BmobGeoPoint nearby;

    /**
     * 这个方法返回一个空的 User 对象
     * 仅用于更新用户信息
     */
    public static User nullUser() {
        return new User();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobFile getBackground() {
        return background;
    }

    public void setBackground(BmobFile background) {
        this.background = background;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public Boolean getBinding() {
        return isBinding;
    }

    public void setBinding(Boolean binding) {
        isBinding = binding;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public Boolean getQqLogin() {
        return qqLogin;
    }

    public void setQqLogin(Boolean qqLogin) {
        this.qqLogin = qqLogin;
    }

    public String getQqavatar() {
        return qqavatar;
    }

    public void setQqavatar(String qqavatar) {
        this.qqavatar = qqavatar;
    }

    public Boolean getQqBinding() {
        return qqBinding;
    }

    public void setQqBinding(Boolean qqBinding) {
        this.qqBinding = qqBinding;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }
}
