package cn.itsource.aigou.service;

import com.baomidou.mybatisplus.service.IService;
import com.liuritian.aigou.domain.ProductType;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author wbxxx
 * @since 2019-06-23
 */
public interface IProductTypeService extends IService<ProductType> {

    List<ProductType> selectTreeData();
}
