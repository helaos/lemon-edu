package com.fatehole.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/23/16:35
 */
@Data
@ApiModel(value = "课程发布信息")
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 6745433225467529L;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程一级分类")
    private String subjectLevelOne;

    @ApiModelProperty(value = "课程二级分类")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程价格")
    private String price;
}
