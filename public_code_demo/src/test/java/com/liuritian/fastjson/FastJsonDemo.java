package com.liuritian.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class FastJsonDemo {
    public static void main(String[] args) {
//        对象转json字符串
        Employee employee = new Employee(69L, "刘日天同志");
        String s = JSON.toJSONString(employee);
        System.out.println("对象转json字符串:====="+s);
        //json转对象
        Employee employee1 = JSON.parseObject(s, Employee.class);
        System.out.println("json转对象:=========="+employee1);
        //对象的list和json字符串的互转
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(86L,"波多野吉衣"));
        list.add(new Employee(88L,"武藤兰"));
        String s1 = JSONArray.toJSONString(list);
        System.out.println("对象list转json：==="+s1);
        List<Employee> list1 = JSON.parseArray(s1, Employee.class);
        list1.forEach(e -> System.out.println("json字符串转对象list==========="+e));
    }

}
