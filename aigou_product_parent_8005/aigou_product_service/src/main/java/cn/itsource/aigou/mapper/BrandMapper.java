package cn.itsource.aigou.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liuritian.aigou.domain.Brand;
import com.liuritian.aigou.query.BrandQuery;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author wbxxx
 * @since 2019-06-23
 */
public interface BrandMapper extends BaseMapper<Brand> {


    long queryPageCount(BrandQuery brandQuery);


    List<Brand> queryPageList(BrandQuery brandQuery);

}
