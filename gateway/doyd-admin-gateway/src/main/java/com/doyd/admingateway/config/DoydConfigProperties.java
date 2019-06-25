package com.doyd.admingateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 自定义配置
 * @author Bernix Ning
 * @date 2018-01-09
 */
@RefreshScope
@ConfigurationProperties(prefix = "doyd")
public class DoydConfigProperties {

    private List<String> openUrls;

    private List<String> publicUrls;

    public List<String> getOpenUrls() {
        return openUrls;
    }

    public void setOpenUrls(List<String> openUrls) {
        this.openUrls = openUrls;
    }

    public List<String> getPublicUrls() {
        return publicUrls;
    }

    public void setPublicUrls(List<String> publicUrls) {
        this.publicUrls = publicUrls;
    }
}
