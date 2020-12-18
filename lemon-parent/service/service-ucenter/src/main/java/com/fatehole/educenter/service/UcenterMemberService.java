package com.fatehole.educenter.service;

import com.fatehole.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.educenter.entity.vo.LoginVo;
import com.fatehole.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 会员登陆
     * @param loginVo 登陆的信息
     * @return token
     */
    String login(LoginVo loginVo);

    /**
     * 会员注册
     * @param registerVo 注册信息
     */
    void register(RegisterVo registerVo);

    /**
     * ？？？
     * @param openid 微信ID
     * @return
     */
    UcenterMember getByOpenid(String openid);

    /**
     * 根据日期查询当天的注册人数
     * @param day 日期
     * @return 注册人数
     */
    int countRegisterByDay(String day);
}
