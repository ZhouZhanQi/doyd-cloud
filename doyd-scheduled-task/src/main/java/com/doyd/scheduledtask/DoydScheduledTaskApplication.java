package com.doyd.scheduledtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DoydScheduledTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoydScheduledTaskApplication.class, args);
    }


}
