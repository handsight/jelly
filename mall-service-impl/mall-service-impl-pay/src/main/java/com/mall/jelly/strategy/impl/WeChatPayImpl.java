package com.mall.jelly.strategy.impl;

import com.github.wxpay.sdk.WXPay;
import com.google.common.collect.Maps;
import com.mall.jelly.config.WeChatPayConfig;
import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.exception.MsgException;
import com.mall.jelly.response.PayInfoResponse;
import com.mall.jelly.strategy.PayManager;
import com.mall.jelly.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Map;

@Service
@Slf4j
public class WeChatPayImpl implements PayManager {

    @Autowired
    private WeChatPayConfig weChatPayConfig;

    @Override
    public PayInfoResponse payInfo(TbOrder order) {
        try {
            Map<String, String> param = Maps.newHashMap();
            ////商品描述
            param.put("body", "buy-challenge");
            //商户订单号
            param.put("out_trade_no", order.getOrderId());
            //总金额（分）
            String total_fee;
            if ("test".equals(SpringContextUtil.getActiveProfile()) || "dev".equals(SpringContextUtil.getActiveProfile()) || "prod".equals(SpringContextUtil.getActiveProfile())) {
                total_fee = "0.01";
            } else {
                total_fee = order.getPayMoney().toString();
            }
            param.put("total_fee", total_fee);

            //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
            param.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress()); //终端IP
            //回调地址
            param.put("notify_url", weChatPayConfig.getNotifyurl());
            //交易类型
            param.put("trade_type", "JSAPI");


            WXPay wxPay=new WXPay(weChatPayConfig);
            //完成下单
            Map<String, String> result = wxPay.unifiedOrder(param);

            isSucccess(result);

            PayInfoResponse payInfoResponse = new PayInfoResponse();
            payInfoResponse.setOrderNo(order.getOrderId());
            payInfoResponse.setType(1);
            payInfoResponse.setPaymentMethod(2);
            payInfoResponse.setPrepayId(result.get("prepay_id"));
            return payInfoResponse;
        } catch (Exception e) {
            log.error("微信下单创建预交易订单异常=====>",e);
            throw new MsgException(ResponseCodeEnum.PAY_ALI_ERROR);
        }
    }


    public void isSucccess(Map<String, String> result){
        String returnCode = result.get("return_code");
        if("FAIL".equals(returnCode)){
            log.error("微信下单通信失败，失败原因：{}",result.get("return_msg"));
            throw new MsgException(ResponseCodeEnum.PAY_WECAHT_PAY_FAIL);
        }
        String resultCode = result.get("result_code");
        if("FAIL".equals(resultCode)){
            log.error("微信下单业务失败，错误码：{}，错误原因：{}",result.get("err_code"),result.get("err_code_des"));
            throw new MsgException(ResponseCodeEnum.PAY_WECAHT_PAY_FAIL);
        }

    }

}
