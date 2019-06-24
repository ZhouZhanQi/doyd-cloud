package com.doyd.dps.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Zhao Junjian
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = ManualExceptionProperties.PREFIX)
public class ManualExceptionProperties {
    public static final String PREFIX = "solar.exception";

    /**
     * 是否启用随机异常
     */
    private boolean enabled;

    /**
     * 当对此数取余为0就会抛出异常
     */
    private int factor;

}
