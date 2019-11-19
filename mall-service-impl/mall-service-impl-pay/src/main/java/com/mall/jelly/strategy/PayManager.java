package com.mall.jelly.strategy;

import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.response.PayInfoResponse;

public interface PayManager {

    PayInfoResponse payInfo(TbOrder order);
}
