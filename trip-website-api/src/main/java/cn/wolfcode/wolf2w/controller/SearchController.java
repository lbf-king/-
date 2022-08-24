package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.search.query.SearchQueryObject;
import cn.wolfcode.wolf2w.search.service.ISearchService;
import cn.wolfcode.wolf2w.search.vo.SearchResultVO;
import cn.wolfcode.wolf2w.service.IDestinationService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.ITravelService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import cn.wolfcode.wolf2w.util.JsonResult;
import cn.wolfcode.wolf2w.util.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
@RestController
public class SearchController {

    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ISearchService searchService;

    @GetMapping("/q")
    public JsonResult q (SearchQueryObject qo) throws UnsupportedEncodingException {
        //url解码
        qo.setKeyword(URLDecoder.decode(qo.getKeyword(),"utf-8"));


        switch (qo.getType()) {
            case SearchQueryObject.TYPE_DEST : return this.searchDest(qo);
            case SearchQueryObject.TYPE_STRATEGY : return this.searchStrategy(qo);
            case SearchQueryObject.TYPE_TRAVEL : return this.searchTravel(qo);
            case SearchQueryObject.TYPE_USER : return this.searchUser(qo);
            default:return this.searchAll(qo);
        }
    }

    //搜索所有
    private JsonResult searchAll(SearchQueryObject qo) {
        SearchResultVO vo = new SearchResultVO();
        Page<UserInfo> up = searchService.searchWithHighlight("userinfo",UserInfo.class, qo, "info","city");
        Page<Travel> tp = searchService.searchWithHighlight("travel",Travel.class, qo, "title","summary");
        for (Travel travel : tp) {
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        Page<Strategy> sp = searchService.searchWithHighlight("strategy",Strategy.class, qo, "title","subTitle","summary");
        Page<Destination> dp = searchService.searchWithHighlight("destination",Destination.class, qo, "name","info");

        vo.setTravels(tp.getContent());
        vo.setStrategys(sp.getContent());
        vo.setUsers(up.getContent());
        vo.setDests(dp.getContent());
        vo.setTotal(dp.getTotalElements() + up.getTotalElements() + sp.getTotalElements() + tp.getTotalElements());

        return JsonResult.success(ParamMap.newInstance().put("qo",qo).put("result",vo));
    }

    //搜索用户
    private JsonResult searchUser(SearchQueryObject qo) {
        Page<UserInfo> page = searchService.searchWithHighlight("userinfo",UserInfo.class, qo, "info","city");
        return JsonResult.success(ParamMap.newInstance().put("qo",qo).put("page",page));
    }

    //搜索游记
    private JsonResult searchTravel(SearchQueryObject qo) {
        Page<Travel> page = searchService.searchWithHighlight("travel",Travel.class, qo, "title","summary");
        for (Travel travel : page) {
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        return JsonResult.success(ParamMap.newInstance().put("qo",qo).put("page",page));
    }

    //搜索攻略
    private JsonResult searchStrategy(SearchQueryObject qo) {
        Page<Strategy> page = searchService.searchWithHighlight("strategy",Strategy.class, qo, "title","subTitle","summary");
        return JsonResult.success(ParamMap.newInstance().put("qo",qo).put("page",page));
    }

    //搜索目的地
    private JsonResult searchDest(SearchQueryObject qo) {
        Destination destination = destinationService.getByName(qo.getKeyword());
        SearchResultVO vo = new SearchResultVO();
        //根据目的地 对象  查询目的地下的攻略游记 用户
        if (destination != null) {
            List<Strategy> strategyList = strategyService.queryByDestId(destination.getId());
            List<Travel> travelList = travelService.queryByDestId(destination.getId());
            List<UserInfo> userInfoList = userInfoService.queryByDestid(destination.getName());

            vo.setUsers(userInfoList);
            vo.setStrategys(strategyList);
            vo.setTravels(travelList);
            vo.setTotal(userInfoList.size() + strategyList.size() + travelList.size() + 0L);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("dest",destination);
        map.put("result",vo);
        map.put("qo",qo);
        return JsonResult.success(ParamMap.newInstance().put("qo",qo).put("dest",destination).put("result",vo));
    }
}
