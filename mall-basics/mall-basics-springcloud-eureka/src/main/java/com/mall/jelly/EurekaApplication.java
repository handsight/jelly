package com.mall.jelly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //开启EurekaS服务
public class EurekaApplication {
    public static void main(String[] args) {


        SpringApplication.run(EurekaApplication.class, args);
    }
}
