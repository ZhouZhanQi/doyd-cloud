/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * The domain model for all registered application at the spring boot admin application.
 */
@Data
@ApiModel("bootadmin微服务信息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BootAdminAppVo {

    @ApiModelProperty("微服务Id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("")
    private String managementUrl;

    @ApiModelProperty("健康检查地址")
    private String healthUrl;

    @ApiModelProperty("服务地址")
    private String serviceUrl;

    @ApiModelProperty("状态信息")
    private StatusInfo statusInfo;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("额外参数")
    private Map<String, String> metadata;

    @ApiModelProperty("信息")
    private Map<String, String> info;

    @JsonIgnore
    public String getInfoDescription() {
        return info == null ? null : info.get("description");
    }

    @JsonIgnore
    public String getInfoVersion() {
        return info == null ? null : info.get("version");
    }
}
