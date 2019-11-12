package com.mall.jelly.consumer;


import com.mall.jelly.consumer.config.SpikeCommodityConsumerConfig;
import com.mall.jelly.dao.OrderDao;
import com.mall.jelly.dao.SeckillDao;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 库存消费者
 */
@Component
@Slf4j
public class StockConsumer2 {

	@Autowired
	private StockConsumer1 stockConsumer1;


	@RabbitListener(queues = SpikeCommodityConsumerConfig.QUEUE_NAME)
	@Transactional(rollbackFor = Exception.class)
	public void process(Message message) throws IOException {
		stockConsumer1.processMessage(message);

	}
	// 调用数据库层判断
	public Boolean toDaoResult(int result) {
		return result > 0 ? true : false;
	}

}
