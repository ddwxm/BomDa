package com.demo.somnus.bomda.util;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Somnus on 2018/4/4.
 * 随机管理工具类
 */

public class RandomUtil {
    public RandomUtil(){}

    public static String Random(){
        String nick = "";
        HashSet integerHashSet=new HashSet();
        Random random=new Random();
        for (int i = 0;; i++) {
            int randomInt = random.nextInt(10);
            System.out.println("生成的randomInt="+randomInt);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                nick +=randomInt;
                System.out.println("添加进HashSet的randomInt="+randomInt+"nick:"+nick);
                if (nick.length() == 6){
                    break;
                }
            }else {
                System.out.println("该数字已经被添加,不能重复添加");
            }
        }
        System.out.println("/////以上为testRandom3()的测试///////");
        return nick;
    }
    public static int RandomInt(){
        int randomInt = 0;
        Random random=new Random();
        randomInt = random.nextInt(16);
        return randomInt;
    }

    public static int[][] twoDArray(int rows,int columns){
        int[][] twoDArray = new int[rows][columns];
        HashSet integerHashSet=new HashSet();
        Random random=new Random();
        for (int j = 0;j<columns;j++){
            for (int i = 0;i<rows; i++) {
                int randomInt = random.nextInt(10);
                System.out.println("生成的randomInt="+randomInt);
                if (!integerHashSet.contains(randomInt)) {
                    integerHashSet.add(randomInt);
                    twoDArray[i][j] = randomInt;
                    System.out.println("添加进HashSet的randomInt="+randomInt);
                }else {
                    System.out.println("该数字已经被添加,不能重复添加");
                }
            }
        }
        return twoDArray;
    }
}
