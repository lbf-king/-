package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.WenDa;
import cn.wolfcode.wolf2w.mapper.WenDaMapper;
import cn.wolfcode.wolf2w.query.WenDaQuery;
import cn.wolfcode.wolf2w.service.IWenDaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WenDaServiceImpl extends ServiceImpl<WenDaMapper, WenDa> implements IWenDaService {

    @Autowired
    private WenDaMapper wenDaMapper;

    @Override
    public Page<WenDa> queryList(WenDaQuery qo) {
        QueryWrapper<WenDa> wrapper = Wrappers.<WenDa>query();
        Page<WenDa> wenDaPage = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        return super.page(wenDaPage,wrapper);
    }
}
