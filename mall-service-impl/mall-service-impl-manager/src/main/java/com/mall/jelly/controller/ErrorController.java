package com.mall.jelly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	// 403权限不足页面
	@RequestMapping("/error/403")
	public String error1() {
		return "/error/403";
	}
	// 500服务器错误页面
	@RequestMapping("/error/500")
	public String error2() {
		return "/error/500";
	}
}
