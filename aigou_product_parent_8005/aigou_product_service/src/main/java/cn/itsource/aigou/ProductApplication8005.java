package cn.itsource.aigou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient //eureka的客户端
@MapperScan("cn.itsource.aigou.mapper")
@EnableFeignClients(basePackages="com.liuritian.aigou")
public class ProductApplication8005 {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication8005.class);
    }
}
