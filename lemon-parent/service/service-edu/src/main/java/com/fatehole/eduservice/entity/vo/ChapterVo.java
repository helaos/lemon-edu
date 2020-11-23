package com.fatehole.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/21/21:26
 */

@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 17546353457529L;

    private String id;

    private String title;

    /**
     * 表示小节
     */
    private List<VideoVo> children = new ArrayList<>();
}
