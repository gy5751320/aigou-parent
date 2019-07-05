package cn.itsource.aigou.web.controller;


import cn.itsource.aigou.service.ISkuService;
import com.baomidou.mybatisplus.plugins.Page;
import com.liuritian.aigou.domain.Sku;
import com.liuritian.aigou.domain.Specification;
import com.liuritian.aigou.query.SkuQuery;
import com.liuritian.aigou.util.AjaxResult;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController// 和controller的区别，controller怎么返回json数据
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    public ISkuService skuService;

    /**
    * 保存和修改公用的
    * @param sku  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Sku sku){
        try {
            if(sku.getId()!=null){
                skuService.updateById(sku);
            }else{
                skuService.insert(sku);
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
            skuService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Sku get(@RequestParam(value="id",required=true) Long id)
    {
        return skuService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Sku> list(){

        return skuService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Sku> json(@RequestBody SkuQuery query)
    {
        Page<Sku> page = new Page<Sku>(query.getPage(),query.getRows());
            page = skuService.selectPage(page);
            return new PageList<Sku>(page.getTotal(),page.getRecords());
    }


    /**
     * controller：接收参数，和业务调用
     * service： 业务处理
     * dao：操作数据库
     * @param map
     * @return
     */
    @RequestMapping(value = "/skuProperties",method = RequestMethod.POST)
    public AjaxResult skuProperties(@RequestBody Map<String,Object> map)
    {
        try {
            //接收参数：
            //调用业务方法：保存ext，保存sku表

            Long productId= Long.valueOf( map.get("productId")+"");
            // map不能直接转换成一个domain：
            //1:在List<Specification> specificationsList参数直接改成接收到的map传过去，serviceimpl中通过map能够获取到所有需要的数据
            //2:在controller中，通过自己写方法，把map转成一个domain：==》局限性比较强
            //3:找一个工具类：把map转成domain：最好

            List<Map<String,Object>> skuProperties =(List<Map<String,Object>>)map.get("skuProperties");
            List<Specification> specificationsList=new ArrayList<>();
            for (Map<String, Object> objectMap : skuProperties) {

                Specification specification = new Specification();
                specification.setId(Long.valueOf(objectMap.get("id")+""));
                specification.setSpecName(objectMap.get("specName")+"");
                //
                ArrayList<String> skuValue = (ArrayList<String>) objectMap.get("skuValue");
                specification.setSkuValue(skuValue.toArray(new String[]{}));
                specificationsList.add(specification);
            }

           // List<Specification> specificationsList= (List<Specification>) map.get("skuProperties");
            List<Map<String, Object>> mapList= (List<Map<String, Object>>) map.get("skuDatas");;
            skuService.saveSku(productId,specificationsList,mapList);
            return  AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return  AjaxResult.me().setSuccess(false).setMsg("操作失败:"+e.getMessage());
        }


    }




}
