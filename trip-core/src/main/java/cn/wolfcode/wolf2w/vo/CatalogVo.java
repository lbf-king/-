package cn.wolfcode.wolf2w.vo;

import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CatalogVo {
    private String destName;
    private List<StrategyCatalog> catalogList = new ArrayList<>();
}


