package com.fatehole.eduservice.controller;

import com.fatehole.commonutil.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * 解决跨域请求问题 @CrossOrigin
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/14/15:13
 */
@Api(tags = "管理员登陆" )
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("/login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
