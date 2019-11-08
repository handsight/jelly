package com.mall.jelly.handler;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//认证失败
@Component
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
	public static final String RETURN_TYPE = "html"; // 登录失败时，用来判断是返回json数据还是跳转html页面


	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		log.info("登录失败:" + exception.getMessage());
		log.info("username=>" + request.getParameter("username"));

		if (RETURN_TYPE.equals("html")) {
			redirectStrategy.sendRedirect(request, response, "/login?error=true");
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("code","1002");
			map.put("msg","登录失败");
			map.put("data",exception.getMessage());
			ResponseUtil.send(JSONObject.toJSON(map).toString(),response);

		}




	}

}
