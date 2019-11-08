package com.mall.jelly.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.AuthorizationService;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.entity.MeiteAppInfo;
import com.mall.jelly.mapper.AppInfoMapper;
import com.mall.jelly.utils.Jwt;
import com.mall.jelly.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthorizationServiceImpl extends BaseApiService<JSONObject> implements AuthorizationService {

	@Value("${jwt.secretKey.api_token_secret}")
	private String app_token_secret;



	@Autowired
	private AppInfoMapper appInfoMapper;

	@Override
	public BaseResponse<JSONObject> applyAppInfo(String appName) {
		// 1.验证参数
		if (StringUtils.isEmpty(appName)) {
			return setResultError("机构名称不能为空!");
		}
		// 2.生成appid和appScrec
		String appId = UUID.randomUUID().toString();
		String appScrect = MD5Util.encode("key" + appId).toUpperCase();
		// 3.添加数据库中
		MeiteAppInfo meiteAppInfo = new MeiteAppInfo(appName, appId, appScrect);
		int insertAppInfo = appInfoMapper.insertAppInfo(meiteAppInfo);
		if (!toDaoResult(insertAppInfo)) {
			return setResultError("申请失败!");
		}
		// 4.返回给客户端
		JSONObject data = new JSONObject();
		data.put("appId", appId);
		data.put("appScrect", appScrect);
		return setResultSuccess(data);
	}

	@Override
	public BaseResponse<JSONObject> getAccessToken(String appId, String appSecret) {
		// 1.参数验证
		if (StringUtils.isEmpty(appId)) {
			return setResultError("appId不能为空!");
		}
		if (StringUtils.isEmpty(appSecret)) {
			return setResultError("appSecret不能为空!");
		}
		// 2.使用appId+appSecret查询数据库
		MeiteAppInfo meiteAppInfo = appInfoMapper.selectByAppInfo(appId, appSecret);
		if (meiteAppInfo == null) {
			return setResultError("appId或者是appSecret错误");
		}
		// 3.生成accessToken 存入redis
		String accessToken = this.createToken(Long.parseLong(meiteAppInfo.getAppId()));
//		stringRedisTemplate.opsForValue().set(UserKey.USER_TOKEN + dbUser.getUserId().toString(), firstToken, 7, TimeUnit.DAYS);
		JSONObject data = new JSONObject();
		data.put("accessToken", accessToken);
		return setResultSuccess(data);
	}

	public BaseResponse<JSONObject> getAppInfo(String accessToken) {
		// 1.验证参数
		if (StringUtils.isEmpty(accessToken)) {
			return setResultError("AccessToken cannot be empty ");
		}
		// 2.从redis中获取accessToken
//		String appId = generateToken.getToken(accessToken);
		String appId="123456";
		if (StringUtils.isEmpty(appId)) {
			return setResultError("accessToken  invalid");
		}
		// 3.使用appid查询数据库
		MeiteAppInfo meiteAppInfo = appInfoMapper.findByAppInfo(appId);
		if (meiteAppInfo == null) {
			return setResultError("AccessToken  invalid");
		}
		// 4.返回应用机构信息
		JSONObject data = new JSONObject();
		data.put("appInfo", meiteAppInfo);
		return setResultSuccess(data);
	}
	private String createToken(Long userId) {
		//生成token
		Map<String, Object> payload = new HashMap<>();
		payload.put("userId", userId);
		payload.put("ext", System.currentTimeMillis() + Jwt.TIMEOUT);
		return Jwt.createToken(payload, app_token_secret);
	}
}
