package cn.wolfcode.wolf2w.search.service.impl;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.search.query.SearchQueryObject;
import cn.wolfcode.wolf2w.search.service.ISearchService;
import cn.wolfcode.wolf2w.service.IDestinationService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.ITravelService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private ElasticsearchRestTemplate template;

    //关键字: keyword = 广州
    //以title为例:
    //原始匹配: "有娃必看,广州长隆野生动物园全攻略"
    //高亮显示后:"有娃必看,<span style="color:red;">广州</span>长隆野生动物园全攻略"
    @Override
    public <T> Page<T> searchWithHighlight(String index, Class<T> clz, SearchQueryObject qo, String... fields) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //高亮显示
        /*"query":{
            "multi_match": {
                "query": "广州",
                "fields": ["title","subTitle","summary"]
            }
        },*/
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(qo.getKeyword(),fields);
        HighlightBuilder highlightBuilder = new HighlightBuilder(); // 生成高亮查询器
        for (String field: fields) {
            highlightBuilder.field(field);// 高亮查询字段
        }
        highlightBuilder.requireFieldMatch(false); // 如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style='color:red'>"); // 高亮设置
        highlightBuilder.postTags("</span>");
        highlightBuilder.fragmentSize(800000); // 最大高亮分片数
        highlightBuilder.numOfFragments(0); // 从第一个分片获取高亮片段
        /**
         "from": 0,
         "size":3,
         */
        Pageable pageable = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize(),
                Sort.Direction.ASC, "_id");// 设置分页参数
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder) // match查询
                .withPageable(pageable).withHighlightBuilder(highlightBuilder) // 设置高亮
                .build();


        SearchHits<T> searchHits = template.search(searchQuery, clz,IndexCoordinates.of(index));
        List<T> list = new ArrayList();
        for (SearchHit<T> searchHit : searchHits) { // 获取搜索到的数据
            T content = this.parseType(clz, searchHit.getId());

            // 处理高亮
            Map<String, String> map = highlightFieldsCopy(searchHit.getHighlightFields(), fields);

            //1：spring 框架中BeanUtils 类，如果是map集合是无法进行属性复制
            //   copyProperties(源， 目标)
            //2: apache  BeanUtils 类 可以进map集合属性复制
            //   copyProperties(目标， 源)
            try {
                BeanUtils.copyProperties(content, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(content);
        }
        Page page = new PageImpl(list, pageable, searchHits.getTotalHits());
        return  page;
    }


    private <T> T parseType(Class<T> clz, String id){
        Long lId = 0L;
        if(StringUtils.hasLength(id)){
            lId = Long.valueOf(id);
        }
        T t = null;
        if(clz == UserInfo.class){
            t = (T) userInfoService.getById(lId);
        }else if(clz == Travel.class){
            t = (T) travelService.getById(lId);

        }else if(clz == Strategy.class){
            t = (T) strategyService.getById(lId);
        }else if(clz == Destination.class){
            t = (T) destinationService.getById(lId);
        }else{
            t= null;
        }
        return t;
    }



    //fields: title subTitle summary
    private Map<String, String>   highlightFieldsCopy(Map<String, List<String>> map, String ...fields){
        Map<String, String> mm = new HashMap<>();
        //title:  "有娃必看，<span style='color:red;'>广州</span>长隆野生动物园全攻略"
        //subTitle: "<span style='color:red;'>广州</span>长隆野生动物园"
        //summary: "如果要说动物园，楼主强烈推荐带娃去<span style='color:red;'>广州</span>长隆野生动物园
        //title subTitle summary
        for (String field : fields) {
            List<String> hfs = map.get(field);
            if (hfs != null && !hfs.isEmpty()) {
                //获取高亮显示字段值, 因为是一个数组, 所有使用string拼接

                StringBuilder sb = new StringBuilder();
                for (String hf : hfs) {
                    sb.append(hf);
                }
                mm.put(field, sb.toString());  //使用map对象将所有能替换字段先缓存, 后续统一替换
            }
        }
        return mm;
    }



}
