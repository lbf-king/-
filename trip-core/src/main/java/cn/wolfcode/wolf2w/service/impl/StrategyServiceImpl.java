package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.*;
import cn.wolfcode.wolf2w.mapper.StrategyMapper;
import cn.wolfcode.wolf2w.query.StrategyQuery;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import cn.wolfcode.wolf2w.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* 攻略服务接口实现
*/
@Service
@Transactional
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper,Strategy> implements IStrategyService  {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyContentService strategyContentService;

    @Override
    public IPage<Strategy> queryPage(StrategyQuery qo) {
        IPage<Strategy> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        wrapper.eq(qo.getDestId() != null,"dest_id",qo.getDestId());
        wrapper.eq(qo.getThemeId() != null,"theme_id",qo.getThemeId());

        Long refid = qo.getRefid();
        Integer type = qo.getType();
        if (refid != null && type != null) {
            if (StrategyCondition.TYPE_THEME == type) {
                wrapper.eq("theme_id",refid);
            } else if(StrategyCondition.TYPE_ABROAD == type || StrategyCondition.TYPE_CHINA == type) {
                wrapper.eq("dest_id",refid);
            }
        }

        wrapper.orderByDesc(StringUtils.hasText(qo.getOrderBy()),qo.getOrderBy());

        return super.page(page, wrapper);
    }

    @Override
    public StrategyContent getContent(Long id) {
        StrategyContent content = strategyContentService.getById(id);
        return content;
    }


    /**
     * 重写  saveOrUpdate
     * @param entity
     * @return
     */
    @Override
    public boolean saveOrUpdate(Strategy entity) {
        //通过分类id  获取 分类 对象 目的地的 id和name   同时 搞定分类名字
        StrategyCatalog catalog = strategyCatalogService.getById(entity.getCatalogId());
        entity.setDestId(catalog.getDestId());
        entity.setDestName(catalog.getDestName());
        entity.setCatalogName(catalog.getName());

        //通过主题  id 获取 主题名字
        StrategyTheme theme = strategyThemeService.getById(entity.getThemeId());
        entity.setThemeName(theme.getName());

        //设置创建时间
        entity.setCreateTime(new Date());
        //判断是否国外
        //通过目的地  id 获取 吐司列表
        List<Destination> toasts = destinationService.queryToastsByParentId(entity.getDestId());
        //如果列表 第一个元素  是中国  表示是国内
        if ("中国".equals(toasts.get(0))) {
            entity.setIsabroad(Strategy.ABROAD_NO);
            //否则是国外
        } else {
            entity.setIsabroad(Strategy.ABROAD_YES);
        }

        boolean b = false;
        //设置五个 计数  默认为0
        if (entity.getId() == null) {
            entity.setViewnum(0);
            entity.setReplynum(0);
            entity.setFavornum(0);
            entity.setSharenum(0);
            entity.setThumbsupnum(0);
            b = super.save(entity);
            //插入文章内容
            StrategyContent content = entity.getContent();
            content.setId(entity.getId());
            strategyContentService.save(content);
        } else {
            b = super.updateById(entity);
            //插入文章内容
            StrategyContent content = entity.getContent();
            content.setId(entity.getId());
            strategyContentService.updateById(content);
        }

        return b;
    }

    @Override
    public List<Strategy> queryByCatalogId(Long id) {
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        wrapper.eq("catalog_id",id);
        List<Strategy> list = super.list(wrapper);
        return list;
    }

    @Override
    public List<Strategy> queryByDestid(Long destId) {
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        wrapper.eq("dest_id",destId);
        wrapper.orderByDesc("viewnum");
        wrapper.last("limit 3");
        return super.list(wrapper);
    }

    @Override
    public void updateStatisNum(StrategyStatisVO vo) {
        UpdateWrapper<Strategy> wrapper = Wrappers.<Strategy>update();
        wrapper.eq("id",vo.getStrategyId());
        wrapper.set("viewnum",vo.getViewnum());
        wrapper.set("replynum",vo.getReplynum());
        wrapper.set("favornum",vo.getFavornum());
        wrapper.set("sharenum",vo.getSharenum());
        wrapper.set("thumbsupnum",vo.getThumbsupnum());
        super.update(wrapper);
    }

    @Override
    public List<Strategy> queryByDestId(Long id) {
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        wrapper.eq("dest_id",id);

        List<Strategy> strategyList = super.list(wrapper);
        return strategyList;
    }
}
