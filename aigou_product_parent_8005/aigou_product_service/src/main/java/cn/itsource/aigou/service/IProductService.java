package cn.itsource.aigou.service;

import com.baomidou.mybatisplus.service.IService;
import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.domain.Product;
import com.liuritian.aigou.util.PageList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author lrt
 * @since 2019-06-28
 */
public interface IProductService extends IService<Product> {
    /**
     * 批量上架
     * @param ids 需要上架的ids
     */
    void batchOnSale(List<Long> ids);

    void batchOffSale(List<Long> ids);

    PageList<ProductDoc> queryFromEs(Map<String, Object> map);
}
