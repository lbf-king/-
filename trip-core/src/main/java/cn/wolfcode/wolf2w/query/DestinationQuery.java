package cn.wolfcode.wolf2w.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DestinationQuery extends  QueryObject {
    private Long parentId; //父目的地id
}
