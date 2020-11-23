package com.fatehole.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/09/15:23
 */
@Data
@ApiModel(value = "教师查询条件", description = "教师查询信息的封装条件")
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 5464564563429L;

    @ApiModelProperty(value = "教师名称; 模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔: 1高级讲师; 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询开始时间", example = "2019-12-01 10:10:10")
    private String end;
}
