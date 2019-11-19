package com.mall.jelly.strategy.enums;


import com.mall.jelly.strategy.impl.AliBaBaPayImpl;
import com.mall.jelly.strategy.impl.WeChatPayImpl;

/**
 * 支付类型枚举
 */
public enum PaymentTypeEnum {

    //支付方式枚举
    //1支付宝支付,
    ALI_PAY(1, AliBaBaPayImpl.class),
    WECHAT_PAY(2, WeChatPayImpl.class),
    ;
    private Integer type;
    private Class clazz;

    PaymentTypeEnum(Integer type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }}
