package com.mall.jelly.build;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.context.RequestContext;

/**
 * 网关权限Build  建造者模式
 *
 */
public interface GatewayBuild {
	/**
	 * 1黑名单拦截
	 */
	Boolean blackBlock(RequestContext ctx, String ipAddres, HttpServletResponse response);

	/**
	 * 2参数验证
	 */
	Boolean toVerifyMap(RequestContext ctx, String ipAddres, HttpServletRequest request);

	/**
	 * 3api权限控制
	 * 
	 * @return
	 */
	Boolean apiAuthority(RequestContext ctx, HttpServletRequest request);
}
