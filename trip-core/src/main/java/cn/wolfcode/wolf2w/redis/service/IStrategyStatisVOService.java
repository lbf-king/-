package cn.wolfcode.wolf2w.redis.service;

import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;

import java.util.List;

public interface IStrategyStatisVOService {
    /**
     * 阅读数加一
     * @param id 攻略id
     */
    void viewnumIncr(Long id);

    /**
     * 回显阅读数
     * @param sid  攻略id
     * @return 返回一个  vo对象
     */
    StrategyStatisVO queryStatisVO(Long sid);

    /**
     * 设置器  搞出一个vo对象
     * @param vo
     */
    void setStatisVO(StrategyStatisVO vo);

    /**
     * 评论数  加一
     * @param strategyId  攻略id
     */
    void replynumIncr(Long strategyId);

    /**
     * 收藏  实现
     * @param sid  攻略id
     * @return true  收藏成功
     * false  取消收藏
     */
    boolean favor(Long sid,Long uid);

    /**
     * 回显蓝色小星星
     * @param sid
     * @param uid
     * @return
     */
    boolean isFavorBySis(Long sid, Long uid);

    /**
     * 实现点赞数加一
     * @param sid 攻略id
     * @param uid 用户id
     * @return true  表示 可以点赞  false  表示 今天已经点过了
     */
    boolean strategyThumbup(Long sid, Long uid);

    /**
     * 判断这个key 存不存在
     * @param id
     * @return
     */
    boolean isVoExists(Long id);

    /**
     * 获取到 redis中 所有 VO  的key的集合
     * @param join
     * @return
     */
    List<StrategyStatisVO> queryByPattern(String join);
}
