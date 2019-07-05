package com.liuritian.aigou;

import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.util.PageList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EsClientFallBack implements EsClient {
    @Override
    public void batchAdd(List<ProductDoc> list) {

    }

    @Override
    public void batchDelete(List<Long> ids) {

    }

    @Override
    public PageList<ProductDoc> query(Map<String, Object> map) {
        return null;
    }
}
