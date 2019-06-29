package com.liuritian.aigou;

import com.liuritian.aigou.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

//redis 接口  页面静态化 动态页面每次都会加载数据 消耗性能
@FeignClient(value = "COMMON-PRIVODER",fallback = FastDfsClientFallBack.class) //表示对哪一个服务进行处理
public interface FastDfsClient {
    //上传文件
    @RequestMapping(value ="/fastdfs/upload",method = RequestMethod.POST)
    AjaxResult upload(@RequestBody MultipartFile file);

    //文件删除 路径
    @RequestMapping(value ="/fastdfs/delete",method = RequestMethod.DELETE)
    AjaxResult delete(@RequestParam("filePath") String filePath);
}
