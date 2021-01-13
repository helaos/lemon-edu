package com.fatehole.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.JwtUtils;
import com.fatehole.commonutil.Result;
import com.fatehole.commonutil.vo.CommentInfoVo;
import com.fatehole.eduservice.client.UcenterClient;
import com.fatehole.eduservice.entity.EduComment;
import com.fatehole.eduservice.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-12-09
 */
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    private EduCommentService commentService;

    private UcenterClient ucenterClient;

    @Autowired
    public void setCommentService(EduCommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @ApiOperation(value = "评论分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "courseQuery", value = "查询对象")
                        String courseId) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(commentPage,wrapper);
        List<EduComment> commentList = commentPage.getRecords();
        Map<String, Object> map = new HashMap<>(16);

        map.put("rows", commentList);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());

        return Result.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/auth/save")
    public Result save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return Result.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        CommentInfoVo memberInfo = ucenterClient.getMemberInfo(memberId);
        System.out.println(memberInfo);
        comment.setNickname(memberInfo.getNickname());
        comment.setAvatar(memberInfo.getAvatar());
        commentService.save(comment);
        return Result.ok();
    }

}

