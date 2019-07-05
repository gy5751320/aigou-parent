package cn.itsource.aigou.web.controller;


import cn.itsource.aigou.service.IProductExtService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liuritian.aigou.domain.ProductExt;
import com.liuritian.aigou.domain.Specification;
import com.liuritian.aigou.query.ProductExtQuery;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productExt")
public class ProductExtController {
    @Autowired
    public IProductExtService productExtService;

    /**
    * 保存和修改公用的
    * @param productExt  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductExt productExt){
        try {
            if(productExt.getId()!=null){
                productExtService.updateById(productExt);
            }else{
                productExtService.insert(productExt);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method= RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            productExtService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductExt get(@RequestParam(value="id",required=true) Long id)
    {
        return productExtService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductExt> list(){

        return productExtService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductExt> json(@RequestBody ProductExtQuery query)
    {
        Page<ProductExt> page = new Page<ProductExt>(query.getPage(),query.getRows());
            page = productExtService.selectPage(page);
            return new PageList<ProductExt>(page.getTotal(),page.getRecords());
    }
    @RequestMapping(value = "/updateViewProperties",method = RequestMethod.POST)
    public AjaxResult updateViewProperties(@RequestBody Map<String,Object> map)
    {
        //使用map
        try {
            Object productId =   map.get("productId");
            //目的是更新 ext表：
            ProductExt entity = new ProductExt();
            //key
            List<Specification> viewPropertiesList =   (List<Specification>)map.get("viewProperties");
            //将viewPropertiesList对象装成json
            String jsonString = JSONArray.toJSONString(viewPropertiesList);
            //存入数据
            entity.setViewProperties(jsonString);
            //判断条件
            Wrapper<ProductExt> wapper = new EntityWrapper<>();
            wapper.eq("productId",productId);
            productExtService.update(entity,wapper);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("操作失败:"+e.getMessage());
        }
    }
}
