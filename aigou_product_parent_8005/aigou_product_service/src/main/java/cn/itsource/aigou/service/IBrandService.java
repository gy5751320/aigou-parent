package cn.itsource.aigou.service;


import com.baomidou.mybatisplus.service.IService;
import com.liuritian.aigou.domain.Brand;
import com.liuritian.aigou.query.BrandQuery;
import com.liuritian.aigou.util.PageList;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author wbxxx
 * @since 2019-06-23
 */
public interface IBrandService extends IService<Brand> {

   // selectPage(page);
    PageList<Brand> queryPage(BrandQuery query);

}
