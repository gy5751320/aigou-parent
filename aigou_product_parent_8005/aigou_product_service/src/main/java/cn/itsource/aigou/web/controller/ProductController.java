package cn.itsource.aigou.web.controller;

import cn.itsource.aigou.service.IProductService;
import com.baomidou.mybatisplus.plugins.Page;
import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.domain.Product;
import com.liuritian.aigou.query.ProductQuery;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.PageList;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.insert(product);
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
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@RequestParam(value="id",required=true) Long id)
    {
        return productService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
        Page<Product> page = new Page<Product>(query.getPage(),query.getRows());
        page = productService.selectPage(page);
        return new PageList<Product>(page.getTotal(),page.getRecords());
    }
    /**
     * 传上下架的标识和ids
     * @param dealType  2  上架  1下架 判断是上架还是下架
     * @return 批量操作的数据
     */
    @RequestMapping(value = "/dealsale",method = RequestMethod.GET)
    public AjaxResult dealsale(@RequestParam("dealType") String dealType,@RequestParam("ids") List<Long> ids)
    {
        try {
            if(!StringUtils.isEmpty(dealType)){

                if("2".equals(dealType)){
                    //上架
                    productService.batchOnSale(ids);
                }else{
                    //下架
                    productService.batchOffSale(ids);
                }

            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return  AjaxResult.me().setMsg("操作失败:"+e.getMessage()).setSuccess(false);
        }
    }

    @RequestMapping(value = "/queryProducts",method = RequestMethod.POST)
    public PageList<ProductDoc> queryProducts(@RequestBody Map<String,Object> map)
    {

        return    productService.queryFromEs(map);

    }
}
