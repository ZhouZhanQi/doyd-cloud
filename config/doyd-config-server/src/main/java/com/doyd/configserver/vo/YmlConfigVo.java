package com.doyd.configserver.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc 配置文件
 */
@Data
@ApiModel("配置文件")
public class YmlConfigVo implements Serializable {

    @ApiModelProperty("application name")
    private String application;

    @ApiModelProperty("配置文件内容")
    private String content;
}
