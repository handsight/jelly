package com.mall.jelly.service.impl;

import com.mall.jelly.dao.TbOrderDao;
import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.exception.MsgException;
import com.mall.jelly.request.PaymentReq;
import com.mall.jelly.response.PayInfoResponse;
import com.mall.jelly.service.PaymentType;
import com.mall.jelly.strategy.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallPayServiceImpl implements PaymentType {

    @Autowired
    private TbOrderDao tbOrderDao;

    @Override
    public PayInfoResponse payInfo(PaymentReq req) throws Throwable {

        TbOrder order = tbOrderDao.findByOrderNo(req.getOrderNo());
        if (order == null) {
            throw new MsgException(ResponseCodeEnum.PAY_ORDER_NOT_EXIST);
        }
        if (order.getOrderStatus() == 1) {
            throw new MsgException(ResponseCodeEnum.PAY_SUCCESS);
        }
        //使用策略模式动态调用
        StrategyContext StrategyContext = new StrategyContext();
        return StrategyContext.payInfo(order);
    }
}
