package com.mall.jelly.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;


/**
 * redis和spring事务封装
 */
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisDataSoureceTransaction {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 开始事务 采用默认传播行为
     *
     * @return
     */
    public TransactionStatus begin() {
        // 1.开启数据库的事务 事务传播行为
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        // 2.开启redis事务
        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.multi();
        return transaction;
    }

    /**
     * 提交事务
     *
     * @throws Exception
     */
    public void commit(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        // 支持Redis与数据库事务同时提交
        dataSourceTransactionManager.commit(transactionStatus);
        //stringRedisTemplate.exec();
    }

    /**
     * 回滚事务
     *
     */
    public void rollback(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        // 1.回滚数据库事务 redis事务和数据库的事务同时回滚
        dataSourceTransactionManager.rollback(transactionStatus);
        // // 2.回滚redis事务
        // stringRedisTemplate.discard();
    }

}
