package com.fatehole.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/24/13:04
 */
@Data
@ApiModel(value = "课程查询对象", description = "课程查询对象封装")
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 23434343645634523L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "一级类别ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别ID")
    private String subjectId;

}
