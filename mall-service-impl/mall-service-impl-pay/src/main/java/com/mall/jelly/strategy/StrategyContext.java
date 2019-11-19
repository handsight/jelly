package com.mall.jelly.strategy;


import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.response.PayInfoResponse;
import com.mall.jelly.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 策略上下文
 */
@Slf4j
public class StrategyContext {

    /**
     * 执行策略方法
     */
    public PayInfoResponse payInfo(TbOrder order) throws Throwable {
        Class clazz = PaymentMethodStrategySingleton.getInstance().getStrategy(order.getPaymentMethod());
        Object bean = SpringContextUtil.getBean(clazz);
        try {
            Method method = clazz.getDeclaredMethod("payInfo", TbOrder.class);
            method.setAccessible(true);
            return (PayInfoResponse) method.invoke(bean, order);
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }
}
