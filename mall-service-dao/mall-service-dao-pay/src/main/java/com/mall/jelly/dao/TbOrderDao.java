package com.mall.jelly.dao;

import com.mall.jelly.entity.TbOrder;
import org.springframework.stereotype.Component;

public interface TbOrderDao {
    TbOrder findByOrderNo(String orderNo);
}
