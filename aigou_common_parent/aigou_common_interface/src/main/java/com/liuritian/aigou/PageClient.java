package com.liuritian.aigou;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

//redis 接口  页面静态化 动态页面每次都会加载数据 消耗性能
@FeignClient(value = "COMMON-PRIVODER",fallback = PageClientFallBack.class) //表示对哪一个服务进行处理
public interface PageClient {
    /*
    * 使用map代替domain: Object data数据  Object muban模板  String path存放的地址
    * */
    @RequestMapping(value = "/page/create",method = RequestMethod.POST)
    void createStaticPage(@RequestBody Map<String,Object> map);


}
