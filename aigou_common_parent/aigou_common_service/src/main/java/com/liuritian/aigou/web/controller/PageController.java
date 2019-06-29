package com.liuritian.aigou.web.controller;

import com.liuritian.aigou.PageClient;
import com.liuritian.aigou.util.AigouConstants;
import com.liuritian.aigou.util.VelocityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PageController implements PageClient{
    @RequestMapping(value = "/page/create",method = RequestMethod.POST)
    @Override
    //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求中的数据的)
    public void createStaticPage(@RequestBody Map<String, Object> map) {

        //数据
        Object model = map.get(AigouConstants.PAGE_MODEL);
        //模板
        String template = map.get(AigouConstants.PAGE_TEMPLATE_FILE_PATH_NAME)+"";
        //页面存放地址
        String target = map.get(AigouConstants.PAGE_TARGET_FILE_PATH_NAME)+"";
        VelocityUtils.staticByTemplate(model,template,target);

    }
}
