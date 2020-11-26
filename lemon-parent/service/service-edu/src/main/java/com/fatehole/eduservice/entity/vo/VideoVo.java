package com.fatehole.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/21/21:26
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 4564563543534529L;

    private String id;

    private String title;
}
