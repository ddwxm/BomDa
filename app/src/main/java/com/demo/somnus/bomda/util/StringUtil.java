package com.demo.somnus.bomda.util;

import java.text.NumberFormat;

/**
 * Created by Somnus on 2018/4/4.
 * 字符管理工具类
 */

public class StringUtil {

    private static String [][] secret = {
            {"+","0"},{"!","1"},{"/","2"},{"#","3"},{"$","4"},
            {"%","5"},{")","6"},{"&","7"},{"*","8"},{"(","9"}};

    /**
     * 电话格式处理
     * @param phone 电话
     * @return
     */
    public static String changePhone(String phone){
        String prefix = phone.substring(0,3);
        String suffix = phone.substring(7);
        return "+86"+prefix+"****"+suffix;
    }

    /**
     * 学号格式处理
     * @param studentId 学号
     * @return
     */
    public static String changeStudentId(String studentId){
        String prefix = studentId.substring(0,4);
        String suffix = studentId.substring(8);
        return prefix+"****"+suffix;
    }

    /**
     * 密码正向加密
     * @param password 密码
     * @return
     */
    public static String encryption(String password){
        StringBuilder builder = new StringBuilder();
        String num = "";
        for (int i=0;i<password.length();i++){
            int chr = password.charAt(i);
            switch (chr){
                case 48: // 0
                    num = secret[0][0];
                    break;
                case 49: // 1
                    num = secret[1][0];
                    break;
                case 50: // 2
                    num = secret[2][0];
                    break;
                case 51: // 3
                    num = secret[3][0];
                    break;
                case 52: // 4
                    num = secret[4][0];
                    break;
                case 53: // 5
                    num = secret[5][0];
                    break;
                case 54: // 6
                    num = secret[6][0];
                    break;
                case 55: // 7
                    num = secret[7][0];
                    break;
                case 56: // 8
                    num = secret[8][0];
                    break;
                case 57: // 9
                    num = secret[9][0];
                    break;
                default:
                    num = password.substring(i,i+1);
                    break;
            }
            builder.append(num);
        }
        return builder.toString();
    }

    /**
     * 密码逆向解密
     * @param character
     * @return
     */
    public static String decryption(String character){
        StringBuilder builder = new StringBuilder();
        String num = "";
        for (int i=0;i<character.length();i++){
            int chr = character.charAt(i);
            switch (chr){
                case 43: // 0
                    num = secret[0][1];
                    break;
                case 33: // 1
                    num = secret[1][1];
                    break;
                case 47: // 2
                    num = secret[2][1];
                    break;
                case 35: // 3
                    num = secret[3][1];
                    break;
                case 36: // 4
                    num = secret[4][1];
                    break;
                case 37: // 5
                    num = secret[5][1];
                    break;
                case 41: // 6
                    num = secret[6][1];
                    break;
                case 38: // 7
                    num = secret[7][1];
                    break;
                case 42: // 8
                    num = secret[8][1];
                    break;
                case 40: // 9
                    num = secret[9][1];
                    break;
                default:
                    num = character.substring(i,i+1);
                    break;
            }
            builder.append(num);
        }
        return builder.toString();
    }

    /**
     * 计算百分数
     * @param a 除数
     * @param b 被除数
     * @return
     */
    public static String percentage(Integer a,Integer b){
        double percent = a.doubleValue() / b.doubleValue();
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(percent);
    }
}
