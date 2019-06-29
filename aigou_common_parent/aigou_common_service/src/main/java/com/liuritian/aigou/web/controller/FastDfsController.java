package com.liuritian.aigou.web.controller;


import com.liuritian.aigou.FastDfsClient;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.FastDfsUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FastDfsController implements FastDfsClient {

    @RequestMapping(value ="/fastdfs/upload",method = RequestMethod.POST)
    @Override
    public AjaxResult upload(@RequestBody MultipartFile file) {
        //上传成功
        try {
            //获取文件名字
            String originalFilename = file.getOriginalFilename();
            System.out.println("originalFilename==========="+originalFilename);
            String[] split = originalFilename.split("\\.");
            String exName = "";//赋值文件扩张名
            //判断split长度是否大于0 就上传
            if(split.length > 0){
                //将后缀名赋给exName
                exName = split[split.length-1];
                System.out.println("获取到的后缀名-----------"+exName);
                //调用工具类 上传方法
                String upload = FastDfsUtil.upload(file.getBytes(), exName);
                System.out.println("============"+upload);
                //成功给个提醒
               return  AjaxResult.me().setSuccess(true).setMsg("上传成功!").setObject(upload);

            }else{
                return  AjaxResult.me().setSuccess(false).setMsg("上传失败........");

            }
        } catch (Exception e) {
            //上传失败
            e.printStackTrace();
            return  AjaxResult.me().setSuccess(false).setMsg("上传失败........"+e.getMessage());
        }
    }
    //删除
    @RequestMapping(value = "/fastdfs/delete",method = RequestMethod.DELETE)
    @Override
    public AjaxResult delete(@RequestParam("filePath") String filePath) {
        try {
            String str = filePath.substring(1);
            //获取group1
            String groupName = str.substring(0, str.indexOf("/"));
            //获取fileName 不要开始的/ +1
            String fileName = str.substring(str.indexOf("/") + 1);
            //调用工具类删除方法
            FastDfsUtil.delete(groupName, fileName);
            //删除成功给个提醒
            return AjaxResult.me().setMsg("恭喜删除成功....");
        } catch (Exception e) {
            e.printStackTrace();
            //删除失败
            return AjaxResult.me().setSuccess(false).setMsg("哈哈哈网络异常....."+e.getMessage());
        }
    }

}
