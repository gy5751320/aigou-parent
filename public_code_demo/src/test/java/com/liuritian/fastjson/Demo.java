package com.liuritian.fastjson;

public class Demo {
    public static void main(String[] args) {
        String filePath = "group1/M00/00/01/rBAHllx6l32AHfQtAAEu5hxxfm4554.jpg";
        System.out.println(filePath.substring(1));

        // group1
        //  filePath.indexOf("/")  获取filePath字符串中第一个"/"的位置(索引) substring(int beginIndex, int endIndex))
        String substring = filePath.substring(0, filePath.indexOf("/"));
        //第一个参数 开始索引
        String substring2 = filePath.substring(4, filePath.indexOf("."));

        //   group1
        //  M00/00/01/rBAHllx6l32AHfQtAAEu5hxxfm4554.jpg
        // 从开始位置截取到最后
      String substring1 = filePath.substring(filePath.indexOf("/") + 1);
      /*  String[] split = filePath.split("\\.");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }*/
        System.out.println(substring);
//        System.out.println(substring2);
//
        System.out.println(substring1);

    }
}
