package com.mall.jelly.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.SpikeCommodityService;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.constants.Prefix;
import com.mall.jelly.dao.SeckillDao;
import com.mall.jelly.entity.SeckillEntity;
import com.mall.jelly.producer.SpikeCommodityProducer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SeckillDao seckillDao;
    @Autowired
    private SpikeCommodityProducer spikeCommodityProducer;

	@Override
	public void initSpike() {
		List<SeckillEntity> list = seckillDao.findList();
		if(list == null) {
			return;
		}
		for(SeckillEntity stock : list) {
			stringRedisTemplate.opsForHash().put(Prefix.SECKILL_STOCK,stock.getSeckillId().toString(),stock.getInventory().toString());
		}
	}
	/**
	 * 使用网关开启限流
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@HystrixCommand(fallbackMethod = "spikeFallback")
	public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
		log.info("###>>>>>秒杀接口线程池名称:" + Thread.currentThread().getName());
		// 1.参数验证
		if (StringUtils.isEmpty(phone)) {
			return setResultError("手机号码不能为空!");
		}
		if (seckillId == null) {
			return setResultError("商品库存id不能为空!");
		}
		//2.扣减redis库存  可以改为前端传入值
        Long stock2 = stringRedisTemplate.opsForHash().increment(Prefix.SECKILL_STOCK, seckillId.toString(), -1);
        if(stock2 < 0) {
            return setResultError("亲，该秒杀已经售空，请下次再来!");
        }
       //3.判断是否已经秒杀到了
        Object obj = stringRedisTemplate.opsForHash().get(Prefix.SECKILL_STOCK_SECKILLID+seckillId, phone);
        if(obj != null) {
            return setResultError("不能重复秒杀!");
        }
		//4.获取到秒杀token之后，异步放入mq中实现修改商品的库存
		sendSeckillMsg(seckillId, phone);
		return setResultSuccess("正在排队中.......");
	}



	private BaseResponse<JSONObject> spikeFallback(String phone, Long seckillId) {
		return setResultError("服务器忙,请稍后重试!");
	}

	/**
	 * 获取到秒杀token之后，异步放入mq中实现修改商品的库存
	 */
	@Async
    void sendSeckillMsg(Long seckillId, String phone) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seckillId", seckillId);
		jsonObject.put("phone", phone);
		spikeCommodityProducer.send(jsonObject);
	}

}
