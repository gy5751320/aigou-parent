package com.liuritian.aigou.web.controller;

import com.liuritian.aigou.RedisClient;
import com.liuritian.aigou.util.RedisUtil;
import org.springframework.web.bind.annotation.*;


@RestController
public class RedisController implements RedisClient{

    @RequestMapping(value = "/redis/set",method = RequestMethod.POST)
    @Override//RequestParam请求参数
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {
        //调用redis的添加方法
        RedisUtil.set(key, value);
    }
    @RequestMapping(value = "/redis/get/{key}",method = RequestMethod.GET)
    @Override//路径变量PathVariable
    public String get(@PathVariable String key) {
        //获取方法
        return RedisUtil.get(key);
    }
}
