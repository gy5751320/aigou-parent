package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuritian.aigou.EsClient;
import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.domain.Product;
import com.liuritian.aigou.domain.ProductExt;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private EsClient esClient;
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

    @Override
    public void batchOnSale(List<Long> ids) {
        //3:修改数据库状态
        for (Long id : ids) {
            //修改数据
            Product entity = new Product();
            entity.setId(id);

            //更新状态和上架时间
            entity.setOnSaleTime(new Date().getTime());
            entity.setUpdateTime(new Date().getTime());
            //将状态修改为上架
            entity.setState(2);
            productMapper.updateById(entity);

        }

        //1：根据ids查询数据，封装到productDoc中，
        List<ProductDoc> list=getProductToProductDoc(ids);
        //2：调用方法上架
        esClient.batchAdd(list);

    }

    private List<ProductDoc> getProductToProductDoc(List<Long> ids) {
        List<ProductDoc> list = new ArrayList<>();
        //根据id查询数据，封装到ProductDoc
        List<Product> products = productMapper.selectBatchIds(ids);
        for (Product product : products) {
            ProductDoc doc = new ProductDoc();
            doc.setId(product.getId());

            // name  subname productTypeName  brandName==>queryWord
            // todo:查询productTypeName brandName
            String queryWord=product.getName()+" "+product.getSubName();
            doc.setQueryWord(queryWord);
            doc.setProductTypeId(product.getProductTypeId());
            doc.setBrandId(product.getBrandId());

            //数据库保存的integer： 钱： 元 角 分  99.98==》数据库单位：分   99.98*100=9998
            doc.setMaxPrice(product.getMaxPrice());
            doc.setMinPrice(product.getMinPrice());
            doc.setSaleCount(product.getSaleCount());
            doc.setOnSaleTime(product.getOnSaleTime());
            doc.setCommentCount(product.getCommentCount());
            doc.setViewCount(product.getViewCount());
            // todo: productid +minPrice==>所有的sku==》价格最小的那一条数据
            doc.setSkuMainPic("group/filename");

            list.add(doc);

        }


        return list;
    }

    @Override
    public void batchOffSale(List<Long> ids) {
        // 1:操作es
        esClient.batchDelete(ids);
        //2：更新数据库
        for (Long id : ids) {
            Product entity = new Product();
            entity.setId(id);

            //更新状态和下架时间
            entity.setOffSaleTime(new Date().getTime());
            entity.setUpdateTime(new Date().getTime());
            entity.setState(1);
            productMapper.updateById(entity);

        }

    }

    @Override
    public PageList<ProductDoc> queryFromEs(Map<String, Object> map) {
        //调用es的方法：
        return esClient.query(map);
    }
}
