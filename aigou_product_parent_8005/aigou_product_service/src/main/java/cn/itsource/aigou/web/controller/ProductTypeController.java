package cn.itsource.aigou.web.controller;


import cn.itsource.aigou.service.IBrandService;
import cn.itsource.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liuritian.aigou.domain.Brand;
import com.liuritian.aigou.domain.ProductType;
import com.liuritian.aigou.query.ProductTypeQuery;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.PageList;
import com.liuritian.aigou.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Autowired
    private IProductTypeService productTypeService;
    @Autowired
    private IBrandService brandService; 
    /**
    * 保存和修改公用的
    * @param productType  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductType productType){
        try {
            if(productType.getId()!=null){
                productTypeService.updateById(productType);
            }else{
                productTypeService.insert(productType);
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
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            productTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductType get(@RequestParam(value="id",required=true) Long id)
    {
        return productTypeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductType> list(){

        return productTypeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductType> json(@RequestBody ProductTypeQuery query)
    {
        Page<ProductType> page = new Page<ProductType>(query.getPage(),query.getRows());
            page = productTypeService.selectPage(page);
            return new PageList<ProductType>(page.getTotal(),page.getRecords());
    }

    // treeData
    @RequestMapping(value = "/treeData",method = RequestMethod.GET)
    public List<ProductType> treeData()
    {

        return productTypeService.selectTreeData();
    }
    //获取面包屑 返回json
    @RequestMapping(value = "/crumbs/{productTypeId}", method = RequestMethod.GET)
    public List<Map<String, Object>> crumbs(@PathVariable("productTypeId") Long productTypeId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        //通过id获取当前path 在通过path获取家谱
        ProductType productType = productTypeService.selectById(productTypeId);//将数据传入service
        //获取path
        String path = productType.getPath();//.1.2.7.
        //拆分 并且转成Long
        List<Long> longs = StrUtils.splitStr2LongArr(path, "\\.");//[1,2,7]
        for (Long id : longs) {
            Map<String,Object> map = new HashMap<>();
            ProductType productType1 = productTypeService.selectById(id);
            map.put("ownerProductType", productType1);
            //在获取到姊姊妹妹哥哥弟弟
            Long pid = productType1.getPid();
            //查询的老父亲的所有子女
            Wrapper<ProductType> wrapper = new EntityWrapper<>();
            wrapper.eq("pid", pid);
            List<ProductType> productTypes = productTypeService.selectList(wrapper);
            //遍历 获取到自己 再删除
            for (int i = 0; i < productTypes.size(); i++) {
                ProductType productType2 = productTypes.get(i);
                //获取到自己
                Long id1 = productType2.getId();
                //判断是否相等
                if (id1.equals(productType1.getId())){
                    productTypes.remove(i);
                    map.put("otherProductTypes", productTypes);
                    break;
                }
            }
            //存入list
            resultList.add(map);
        }
        //再将数据返回前台
        return resultList;
    }
    //展示品牌
    @RequestMapping(value = "/brands/{productTypeId}",method = RequestMethod.GET)
    public List<Brand> brandList(@PathVariable("productTypeId") Long productTypeId){
        //获取所有品牌
        Wrapper<Brand> wrapper = new EntityWrapper<>();
        //判断条件 productTypeId
        wrapper.eq("product_type_id", productTypeId);
        return  brandService.selectList(wrapper);
    }


    public static void main(String[] args) {
        String demo = ".1.2.1.";
        System.out.println(StrUtils.splitStr2LongArr(demo, "\\."));
    }
}
