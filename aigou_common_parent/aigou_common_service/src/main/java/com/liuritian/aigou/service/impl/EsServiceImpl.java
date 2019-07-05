package com.liuritian.aigou.service.impl;


import com.liuritian.aigou.doc.ProductDoc;
import com.liuritian.aigou.repository.EsRepository;
import com.liuritian.aigou.service.IEsService;
import com.liuritian.aigou.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EsServiceImpl implements IEsService {

    @Autowired
    private EsRepository esRepository;

    @Override
    public void batchAdd(List<ProductDoc> list) {

        esRepository.saveAll(list);
    }

    @Override
    public void batchDelete(List<Long> ids) {

        for (Long id : ids) {
            esRepository.deleteById(id);
        }
    }
    //高级查询 分页
    @Override
    public PageList<ProductDoc> query(Map<String, Object> params) {
        //获取map中数据
        String keyword = (String) params.get("keyword"); //查询
        String sortField = (String) params.get("sortField"); //排序
        String sortType = (String) params.get("sortType");//排序
        Long productType = params.get("productType") !=null?Long.valueOf(params.get("productType").toString()):null;//过滤
        Long brandId = params.get("brandId") !=null?Long.valueOf(params.get("brandId").toString()):null;//过滤
        Long priceMin = params.get("priceMin") !=null?Long.valueOf(params.get("priceMin").toString())*100:null;//过滤最小价格
        Long priceMax = params.get("priceMax") !=null?Long.valueOf(params.get("priceMax").toString())*100:null;//过滤最大价格
        Long page = params.get("page") !=null?Long.valueOf(params.get("page").toString()):null; //分页
        Long rows = params.get("rows") !=null?Long.valueOf(params.get("rows").toString()):null;//分页
        //构建器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //设置查询和过滤
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //不为空
        if (StringUtils.isNotBlank(keyword)){
            //查询条件
            boolQuery.must(QueryBuilders.matchQuery("queryWord", keyword));
        }
        List<QueryBuilder> filter = boolQuery.filter();
        if (productType != null){ //类型
            // productTypeId
            filter.add(QueryBuilders.termQuery("productTypeId", productType));
        }
        if (brandId != null){ //品牌
            filter.add(QueryBuilders.termQuery("brandId", brandId));
        }
        //最大价格 最小价格
        //minPrice <= priceMax && maxPrice>=priceMin
        if(priceMax!=null){
            filter.add(QueryBuilders.rangeQuery("minPrice").lte(priceMax));
        }
        if(priceMin!=null){
            filter.add(QueryBuilders.rangeQuery("maxPrice").gte(priceMax));
        }

        builder.withQuery(boolQuery);
        //排序
        SortOrder defaultSortOrder = SortOrder.DESC;
        if (StringUtils.isNotBlank(sortField)){//销量 新品 价格 人气 评论
            //如果传入的不是降序改为升序
            if (StringUtils.isNotBlank(sortType) && sortType.equals(SortOrder.DESC)){
                defaultSortOrder = SortOrder.ASC;
            }
            //销量
            if (sortField.equals("xl")){
                builder.withSort(SortBuilders.fieldSort("saleCount").order(defaultSortOrder));
            }
            // 新品
            if (sortField.equals("xp")){
                builder.withSort(SortBuilders.fieldSort("onSaleTime").order(defaultSortOrder));
            }
            // 人气
            if (sortField.equals("rq")){
                builder.withSort(SortBuilders.fieldSort("viewCount").order(defaultSortOrder));
            }
            // 评论
            if (sortField.equals("pl")){
                builder.withSort(SortBuilders.fieldSort("commentCount").order(defaultSortOrder));
            }
            // 价格  索引库有两个字段 最大,最小
            //如果用户按照升序就像买便宜的,就用最小价格,如果用户按照降序想买贵的,用最大价格
            if (sortField.equals("jg")){
                if (SortOrder.ASC.equals(defaultSortOrder)){
                    builder.withSort(SortBuilders.fieldSort("minPrice").order(defaultSortOrder));
                }else{
                    builder.withSort(SortBuilders.fieldSort("maxPrice").order(defaultSortOrder));
                }
            }
        }
        //分页
        Long pageTmp = page-1; //从0开始
        builder.withPageable(PageRequest.of(pageTmp.intValue(), rows.intValue()));
        //截取字段 @TODO
        //封装数据
        Page<ProductDoc> productDocs = esRepository.search(builder.build());

        List<ProductDoc> content = productDocs.getContent();
        return new PageList<>(productDocs.getTotalElements(),content);
    }
}
