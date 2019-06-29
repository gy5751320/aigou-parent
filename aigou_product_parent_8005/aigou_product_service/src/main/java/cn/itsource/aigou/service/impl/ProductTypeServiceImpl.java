package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuritian.aigou.PageClient;
import com.liuritian.aigou.RedisClient;
import com.liuritian.aigou.domain.ProductType;
import com.liuritian.aigou.util.AigouConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private PageClient pageClient;
    //覆写修改方法 redis修改数据
    @Override
    public boolean updateById(ProductType entity) {
//        先修改数据库 调用父类的方法
        boolean b = super.updateById(entity);
        //在修改redis
        List<ProductType> productTypes = treeDataLoop();
        //讲list对象转换成json
        String productTypesJson = JSONArray.toJSONString(productTypes);
        redisClient.set(AigouConstants.REDIS_PRODUCTTYPE_KEY, productTypesJson);
        //修改之后从新生成静态页面 存入map
        Map<String,Object> map = new HashMap<>();
        //存入当数据
        map.put(AigouConstants.PAGE_MODEL, productTypes);
        //模板
        String path = "D:\\Software\\IntelliJ IDEA 2017.3.3\\IdeaProjects\\aigou-parent\\aigou_product_parent_8005\\aigou_product_service\\src\\main\\resources\\template\\product.type.vm";
        map.put(AigouConstants.PAGE_TEMPLATE_FILE_PATH_NAME,path );
        //生成文件存放的地址
        String wjPath = "D:\\Software\\IntelliJ IDEA 2017.3.3\\IdeaProjects\\aigou-parent\\aigou_product_parent_8005\\aigou_product_service\\src\\main\\resources\\template\\product.type.vm.html";
        map.put(AigouConstants.PAGE_TARGET_FILE_PATH_NAME, wjPath);
        pageClient.createStaticPage(map);
//        //home的页面
        Map<String,Object> homeMap  = new HashMap<>();

        Map<String,Object> modelMap  = new HashMap<>();
        modelMap.put("staticRoot","D:\\Software\\IntelliJ IDEA 2017.3.3\\IdeaProjects\\aigou-parent\\aigou_product_parent_8005\\aigou_product_service\\src\\main\\resources\\");
        homeMap.put(AigouConstants.PAGE_MODEL,modelMap);
        //模板文件：
        path ="D:\\Software\\IntelliJ IDEA 2017.3.3\\IdeaProjects\\aigou-parent\\aigou_product_parent_8005\\aigou_product_service\\src\\main\\resources\\template\\home.vm";
        homeMap.put(AigouConstants.PAGE_TEMPLATE_FILE_PATH_NAME,path);

        //生成的文件：放到前端的项目中
        wjPath = "D:\\Software\\IntelliJ IDEA 2017.3.3\\IdeaProjects\\aigou-parent\\aigou_product_parent_8005\\aigou_product_service\\src\\main\\resources\\html\\home.html";
        homeMap.put(AigouConstants.PAGE_TARGET_FILE_PATH_NAME,wjPath);
        pageClient.createStaticPage(homeMap);
        return b;
    }
    @Override
    public List<ProductType> selectTreeData() {
        //现获取key REDIS_PRODUCTTYPE_KEY 在判断是否有结果 有直接返回 没有就从数据库查找 在存入redis在返回
        String redisProducttypeKey = redisClient.get(AigouConstants.REDIS_PRODUCTTYPE_KEY);
        if(StringUtils.isEmpty(redisProducttypeKey)){
            //没有 就从数据库查询 再添加到redis
            List<ProductType> productTypes = treeDataLoop();
            System.out.println("从数据库拿数据===============================");
            //讲对象转换成json
            String productTypesJson = JSON.toJSONString(productTypes);
            //将数据添加到redis
            redisClient.set(AigouConstants.REDIS_PRODUCTTYPE_KEY, productTypesJson);
            //返回查询的数据
            return productTypes;
        }else {
            //直接返回 将string装成json数组字符串
            System.out.println("从redis拿数据===============================");
            return JSON.parseArray(redisProducttypeKey, ProductType.class);
        }
        //组装树结构：
  }

    private List<ProductType> treeDataLoop() {
        //发送一条sql，查询所有的数据
        List<ProductType> productTypeList = new ArrayList<>();
        productTypeList = productTypeMapper.selectList(null);
        //组装数据id和数据对象的关系
        Map<Long,ProductType> map = new HashMap<>() ;
        for (ProductType c : productTypeList) {
            map.put(c.getId(),c);
        }

        List<ProductType> result = new ArrayList<>();
        //遍历每一个对象：找它下一级设置给他
        for (ProductType current : productTypeList) {
                //找当前对象的上一级：
            Long pid = current.getPid();
            if(pid==0){
                //一级菜单
                result.add(current);
                continue;
            }
            ProductType parent = map.get(pid);
            parent.getChildren().add(current);
        }

        return result;
    }

    private List<ProductType> treeDataRecursion(long pid) {
        //  select * from t_product_type where pid =0
        //某一个的节点的下一级：
        List<ProductType> allChildren = getAllChildren(pid);

        //没有下一级 递归的出口
        if(allChildren==null||allChildren.size()==0){
            return null;
        }

        // 1   100   163
        for (ProductType child : allChildren) {
            // 获取当前对象的下一级
            Long currentId = child.getId();
            List<ProductType> sons = treeDataRecursion(currentId);
            child.setChildren(sons);

        }

        return allChildren;
    }


    private List<ProductType> getAllChildren(long pid) {
        List<ProductType> children = new ArrayList<>();
        Wrapper<ProductType> wrapper =new EntityWrapper<>();
        wrapper.eq("pid",pid);
        return  productTypeMapper.selectList(wrapper);
    }


}
