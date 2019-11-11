package com.mall.jelly.distributedlock.redis.impl;

import com.mall.jelly.distributedlock.redis.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {
    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    //80s 有慢sql，超时时间设置长一点
    private static final long DEFAULT_EXPIRE = 80;
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Override
    public boolean lock(String key, long timeout) {
        String lockKey = generateLockKey(key);
        long start = System.currentTimeMillis();
        long expires = System.currentTimeMillis() + timeout + 1;
        String expiresStr = String.valueOf(expires);
        try {
            // //当前时间小于获取锁的超时时间就一直循环获取锁
            while ((System.currentTimeMillis() - start) < timeout) {
                RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
                //setNX成功返回1；失败返回0
                if (connection.setNX(lockKey.getBytes(), expiresStr.getBytes())) {
                    //暂设置为80s过期，防止异常中断锁未释放
                    redisTemplate.expire(lockKey, DEFAULT_EXPIRE, TimeUnit.SECONDS);
                    if (logger.isDebugEnabled()) {
                        logger.debug("add RedisLock[" + key + "].");
                    }
                    connection.close();
                    return true;
                }
                connection.close();
                TimeUnit.SECONDS.sleep(3);
            }
            return false;
        } catch (Exception e) {
            unlock(lockKey);
            return false;
        }
    }
    @Override
    public void unlock(String key) {
        String lockKey = generateLockKey(key);
        if (logger.isDebugEnabled()) {
            logger.debug("release RedisLock[" + lockKey + "].");
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.del(lockKey.getBytes());
        connection.close();
    }

    private String generateLockKey(String key) {
        return String.format("LOCK:%s", key);
    }


}
