package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.mapper.BrandMapper;
import cn.itsource.aigou.service.IBrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liuritian.aigou.domain.Brand;
import com.liuritian.aigou.query.BrandQuery;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author wbxxx
 * @since 2019-06-23
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageList<Brand> queryPage(BrandQuery query) {

        PageList<Brand> pageList =new PageList<>();
        long count = brandMapper.queryPageCount(query);
        if(count>0){
            List<Brand> brands = brandMapper.queryPageList(query);
            pageList.setTotal(count);
            pageList.setRows(brands);
        }
        return pageList;
    }
}
