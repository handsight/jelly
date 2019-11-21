package com.mall.jelly;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableFeignClients
@MapperScan(basePackages = "com.mall.jelly")
public class SocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }
}
