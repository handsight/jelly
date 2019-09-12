package com.mall.jelly.weixin.service;

import com.mall.jelly.weixin.entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "微信服务接口")
public interface WeiXinAppService {

	/**
	 * 功能说明： 应用服务接口
	 */
	@ApiOperation(value = "微信应用服务接口")
	@GetMapping("/getApp")
	public AppEntity getApp();

}
