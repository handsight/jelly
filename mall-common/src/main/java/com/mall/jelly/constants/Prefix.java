package com.mall.jelly.constants;

/**
 * mallJelly
 */
public interface Prefix {

    String USER_BASE_CONFIG = "user:config:";
    String USER_SESSION_TOKEN = USER_BASE_CONFIG + "token:";
    String USER_SESSION_NO_TOKEN=USER_BASE_CONFIG+"notoken:";


    //秒杀配置
    String SECKILL_BASE_CONFIG = "seckill:config:";
    String SECKILL_STOCK = SECKILL_BASE_CONFIG + "stock:";
    String SECKILL_STOCK_SECKILLID = SECKILL_BASE_CONFIG + "seckillId:";
}
