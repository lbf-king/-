package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 根据手机号做查询条件  来查询 数据库中有没有符合的数据
     * @param phone 前端请求带过来的参数
     * @return 返回布尔值  true表示数据存在  false表示数据不存在
     */
    boolean checkPhone(String phone);

    /**
     * 创建验证码
     * @param phone 根据手机号 创建验证码
     */
    void sendVerifyCode(String phone);

    /**
     * 校验参数
     * @param nickname
     * @param password
     * @param rpassword
     * @param phone
     * @param verifyCode
     */
    void regist(String nickname, String password,String rpassword, String phone, String verifyCode) ;

    /**
     * 根据账号  密码 查询数据库 有没有对应得数据
     * @param username 就是phone
     * @param password
     * @return
     */
    UserInfo login(String username, String password);

    /**
     * 根据目的地名称 来查询所有用户的list
     * @param city
     * @return
     */
    List<UserInfo> queryByDestid(String city);

}
