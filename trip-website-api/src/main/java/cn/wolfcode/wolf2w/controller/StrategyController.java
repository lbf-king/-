package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.annotation.RequiredLogin;
import cn.wolfcode.wolf2w.annotation.UserParam;
import cn.wolfcode.wolf2w.domain.*;
import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.mongodb.service.IStrategyCommentService;
import cn.wolfcode.wolf2w.query.StrategyQuery;
import cn.wolfcode.wolf2w.redis.service.IStrategyStatisVOService;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import cn.wolfcode.wolf2w.service.IStrategyConditionService;
import cn.wolfcode.wolf2w.service.IStrategyRankService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.IStrategyThemeService;
import cn.wolfcode.wolf2w.util.JsonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("strategies")
public class StrategyController {
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyRankService strategyRankService;
    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    @Autowired
    private IStrategyConditionService strategyConditionService;
    @Autowired
    private IStrategyStatisVOService strategyStatisVOService;
    @Autowired
    private IStrategyCommentService strategyCommentService;

    @GetMapping("/content")
    //查询目的地下的概况
    public JsonResult content(Long id) {
        return JsonResult.success(strategyService.getContent(id));
    }


    @GetMapping("/detail")
    //查询 攻略明细
    public JsonResult detail(Long id) {
        //查询到攻略明细对象
        Strategy strategy = strategyService.getById(id);
        //获取到文章内容
        StrategyContent content = strategyService.getContent(id);
        //将文章内容 设置到 明细对象中 返回给前端
        strategy.setContent(content);


        //实现阅读数  +1
        strategyStatisVOService.viewnumIncr(id);

        return JsonResult.success(strategy);
    }




    @GetMapping("/themes")
    public JsonResult themes() {
        List<StrategyTheme> list = strategyThemeService.list();
        return JsonResult.success(list);
    }

    /**
     *分页
     */
    @GetMapping("/query")
    public JsonResult query(StrategyQuery qo) {
        IPage<Strategy> page = strategyService.queryPage(qo);
        return JsonResult.success(page);
    }

    /**
     * 热门推荐排行
     * @param type
     * @return
     */
    @GetMapping("/rank")
    public JsonResult rank(Integer type) {
        List<StrategyRank> list = strategyRankService.queryByType(type);
        return JsonResult.success(list);
    }


    /**
     * 攻略条件统计操作
     * @param type
     * @return
     */
    @GetMapping("/condition")
    public JsonResult condition(Integer type) {
        List<StrategyCondition> list = strategyConditionService.queryByType(type);
        return JsonResult.success(list);
    }

    /**
     * 保存攻略评论 信息
     * @param strategyComment
     * @param request
     * @return
     */
    @PostMapping("/commentAdd")
    public JsonResult condition(StrategyComment strategyComment, HttpServletRequest request) {
        //通过请求对象  拿到 请求头中的 token
        String token = request.getHeader("token");
        //获取到user对象
        UserInfo user = userInfoRedisService.getUserByToken(token);
        //将user对象  中的信息  复制到  strategyComment对象中
        BeanUtils.copyProperties(user,strategyComment);
        strategyComment.setUserId(user.getId());

        strategyCommentService.save(strategyComment);


        strategyStatisVOService.replynumIncr(strategyComment.getStrategyId());
        return JsonResult.success();
    }

    /**
     * 攻略阅读数  回显
     * @param sid  攻略id
     * @return 返回一个vo对象 给前端
     */
    @GetMapping("/statisVo")
    public JsonResult statisVo(Long sid) {
        StrategyStatisVO vo = strategyStatisVOService.queryStatisVO(sid);
        return JsonResult.success(vo);
    }

    /**
     * 评论分页
     * @param qo
     * @return
     */
    @GetMapping("/comments")
    public JsonResult comments(StrategyCommentQuery qo) {
        Page<StrategyComment> list = strategyCommentService.queryPage(qo);
        return JsonResult.success(list);
    }


    /**
     * 收藏 实现
     * @param sid  攻略 id
     * @param userInfo 用户信息
     * @return  返回 布尔值
     */
    @RequiredLogin
    @PostMapping("/favor")
    public JsonResult favor(Long sid , @UserParam UserInfo userInfo) {
        boolean b = strategyStatisVOService.favor(sid,userInfo.getId());
        return JsonResult.success(b);
    }

    /**
     * 实现点赞数加一
     * @param sid
     * @param userInfo
     * @return
     */
    @RequiredLogin
    @PostMapping("/strategyThumbup")
    public JsonResult strategyThumbup(Long sid , @UserParam UserInfo userInfo) {
        boolean b = strategyStatisVOService.strategyThumbup(sid,userInfo.getId());
        return JsonResult.success(b);
    }

    /*
        主题推荐
         */

    /**
     * 热门推荐排行
     * @param
     * @return
     */
    @GetMapping("/themeCds")
    public JsonResult themeCds() {
        List<Strategy> list = strategyService.list();
        return JsonResult.success(list);
    }



}
