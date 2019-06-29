package com.liuritian.aigou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient//启动Eureka客户端
public class CommonApplication5005 {
    public static void main(String[] args) {
        SpringApplication.run(CommonApplication5005.class,args);
    }
}
