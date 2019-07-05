package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.SkuMapper;
import cn.itsource.aigou.service.ISkuService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuritian.aigou.domain.ProductExt;
import com.liuritian.aigou.domain.Sku;
import com.liuritian.aigou.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * SKU 服务实现类
 * </p>
 *
 * @author wbxxx
 * @since 2019-07-01
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {

    @Autowired
    private ProductExtMapper productExtMapper;

    @Autowired
    private SkuMapper skuMapper;


    /**
     * 操作ext
     * 操作sku表
     * @param productId  商品id
     * @param specificationsList  保存到扩展表的skuProperties字段
     * @param mapList  保存到sku表的
     */
    @Override
    public void saveSku(Long productId, List<Specification> specificationsList, List<Map<String, Object>> mapList) {
        //1:更新ext表的skuProperties  ==》specificationsList
        ProductExt productExt=new ProductExt();
        productExt.setSkuProperties(JSONArray.toJSONString(specificationsList));
        Wrapper<ProductExt> wrapper = new EntityWrapper<>();
        wrapper.eq("productId",productId);
        productExtMapper.update(productExt,wrapper);
        //2：保存到sku表中：==》mapList:
        for (Map<String, Object> skuMap : mapList) {
            //一个map对应一个sku：
            //1:创建一个sku
            Sku sku = new Sku();
            //2： map==>转sku
            //2.1：设置不是前台传过来的数据
            sku.setCreateTime(new Date().getTime());
            sku.setUpdateTime(new Date().getTime());
            sku.setProductId(productId);
            //2.2:设置前台穿过来的数据：

            //sku.setPrice();
            //List<Map>:转要放到skuProperties中的值
            List<Map<String,Object>> otherMapList=new ArrayList<>();
            Set<Map.Entry<String, Object>> entries = skuMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if("price".equals(key)){
                    sku.setPrice(Integer.valueOf(value+""));
                }else if("availableStock".equals(key)){
                    sku.setAvailableStock(Integer.valueOf(value+""));
                }else{
                    //sku属性:  颜色=yellow,
                    Map<String,Object> otherMap= new HashMap<>();
                    otherMap.put(key,value);
                    // otherMap.put("颜色","yellow");
                    otherMapList.add(otherMap);
                }
            }

            List<Map<String,Object>> speMapList = new ArrayList<>();
            for (Map<String, Object> map : otherMapList) {
                // map:  {"颜色":"yellow"}
                Map<String,Object> speMap = new HashMap<>();
                // map ===>speMap转换
                Set<Map.Entry<String, Object>> entries1 = map.entrySet();
                for (Map.Entry<String, Object> stringObjectEntry : entries1) {
                    String key = stringObjectEntry.getKey();
                    Object value = stringObjectEntry.getValue();
                    speMap.put("key",key);//颜色
                    speMap.put("value",value);
                    //id:1：select id from t_spe.... where speName=key and productTypeId =?==>要发送sql
                    //2: 不发sql，从specificationsList中取获取：
                    Object id = getSpeId(key,specificationsList);
                    speMap.put("id",id);
                }
                speMapList.add(speMap);
            }
            sku.setSkuProperties(JSONArray.toJSONString(speMapList));

            Map<String, String> sortIndexAndNameMap = getSortIndex(speMapList, specificationsList);
            sku.setSortIndex(sortIndexAndNameMap.get("sortIndex"));
            sku.setSkuName(sortIndexAndNameMap.get("skuName"));

            //3：保存这个sku：
            skuMapper.insert(sku);
        }

    }

    /**
     * 从specificationsList 获取specName=key的数据的id
     * @param key
     * @param specificationsList
     * @return
     */
    private Object getSpeId(String key, List<Specification> specificationsList) {
        // java.util.LinkedHashMap cannot be cast to cn.itsource.aigou.domain.Specification
        for (Specification specification : specificationsList) {
            String specName = specification.getSpecName();
            if(key.equals(specName)){
                return  specification.getId();
            }
        }
        return null;
    }

    /**
     * 获取sortIndex
     * @param speMapList    [{"id":1,"key":"颜色","value":"yellow"},{"id":2,"key":"尺寸","value":"26"}]
     * @param specificationsList
     * @return
     */
    public Map<String,String> getSortIndex(List<Map<String, Object>> speMapList, List<Specification> specificationsList) {
        Map<String,String> resultMap= new HashMap<>();
        StringBuilder sortIndex = new StringBuilder();
        StringBuilder skuName = new StringBuilder();
        for (Map<String, Object> map : speMapList) {
            //map:{"id":1,"key":"颜色","value":"yellow"}
            String  specNameMap = map.get("key")+"";
            String value = map.get("value")+"";
            for (Specification specification : specificationsList) {
                String specName = specification.getSpecName();
                if(specNameMap.equals(specName)){
                    String[] skuValue = specification.getSkuValue();
                    for (int i = 0; i < skuValue.length; i++) {
                        String istr =  skuValue[i];
                        if(value.equals(istr)){
                            sortIndex.append(i).append("_");
                            skuName.append(value).append("_");
                            break;
                        }
                        //
                    }
                }
            }

        }
        String s = sortIndex.toString();
        resultMap.put("sortIndex",s.substring(0,s.length()-1));
        resultMap.put("skuName",skuName.toString().substring(0,s.length()-1));
        return resultMap;
    }

    public static void main(String[] args) {
        String s="1_0_";
        System.out.println(s.substring(0, s.length() - 1));
    }

}
