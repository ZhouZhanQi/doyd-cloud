/*
 * Copyright 2014-2018 the original author or authors.
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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registration info for the instance registers with (including metadata)
 *
 * @author Johannes Edmeier
 */
@Data
@NoArgsConstructor
@ToString(exclude = "metadata")
public class Registration implements Serializable {

    private String name;

    private String managementUrl;

    private String healthUrl;

    private String serviceUrl;

    private String source;

    private Map<String, String> metadata;
}
