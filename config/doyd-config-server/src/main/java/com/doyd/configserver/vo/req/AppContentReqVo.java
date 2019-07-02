package com.doyd.configserver.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc
 */
@Data
@ApiModel("配置文件内容请求参数")
public class AppContentReqVo implements Serializable {

    @ApiModelProperty("yaml格式的配置内容")
    private String content;

}
