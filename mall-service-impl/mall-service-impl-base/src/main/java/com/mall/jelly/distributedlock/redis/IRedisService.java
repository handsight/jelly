package com.mall.jelly.distributedlock.redis;

/**
 * 分布式锁redis 基于setNx方法
 */
public interface IRedisService {



     boolean lock(String key, long timeout);

     void unlock(String key);



}
