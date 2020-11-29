package com.fatehole.educms.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/28/13:28
 */
@Data
@ApiModel(value = "Banner查询对象", description = "Banner查询对象封装")
public class BannerQuery implements Serializable {

    private static final long serialVersionUID = 4455345214523L;

    @ApiModelProperty(value = "图片名称")
    private String title;

    @ApiModelProperty(value = "路由地址")
    private String linkUrl;

}
