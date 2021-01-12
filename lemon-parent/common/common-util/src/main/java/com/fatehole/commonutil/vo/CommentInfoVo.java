package com.fatehole.commonutil.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/09/9:03
 */
@Data
@ApiModel(value = "用户信息-评论部分", description = "评论部分需要的用户信息")
public class CommentInfoVo {

    @ApiModelProperty("id?")
    private String id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("手机")
    private String mobile;

}
