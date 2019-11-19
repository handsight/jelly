package com.mall.jelly.strategy;


import com.mall.jelly.strategy.enums.PaymentTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付方式类型策略单例
 */
public class PaymentMethodStrategySingleton {

    private static Map<Integer,Class> strategyMap = new HashMap<>();
    static{
        for (PaymentTypeEnum t : PaymentTypeEnum.values()){
            strategyMap.put(t.getType(), t.getClazz());
        }
    }
    public Class getStrategy(Integer type){
        return strategyMap.get(type);
    }

    private static PaymentMethodStrategySingleton instance=null;
    public PaymentMethodStrategySingleton() {
    }
    public synchronized static PaymentMethodStrategySingleton getInstance() {
        if (instance == null) {
            instance = new PaymentMethodStrategySingleton();
        }
        return instance;
    }

}
