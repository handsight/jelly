package com.mall.jelly.feign;

import com.mall.jelly.WeiXinAppService;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "app-mall-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {


}
