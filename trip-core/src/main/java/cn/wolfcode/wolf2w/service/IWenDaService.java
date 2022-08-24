package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.WenDa;
import cn.wolfcode.wolf2w.query.WenDaQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IWenDaService extends IService<WenDa> {

    Page<WenDa> queryList(WenDaQuery qo);
    

}
