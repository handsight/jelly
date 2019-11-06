package com.mall.jelly.handler;

import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 定义网关请求的Handler
 *
 */
public interface GatewayHandler {

	/**
	 * 每一个Handler执行的方法
	 */
	public void service(RequestContext ctx, HttpServletRequest req, HttpServletResponse response);

	/**
	 * 指向下一个Handler
	 * 
	 * @param gatewayHandler
	 */
	public void setNextHandler(GatewayHandler gatewayHandler);
}
