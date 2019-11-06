package com.mall.jelly.build.impl;

import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.build.GatewayBuild;
import com.mall.jelly.constants.Constants;
import com.mall.jelly.utils.SignUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 参数验证Build
 *
 */
@Slf4j
@Component
public class VerificationBuild implements GatewayBuild {



	@Override
	public Boolean blackBlock(RequestContext ctx, String ipAddres, HttpServletResponse response) {

		//1.查询redis黑名单

		return true;
	}

	@Override
	public Boolean toVerifyMap(RequestContext ctx, String ipAddres, HttpServletRequest request) {
		// 2.验证签名拦截
		Map<String, String> verifyMap = SignUtil.toVerifyMap(request.getParameterMap(), true);
		if (!SignUtil.verify(verifyMap)) {
			resultError(ctx, "ip:" + ipAddres + ",Sign fail");
			return false;
		}
		return true;
	}



	@Override
	public Boolean apiAuthority(RequestContext ctx, HttpServletRequest request) {
		// 3.api权限控制
		String servletPath = request.getServletPath();
		log.info(">>>>>servletPath:" + servletPath + ",servletPath.substring(0, 5):" + servletPath.substring(0, 5));
		if (!servletPath.substring(0, 7).equals("/public")) {
			return true;
		}
		String accessToken = request.getParameter("accessToken");
		log.info(">>>>>accessToken验证:" + accessToken);
		if (StringUtils.isEmpty(accessToken)) {
			resultError(ctx, "AccessToken cannot be empty");
			return false;
		}
		// 调用接口验证accessToken是否失效
//		BaseResponse<JSONObject> appInfo = verificaCodeServiceFeign.getAppInfo(accessToken);
//		log.info(">>>>>>data:" + appInfo.toString());
//		if (!isSuccess(appInfo)) {
//			resultError(ctx, appInfo.getMsg());
//			return false;
//		}
		return true;
	}

	// 接口直接返回true 或者false
	public Boolean isSuccess(BaseResponse<?> baseResp) {
		if (baseResp == null) {
			return false;
		}
		if (!baseResp.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return false;
		}
		return true;
	}


	private void resultError(RequestContext ctx, String errorMsg) {
		ctx.setResponseStatusCode(401);
		// 网关响应为false 不会转发服务
		ctx.setSendZuulResponse(false);
		ctx.setResponseBody(errorMsg);
	}
}
