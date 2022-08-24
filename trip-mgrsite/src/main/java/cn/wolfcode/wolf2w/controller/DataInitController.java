package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.search.domain.DestinationEs;
import cn.wolfcode.wolf2w.search.domain.StrategyEs;
import cn.wolfcode.wolf2w.search.domain.TravelEs;
import cn.wolfcode.wolf2w.search.domain.UserInfoEs;
import cn.wolfcode.wolf2w.search.service.IDestinationEsService;
import cn.wolfcode.wolf2w.search.service.IStrategyEsService;
import cn.wolfcode.wolf2w.search.service.ITravelEsService;
import cn.wolfcode.wolf2w.search.service.IUserInfoEsService;
import cn.wolfcode.wolf2w.service.IDestinationService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.ITravelService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


//@Controller
@ResponseBody
public class DataInitController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private IUserInfoEsService userInfoEsService;
    @Autowired
    private ITravelEsService travelEsService;
    @Autowired
    private IStrategyEsService strategyEsService;
    @Autowired
    private IDestinationEsService destinationEsService;


    @RequestMapping("init")
    public String init() {
        //用户
        List<UserInfo> userInfoList = userInfoService.list();
        for (UserInfo userInfo : userInfoList) {
            UserInfoEs userInfoEs = new UserInfoEs();
            BeanUtils.copyProperties(userInfo,userInfoEs);
            userInfoEsService.save(userInfoEs);
        }
        //游记
        List<Travel> travelList = travelService.list();
        for (Travel travel : travelList) {
            TravelEs travelEs = new TravelEs();
            BeanUtils.copyProperties(travel,travelEs);
            travelEsService.save(travelEs);
        }
        //攻略
        List<Strategy> strategyList = strategyService.list();
        for (Strategy strategy : strategyList) {
            StrategyEs strategyEs = new StrategyEs();
            BeanUtils.copyProperties(strategy,strategyEs);
            strategyEsService.save(strategyEs);
        }
        //目的地
        List<Destination> destinationList = destinationService.list();
        for (Destination destination : destinationList) {
            DestinationEs destinationEs = new DestinationEs();
            BeanUtils.copyProperties(destination,destinationEs);
            destinationEsService.save(destinationEs);
        }
        return "ok";
    }

}
