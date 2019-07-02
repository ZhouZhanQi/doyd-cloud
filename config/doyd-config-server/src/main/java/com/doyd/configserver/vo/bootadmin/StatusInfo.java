/*
 * Copyright 2016 the original author or authors.
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
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Represents a certain status a certain time.
 *
 * @author Johannes Stelzer
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusInfo {

    public static final String UNKNOWN = "UNKNOWN";
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String OFFLINE = "OFFLINE";

    @ApiModelProperty("状态信息")
    private String status;

    @ApiModelProperty("时间戳")
    private long timestamp;

    @JsonIgnore
    public boolean isUp() {
        return UP.equals(status);
    }

    @JsonIgnore
    public boolean isOffline() {
        return OFFLINE.equals(status);
    }

    @JsonIgnore
    public boolean isDown() {
        return DOWN.equals(status);
    }

    @JsonIgnore
    public boolean isUnknown() {
        return UNKNOWN.equals(status);
    }
}