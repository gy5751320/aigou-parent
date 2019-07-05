package cn.itsource.aigou.service;


import com.baomidou.mybatisplus.service.IService;
import com.liuritian.aigou.domain.Sku;
import com.liuritian.aigou.domain.Specification;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * SKU 服务类
 * </p>
 *
 */
public interface ISkuService extends IService<Sku> {

    /**
     * 商品保存sku的逻辑：
     *  1：保存扩展表
     *  2：保存sku表
     * @param productId  商品id
     * @param specificationsList  保存到扩展表的skuProperties字段
     * @param mapList  保存到sku表的
     */
    void saveSku(Long productId, List<Specification> specificationsList, List<Map<String, Object>> mapList);

}
