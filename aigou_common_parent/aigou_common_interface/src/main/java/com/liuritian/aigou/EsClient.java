package com.liuritian.aigou;

import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "COMMON-PRIVODER",fallback = EsClientFallBack.class)
public interface EsClient {
    //批量上架
    @RequestMapping(value = "/es/onsale",method = RequestMethod.POST)
    void batchAdd(@RequestBody List<ProductDoc> list);
    //批量下架
    @RequestMapping(value = "/es/offsale",method = RequestMethod.GET)
    void batchDelete(@RequestParam("ids") List<Long> ids);
    //高级查询 面包屑 匹配 价格
    @RequestMapping(value = "/es/query",method = RequestMethod.POST)
    PageList<ProductDoc> query(@RequestBody Map<String,Object> map);
}
