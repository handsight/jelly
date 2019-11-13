package com.mall.jelly;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableFeignClients
@MapperScan(basePackages = "com.mall.jelly")
public class SearchApplication {
    public static void main(String[] args) {

        //springBoot同时整合 Redis 和 ES 时，发生了冲突
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(SearchApplication.class, args);
    }
}
