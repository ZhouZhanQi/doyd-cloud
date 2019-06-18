package com.doyd.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DoydEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydEurekaServerApplication.class, args);
    }

}
