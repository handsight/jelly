package com.mall.jelly.handler.impl;

import com.mall.jelly.handler.GatewayHandler;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api验证TokenHandler
 */
@Component
@Slf4j
public class ApiAuthorityHandler extends BaseHandler implements GatewayHandler {

	@Override
	public void service(RequestContext ctx, HttpServletRequest req, HttpServletResponse response) {
		log.info(">>>>>>>第四关api验证TokenHandler执行>>>>");
	}

}
