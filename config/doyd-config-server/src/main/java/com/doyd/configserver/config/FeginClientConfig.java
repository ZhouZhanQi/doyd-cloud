package com.doyd.configserver.config;

import com.doyd.configserver.helper.ByteArrayDecoder;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author zhouzq
 * @date 2019/7/3 1:52
 */
@Configurable
public class FeginClientConfig {

    @Bean
    public Logger.Level feiginLogger(){
        return Logger.Level.FULL;
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuth() {
        return new BasicAuthRequestInterceptor("zhouzq", "825116");
    }

    @Bean
    public ByteArrayDecoder decoder() {
        return new ByteArrayDecoder();
    }


}
