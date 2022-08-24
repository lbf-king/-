package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.domain.TravelContent;
import cn.wolfcode.wolf2w.query.TravelQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 游记服务接口
 */
public interface ITravelService extends IService<Travel>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Travel> queryPage(TravelQuery qo);

    /**
     * 修改 审核状态
     * @param id
     * @param state
     */
    void auditByIdAndState(Long id, Integer state);

    /**
     * 查看游记内容
     * @param id
     * @return
     */
    TravelContent getContentById(Long id);

    List<Travel> queryByDestid(Long destId);

    /**
     * 根据  目的地id  获取到游记集合
     * @param id
     * @return
     */
    List<Travel> queryByDestId(Long id);
}
