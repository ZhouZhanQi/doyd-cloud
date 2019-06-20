package com.doyd.bootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class DoydBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydBootAdminApplication.class, args);
    }

}
