package com.liuritian.aigou.service;


import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.util.PageList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface IEsService {
    //批量添加
    void batchAdd(@RequestBody List<ProductDoc> list);
    /**
     * 批量下架
     * @param ids
     */
    void batchDelete(@RequestParam List<Long> ids);
    //高级查询
    PageList<ProductDoc> query(Map<String,Object> map);


}
