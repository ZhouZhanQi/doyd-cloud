package com.doyd.config.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 应用信息
 *
 * @author zhouzq
 * @date 2019/6/19 0:58
 */
public class AppInfoVo {

    @ApiModelProperty("应用ID")
    private String name;

    @ApiModelProperty("应用实例的url列表(一个应用可能有多个实例)")
    private List<String> urls;

    @ApiModelProperty("应用描述")
    private String description;

    @ApiModelProperty("应用版本")
    private String version;

    @JsonIgnore
    private boolean initialized;
}
