package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2018/2/11.
 * 课程表实体Bean
 */

public class Schedule extends BmobObject {
    private User student;
    private String year;
    private String semester;
    private String name;
    private String time;
    private String teacher;
    private String address;

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
