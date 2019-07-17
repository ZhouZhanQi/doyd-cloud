package com.doyd.admingateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class DoydAdminGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydAdminGatewayApplication.class, args);
    }


}
