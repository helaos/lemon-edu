package com.fatehole.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/09/15:23
 */
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称; 模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔: 1高级讲师; 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询开始时间", example = "2019-12-01 10:10:10")
    private String end;
}
