package com.mall.jelly.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 秒杀商品生产者队列
 */
@Configuration
public class SpikeCommodityProducerConfig {

    // 交换空间名称
    public static final String EXCHANGE = "com.komi.kp.jms.exchange.spike";
    // 设置路由key
    public static final String ROUTINGKEY = "com.komi.kp.jms.routingkey.spike";
    // 队列名称
    public static final String QUEUE_NAME = "com.komi.kp.jms.queue.spike";

    @Bean("spikeQueue")
    public Queue queue() { // 要穿件的队列信息
        return new Queue(QUEUE_NAME);
    }

    @Bean("spikeExchange")
    public DirectExchange getDirectExchange() { // 使用直连的模式
        return new DirectExchange(EXCHANGE, true, true);
    }

    @Bean("spikeBinding")
    public Binding bindingExchangeQueue() {
        return BindingBuilder.bind(queue()).to(getDirectExchange()).with(ROUTINGKEY) ;
    }
}
