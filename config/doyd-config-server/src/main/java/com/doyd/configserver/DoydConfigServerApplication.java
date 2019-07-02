package com.doyd.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigServer
@SpringBootApplication
public class DoydConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydConfigServerApplication.class, args);
    }

}
