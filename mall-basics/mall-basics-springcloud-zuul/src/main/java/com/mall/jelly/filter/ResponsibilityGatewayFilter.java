package com.mall.jelly.filter;

import com.mall.jelly.handler.GatewayHandler;
import com.mall.jelly.handler.ResponsibilityClient;
import com.mall.jelly.xss.XssFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  责任链网关
 */
@Component
@Slf4j
public class ResponsibilityGatewayFilter extends ZuulFilter {
    @Autowired
    private XssFilter xssFilter;
    @Autowired
    private ResponsibilityClient responsibilityClient;
    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {

        // 优先级为0，数字越大，优先级越低
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 1.获取请求对象
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        GatewayHandler handler = responsibilityClient.getHandler();
        handler.service(ctx, request, response);
        //5 XSS过滤
        xssFilter.filterParameters(request,ctx);

        return null;
    }
}
