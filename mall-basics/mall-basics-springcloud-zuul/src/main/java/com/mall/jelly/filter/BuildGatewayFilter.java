package com.mall.jelly.filter;

import com.mall.jelly.build.GatewayDirector;
import com.mall.jelly.utils.GetIP;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2基于建造者模式设计网关
 */
@Component
@Slf4j
public class BuildGatewayFilter extends ZuulFilter {



    @Autowired
    private GatewayDirector gatewayDirector;

    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //0获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("UTF-8");
        // 1.获取ip地址
        String ipAddres = GetIP.getIpAddress(request);

       //2.建造者模式网关
        gatewayDirector.direcot(ctx,ipAddres,response,request);
        return null;
    }

}
