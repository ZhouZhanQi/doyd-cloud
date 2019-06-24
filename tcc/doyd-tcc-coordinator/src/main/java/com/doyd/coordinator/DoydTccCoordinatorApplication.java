package com.doyd.coordinator;

import com.doyd.coordinator.handler.TccResponseErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DoydTccCoordinatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydTccCoordinatorApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate template = new RestTemplate();
        template.setErrorHandler(new TccResponseErrorHandler());
        return template;
    }

}
