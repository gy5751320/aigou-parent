package com.liuritian.aigou.web.controller;


import com.liuritian.aigou.EsClient;
import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.service.IEsService;
import com.liuritian.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EsController implements EsClient {

    @Autowired
    private IEsService esService;


    @RequestMapping(value = "/es/onsale",method = RequestMethod.POST)
    @Override
    public void batchAdd(@RequestBody List<ProductDoc> list) {
        esService.batchAdd(list);

    }

    @RequestMapping(value = "/es/offsale",method = RequestMethod.GET)
    @Override
    public void batchDelete(@RequestParam("ids") List<Long> ids) {
        esService.batchDelete(ids);
    }
    @RequestMapping(value = "/es/query",method = RequestMethod.POST)
    @Override
    public PageList<ProductDoc> query(@RequestBody Map<String, Object> map) {
        return esService.query(map);
    }
}
