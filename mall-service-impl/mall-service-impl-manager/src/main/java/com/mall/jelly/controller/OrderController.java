package com.mall.jelly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

	// 自定义登陆页面
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 首页
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// 查询订单
	@RequestMapping("/showOrder")
	public String showOrder() {
		return "showOrder";
	}

	// 添加订单
	@RequestMapping("/addOrder")
	public String addOrder() {
		return "addOrder";
	}

	// 修改订单
	@RequestMapping("/updateOrder")
	public String updateOrder() {
		return "updateOrder";
	}

	// 删除订单
	@RequestMapping("/deleteOrder")
	public String deleteOrder() {
		return "deleteOrder";
	}



}
