package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuritian.aigou.domain.Product;
import com.liuritian.aigou.domain.ProductExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author lrt
 * @since 2019-06-28
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    //覆写insert方法
    @Autowired
    private ProductExtMapper productExtMapper;
    @Override
    public boolean insert(Product entity) {
        //设置默认 创建时间
        entity.setCreateTime(new Date().getTime());
        //更新时间
        entity.setUpdateTime(new Date().getTime());
        //先保存主表
        boolean insert = super.insert(entity);
        //在保存关联对象表 商品扩展信息信息
        //获取关联对象
        ProductExt productExt = entity.getProductExt();
        //再添加关联主键
        productExt.setProductId(entity.getId());
        //保存关联对象表
        productExtMapper.insert(productExt);
        return insert;
    }
}
