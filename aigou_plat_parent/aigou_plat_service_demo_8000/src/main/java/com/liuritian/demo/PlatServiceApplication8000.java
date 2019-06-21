package com.liuritian.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端
public class PlatServiceApplication8000 {
    public static void main(String[] args) {
        //启动sb
        SpringApplication.run(PlatServiceApplication8000.class);
    }
}
