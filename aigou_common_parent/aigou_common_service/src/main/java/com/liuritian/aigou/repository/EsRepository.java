package com.liuritian.aigou.repository;

import com.liuritian.aigou.doc.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface EsRepository extends ElasticsearchRepository<ProductDoc,Long> {
}
