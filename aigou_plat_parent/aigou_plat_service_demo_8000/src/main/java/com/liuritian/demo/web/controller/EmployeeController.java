package com.liuritian.demo.web.controller;

import com.liuritian.aigou.Employee;
import com.liuritian.aigou.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController//和controller注解区别 rest:controller&responseBody 组合
public class EmployeeController {
    //RequestBody注解 接收请求参数 post
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        System.out.println(employee.getUsernmae()+"密码====="+employee.getPassword());
        //判断登录用户石峰为null  "cnm".equals(employee.getUsernmae()) &&
        if ( "cnm".equals(employee.getUsernmae()) &&"cx1320".equals(employee.getPassword())){
            //成功
            return AjaxResult.me().setMsg("苍老师来看你了").setObject(null);
        }else {
            //失败
            return AjaxResult.me().setSuccess(false).setMsg("你太丑了");
        }
    }

    //get方式
    @RequestMapping(value = "/login2",method = RequestMethod.GET)
    public AjaxResult login2(){
        return AjaxResult.me();
    }
}
