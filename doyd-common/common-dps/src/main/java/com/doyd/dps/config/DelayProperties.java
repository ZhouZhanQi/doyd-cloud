package com.doyd.dps.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Zhao Junjian
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = DelayProperties.PREFIX)
public class DelayProperties {

    public static final String PREFIX = "doyd.delay";

    /**
     * 延迟返回毫秒时间
     */
    private long timeInMillseconds;

}
