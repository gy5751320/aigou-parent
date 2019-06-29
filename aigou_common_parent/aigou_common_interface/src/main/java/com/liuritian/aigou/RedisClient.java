package com.liuritian.aigou;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//redis 接口 缓存 解决分类数据多 数据库压力大问题
@FeignClient(value = "COMMON-PRIVODER",fallback = RedisClientFallBack.class) //表示对哪一个服务进行处理
public interface RedisClient {
    //@RequestBody:只能用一个,一般用在post请求 @RequestParam能用多个
    @RequestMapping(value = "/redis/set",method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "/redis/get/{key}",method = RequestMethod.GET)
    String get(@PathVariable("key") String key);
}
