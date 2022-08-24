package cn.wolfcode.wolf2w.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("wenda")
public class WenDa extends BaseDomain {
    private String title;  // 标题
    private String container;   //头像
    private String info;//用户信息
    private Integer level; //等级
    private String img; //封面
    private String answer; //回答
    private String mdd; //地址
    private String mdd2; //地址2
    private Date time; // 时间

}
