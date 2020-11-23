package com.fatehole.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
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

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
