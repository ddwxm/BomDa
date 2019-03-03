package com.demo.somnus.bomda.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Somnus on 2018/4/7.
 * 成绩Bean
 */

public class Grade extends BmobObject{
    // 用户
    private User user;
    // 学年
    private String year;
    // 学期
    private String semester;
    // 课程代码
    private String code;
    // 课程名称
    private String name;
    // 课程性质
    private String type;
    // 学分
    private String credits;
    // 绩点
    private String gpa;
    // 成绩
    private String grade;
    // 辅修标记
    private String minor;
    // 重修标记
    private String reset;
    // 重修成绩
    private String resetGrade;
    // 开课学院
    private String college;
    // 补考成绩
    private String examination;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getResetGrade() {
        return resetGrade;
    }

    public void setResetGrade(String resetGrade) {
        this.resetGrade = resetGrade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }
}
