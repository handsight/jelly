package com.mall.jelly.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.mall.jelly.OrderSeckillService;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.constants.Prefix;
import com.mall.jelly.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderSeckillServiceImpl extends BaseApiService<JSONObject> implements OrderSeckillService {
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Override
	public BaseResponse<JSONObject> getOrder(String phone, Long seckillId) {
		log.info(">>>>>>查询秒杀结果线程名称:" + Thread.currentThread().getName());
		if (StringUtils.isEmpty(phone)) {
			return setResultError("手机号码不能为空!");
		}
		if (seckillId == null) {
			return setResultError("商品库存id不能为空!");
		}
		Object obj = stringRedisTemplate.opsForHash().get(Prefix.SECKILL_STOCK_SECKILLID+seckillId, phone);
		if (obj == null) {
			return setResultError("秒杀失败.....");
		}
		return setResultSuccess("恭喜你秒杀成功!");
	}

}
