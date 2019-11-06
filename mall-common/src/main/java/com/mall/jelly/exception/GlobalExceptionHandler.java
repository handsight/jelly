package com.mall.jelly.exception;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseApiService<JSONObject> {
	// 定义错误显示页，error.html
	public static final String DEFAULT_ERROR_VIEW = "error";
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseResponse<JSONObject> defaultErrorHandler(HttpServletRequest request, Exception e) {

		log.info("###全局捕获异常###,error:{}", e);
		return setResultError("系统错误!");
	}

	public ModelAndView defaultErrorHandler2(HttpServletRequest request,
			Exception e) { // 出现异常之后会跳转到此方法
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW); // 设置跳转路径
		mav.addObject("exception", e); // 将异常对象传递过去
		mav.addObject("url", request.getRequestURL()); // 获得请求的路径
		return mav;
	}


//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	public com.alibaba.fastjson.JSONObject defaultErrorHandler3(HttpServletRequest request,Exception e) {
//		JSONObject result = new JSONObject();
//		result.put("code", 500);; 	// 标记一个错误信息类型
//		result.put("msg", e.getMessage());
//		result.put("url", request.getRequestURL());
//		return result ;
//	}

}
