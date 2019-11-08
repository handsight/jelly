package com.mall.jelly.service.impl;

import com.mall.jelly.response.UserOutDto;
import com.mall.jelly.service.WeiXinAppService;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 * 
 * @description:微信服务接口的实现
 */
@RestController
public class WeiXinAppServiceImpl implements WeiXinAppService {


	@Override
	public UserOutDto getApp() {
		UserOutDto user = new UserOutDto();
		user.setMobile("12345689");
		return user;
	}

}
