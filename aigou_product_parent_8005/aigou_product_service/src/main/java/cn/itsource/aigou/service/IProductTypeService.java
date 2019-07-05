package cn.itsource.aigou.service;

import com.baomidou.mybatisplus.service.IService;
import com.liuritian.aigou.domain.ProductType;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 */
public interface IProductTypeService extends IService<ProductType> {

    List<ProductType> selectTreeData();
}
