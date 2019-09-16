package com.mall.jelly.member.service.impl;

import com.mall.jelly.member.MemberService;
import com.mall.jelly.member.feign.WeiXinAppServiceFeign;

import com.mall.jelly.response.UserOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * @description:会员服务接口实现
 */
@RestController
public class MemberServiceImpl implements MemberService {
	@Autowired
	private WeiXinAppServiceFeign weiXinAppServiceFeign;

	@Override
	public UserOutDto memberInvokeWeixin() {
		return weiXinAppServiceFeign.getApp();
	}

}
