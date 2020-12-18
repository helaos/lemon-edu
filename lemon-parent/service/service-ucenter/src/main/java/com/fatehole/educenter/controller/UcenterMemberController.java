package com.fatehole.educenter.controller;


import com.fatehole.commonutil.JwtUtils;
import com.fatehole.commonutil.Result;
import com.fatehole.commonutil.vo.CommentInfoVo;
import com.fatehole.educenter.entity.UcenterMember;
import com.fatehole.educenter.entity.vo.LoginVo;
import com.fatehole.educenter.entity.vo.RegisterVo;
import com.fatehole.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-29
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    private UcenterMemberService memberService;

    @Autowired
    public void setMemberService(UcenterMemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public Result loginUser(@RequestBody LoginVo loginVo) {

        String token = memberService.login(loginVo);

        return Result.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);

        return Result.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/info")
    public Result getUserInfo(HttpServletRequest request) {
        // 调用JWT工具类的方法
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        // 查询id查询寻用户信息 TODO: 应该新建一个现代VO来返回用户信息，屏蔽调密码等敏感信息
        UcenterMember member = memberService.getById(memberId);

        return Result.ok().data("info", member);
    }

    @ApiOperation(value = "根据token字符串获取用户信息")
    @PostMapping("/{id}")
    public CommentInfoVo getMemberInfo(@PathVariable("id") String id) {
        // 根据用户id获取用户信息
        UcenterMember member = memberService.getById(id);
        CommentInfoVo infoVo = new CommentInfoVo();
        // 拷贝信息
        BeanUtils.copyProperties(member, infoVo);
        return infoVo;
    }

    @ApiOperation(value = "获取某日期的注册人数")
    @GetMapping("/count/{day}")
    public Result registerCount(@PathVariable("day") String day) {
        // 查询
        int count = memberService.countRegisterByDay(day);
        // 返回
        return Result.ok().data("count", count);
    }
}

