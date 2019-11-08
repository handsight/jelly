package com.mall.jelly.build.feign;

import com.mall.jelly.AuthorizationService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-auth")
public interface AuthorizationServiceFeign extends AuthorizationService {

}
