package com.mall.jelly.member.feign;

import com.mall.jelly.weixin.service.WeiXinAppService;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "app-mall-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {


}
