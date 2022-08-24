package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Banner;
import cn.wolfcode.wolf2w.query.BannerQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 首页服务接口
 */
public interface IBannerService extends IService<Banner>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Banner> queryPage(BannerQuery qo);

    /**
     * 显示  文章  或者  攻略  前五
     * @param type
     * @return
     */
    List<Banner> queryByType(int type);

}
