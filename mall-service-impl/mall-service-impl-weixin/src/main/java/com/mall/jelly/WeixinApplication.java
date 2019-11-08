package com.mall.jelly;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 
 * 
 * @description: 微服务服务实现
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableApolloConfig
public class WeixinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinApplication.class, args);
	}
}
