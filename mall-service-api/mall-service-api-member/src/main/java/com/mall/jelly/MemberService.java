package com.mall.jelly;

import com.mall.jelly.response.UserOutDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;




@Api(tags = "会员服务接口")
public interface MemberService {

	/**
	 * 会员服务接口调用微信接口
	 * 
	 * @return
	 */
	@ApiOperation(value = "会员服务调用微信服务")
	@GetMapping("/memberInvokeWeixin")
	 UserOutDto memberInvokeWeixin();

}
