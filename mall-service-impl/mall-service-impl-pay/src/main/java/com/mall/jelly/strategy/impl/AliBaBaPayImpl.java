package com.mall.jelly.strategy.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.mall.jelly.config.AlipayConfig;
import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.exception.MsgException;
import com.mall.jelly.response.PayInfoResponse;
import com.mall.jelly.strategy.PayManager;
import com.mall.jelly.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 *
 * @author 骑着蜗牛去旅行
 * @date 2018/8/29
 */
@Service
@Slf4j
public class AliBaBaPayImpl implements PayManager {

    @Override
    public PayInfoResponse payInfo(TbOrder order) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        //设置请求参数异步通知地址 同步地址
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderId();
        //付款金额，必填  最好分为单位,支付宝以元为单位

        String total_amount;
        if ("test".equals(SpringContextUtil.getActiveProfile()) || "dev".equals(SpringContextUtil.getActiveProfile()) || "prod".equals(SpringContextUtil.getActiveProfile())) {
            total_amount = "0.01";
        } else {
            total_amount = order.getPayMoney().toString();
        }

        //订单名称，必填
        String subject = "buy-challenge";
        //商品描述，可空
        String body = "consumption";
        // 超时时间 可空
        String timeout_express = "2m";

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setBody(body);
        model.setTotalAmount(total_amount);
        model.setProductCode(product_code);
        alipayRequest.setBizModel(model);
        model.setTimeoutExpress(timeout_express);
        //请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            PayInfoResponse payInfoResponse = new PayInfoResponse();
            payInfoResponse.setAliPayInfoHtml(result);
            payInfoResponse.setOrderNo(order.getOrderId());
            payInfoResponse.setType(0);
            payInfoResponse.setPaymentMethod(2);
            return payInfoResponse;
        } catch (AlipayApiException e) {
            log.info("请求支付宝客户端异常=====>,", e);
            throw new MsgException(ResponseCodeEnum.PAY_ALI_ERROR);
        }
    }
}
