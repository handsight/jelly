package com.mall.jelly.consumer;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.constants.Prefix;
import com.mall.jelly.consumer.config.SpikeCommodityConsumerConfig;

import com.mall.jelly.dao.OrderDao;
import com.mall.jelly.dao.SeckillDao;
import com.mall.jelly.entity.OrderEntity;
import com.mall.jelly.entity.SeckillEntity;
import com.mall.jelly.utils.RedisDataSoureceTransaction;
import com.mall.jelly.utils.SnowflakeIdWorker;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.io.IOException;
import java.util.Map;

/**
 * 库存消费者
 */
@Component
@Slf4j
public class StockConsumer {
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisDataSoureceTransaction manualTransaction;


	@RabbitListener(queues = SpikeCommodityConsumerConfig.QUEUE_NAME)
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
		String messageId = message.getMessageProperties().getMessageId();
		String msg = new String(message.getBody(), "UTF-8");
		log.info(">>>messageId:{},msg:{}", messageId, msg);
		JSONObject jsonObject = JSONObject.parseObject(msg);
		Long seckillId = jsonObject.getLong("seckillId");
		String phone = jsonObject.getString("phone");

		//1.判断是否已经秒杀到了
		Object obj = stringRedisTemplate.opsForHash().get(Prefix.SECKILL_STOCK_SECKILLID+seckillId, phone);
		if(obj != null) {
			log.info("不能重复秒杀");
			return;
		}

		// 2.获取秒杀id
		SeckillEntity seckillEntity = seckillDao.findBySeckillId(seckillId);
		if (seckillEntity == null) {
			log.info("seckillId:{},商品信息不存在!", seckillId);
			return;
		}
		if(seckillEntity.getInventory() <= 0) {
			return;
		}

		Long version = seckillEntity.getVersion();
		//基于乐观锁防止超卖   悲观锁方式 update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0
		int inventoryDeduction = seckillDao.inventoryDeduction(seckillId, version);
		if (!toDaoResult(inventoryDeduction)) {
			log.info(">>>seckillId:{}修改库存失败>>>>inventoryDeduction返回为{} 秒杀失败！", seckillId, inventoryDeduction);
			return;
		}

		TransactionStatus transactionStatus = null;
		try {
			// 3.添加秒杀订单
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setUserPhone(phone);
			orderEntity.setSeckillId(seckillId);
			orderEntity.setState(1L);
			int insertOrder = orderDao.insertOrder(orderEntity);
			if (!toDaoResult(insertOrder)) {
				manualTransaction.rollback(transactionStatus);
				return;
			}
			stringRedisTemplate.opsForHash().put(Prefix.SECKILL_STOCK_SECKILLID+seckillId, phone, new SnowflakeIdWorker(0, 0).nextId());
			manualTransaction.commit(transactionStatus);
			log.info(">>>修改库存成功seckillId:{}>>>>inventoryDeduction返回为{} 秒杀成功", seckillId, inventoryDeduction);
		} catch (Exception e) {
			try {
				manualTransaction.rollback(transactionStatus);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	// 调用数据库层判断
	public Boolean toDaoResult(int result) {
		return result > 0 ? true : false;
	}

}
