package cn.itsource.aigou.web.controller;


import cn.itsource.aigou.service.IProductExtService;
import cn.itsource.aigou.service.ISpecificationService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liuritian.aigou.domain.ProductExt;
import com.liuritian.aigou.domain.Specification;
import com.liuritian.aigou.query.SpecificationQuery;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    public ISpecificationService specificationService;

    @Autowired
    public IProductExtService productExtService;


    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Specification specification){
        try {
            if(specification.getId()!=null){
                specificationService.updateById(specification);
            }else{
                specificationService.insert(specification);
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
            specificationService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@RequestParam(value="id",required=true) Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Specification> list(){

        return specificationService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        Page<Specification> page = new Page<Specification>(query.getPage(),query.getRows());
            page = specificationService.selectPage(page);
            return new PageList<Specification>(page.getTotal(),page.getRecords());
    }

    /**
     * 通过商品分类，获取这个分类的显示属性  /specification/viewProperties/productTypeId
     * @param productTypeId 商品分类的id
     * @return
     */
    @RequestMapping(value="/viewProperties/{productTypeId}/{productId}",method= RequestMethod.GET)
    public List<Specification> viewProperties(@PathVariable("productTypeId") Long productTypeId, @PathVariable("productId") Long productId){
        //判断是添加还是修改： 判断条件
        Wrapper<ProductExt> extWrapper= new EntityWrapper<>();
        extWrapper.eq("productId",productId);
        ProductExt productExt = productExtService.selectOne(extWrapper);
        //如果有数据就是修改 且不为空
        if(productExt != null &&  !StringUtils.isEmpty(productExt.getViewProperties())){
            //获取viewProperties 现在是json
            String viewProperties = productExt.getViewProperties();
            //在转成对象 取数据
            return JSONArray.parseArray(viewProperties, Specification.class);
        }else{
            Wrapper<Specification> wapper = new EntityWrapper<>();
            // SELECT * FROM `t_specification` s where s.productTypeId=1 and s.type=1 ;
            wapper.eq("productTypeId",productTypeId);
            wapper.eq("type",1);
            return   specificationService.selectList(wapper);
        }

    }

    @RequestMapping(value="/skuProperties/{productTypeId}",method=RequestMethod.GET)
    public List<Specification> skuProperties(@PathVariable("productTypeId") Long productTypeId){

        Wrapper<Specification>  wapper = new EntityWrapper<>();
        // SELECT * FROM `t_specification` s where s.productTypeId=1 and s.type=1 ;
        wapper.eq("productTypeId",productTypeId);
        wapper.eq("type",2);
        return   specificationService.selectList(wapper);
    }
}
