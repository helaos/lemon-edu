package com.fatehole.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.commonutil.JwtUtils;
import com.fatehole.commonutil.MD5;
import com.fatehole.educenter.entity.UcenterMember;
import com.fatehole.educenter.entity.vo.LoginVo;
import com.fatehole.educenter.entity.vo.RegisterVo;
import com.fatehole.educenter.mapper.UcenterMemberMapper;
import com.fatehole.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String login(LoginVo loginVo) {

        // 获取手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 手机号和密码的非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new LemonException(20001, "登陆失败！");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);

        // 判断对象是否为空
        if (member == null) {
            throw new LemonException(20001, "该手机没有注册");
        }

        // 校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new LemonException(20001, "密码错误");
        }

        // 校验是否被禁用
        if(member.getIsDisabled()) {
            throw new LemonException(20001, "该账号已被封禁，如有问题请练习管理员申诉！");
        }

        // 使用JWT生成token字符串
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册信息，进行校验
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new LemonException(20001, "注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);

        if (StringUtils.isEmpty(redisCode)) {
            throw new LemonException(20001, "验证码已失效");
        }

        if (!code.equals(redisCode)) {
            throw new LemonException(20001, "验证码错误！");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("mobile", mobile);

        Integer count = baseMapper.selectCount(wrapper);

        if (count > 0) {
            throw new LemonException(20001, "手机号已被注册");
        }

        UcenterMember member = new UcenterMember();

        // 保存到数据库
        member
                .setMobile(mobile)
                .setNickname(nickname)
                .setPassword(MD5.encrypt(password))
                .setIsDisabled(false)
                .setAvatar("https://lemon-edu.oss-cn-beijing.aliyuncs.com/avatar/2020/11/16a5381233476a4ac0ad631154c91bbd1ffile.png");

        this.save(member);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("openid", openid);

        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int countRegisterByDay(String day) {

        return baseMapper.selectRegisterCount(day);
    }
}
