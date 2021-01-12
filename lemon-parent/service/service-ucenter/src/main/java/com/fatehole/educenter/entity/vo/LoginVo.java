package com.fatehole.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/29/23:03
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 87676645673423L;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
