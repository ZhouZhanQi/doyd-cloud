/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Value type for the instance identifier
 */
@Data
public final class InstanceId implements Serializable, Comparable<InstanceId> {
    private String value;

    private InstanceId(String value) {
        Assert.hasText(value, "'value' must have text");
        this.value = value;
    }

    @JsonCreator
    public static InstanceId of(String value) {
        return new InstanceId(value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(InstanceId that) {
        return this.value.compareTo(that.value);
    }
}
