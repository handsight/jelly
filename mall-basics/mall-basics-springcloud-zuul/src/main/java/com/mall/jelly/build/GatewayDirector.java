package com.mall.jelly.build;

import com.mall.jelly.xss.XssFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 链接Build
 *
 */
@Component
public class GatewayDirector {


	@Resource(name = "verificationBuild")
	private GatewayBuild gatewayBuild;
	@Autowired
	private XssFilter xssFilter;
	/**
	 * 连接建造者
	 * 
	 * @param ctx
	 * @param ipAddres
	 * @param response
	 * @param request
	 */
	public void direcot(RequestContext ctx, String ipAddres, HttpServletResponse response, HttpServletRequest request) {
		// 1.黑名单
		Boolean blackBlock = gatewayBuild.blackBlock(ctx, ipAddres, response);
		if (!blackBlock) {
			return;
		}
        // 2.参数验证
		Boolean verifyMap = gatewayBuild.toVerifyMap(ctx, ipAddres, request);
		if (!verifyMap) {
			return;
		}
		// 3.验证accessToken
		Boolean apiAuthority = gatewayBuild.apiAuthority(ctx, request);
		if (!apiAuthority) {
			return;
		}
		//4 XSS过滤
		xssFilter.filterParameters(request,ctx);
	}

}
