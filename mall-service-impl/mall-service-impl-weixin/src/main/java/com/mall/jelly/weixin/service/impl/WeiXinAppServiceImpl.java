package com.mall.jelly.weixin.service.impl;

import com.mall.jelly.weixin.entity.AppEntity;
import com.mall.jelly.weixin.service.WeiXinAppService;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 * 
 * @description:微信服务接口的实现
 */
@RestController
public class WeiXinAppServiceImpl implements WeiXinAppService {


	@Override
	public AppEntity getApp() {
		return new AppEntity("123456789", "888888");
	}

}
