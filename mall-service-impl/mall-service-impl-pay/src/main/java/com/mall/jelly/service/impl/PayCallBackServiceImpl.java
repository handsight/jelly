package com.mall.jelly.service.impl;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.mall.jelly.config.AlipayConfig;
import com.mall.jelly.config.WeChatPayConfig;
import com.mall.jelly.dao.TbOrderDao;
import com.mall.jelly.entity.TbOrder;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.exception.MsgException;
import com.mall.jelly.service.PayCallBackService;
import com.mall.jelly.strategy.impl.WeChatPayImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;


@Service
@Slf4j
public class PayCallBackServiceImpl implements PayCallBackService {
    @Autowired
    private TbOrderDao tbOrderDao;

    @Autowired
    private WeChatPayImpl weChatPayImpl;


    @Autowired
    private WeChatPayConfig weChatPayConfig;

    @Override
    public String alipayAsynCallBack(HttpServletResponse response, Map<String, String> params) {
        try {
            log.info("####异步回调开始####params:{}", params);
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,
                    AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            if (!signVerified) {
                return "fail";
            }
            String orderNo = params.get("out_trade_no");
            //1查询支付信息
            TbOrder order = tbOrderDao.findByOrderNo(orderNo);
            if (order == null) {
                throw new MsgException(ResponseCodeEnum.PAY_ORDER_NOT_EXIST);
            }
            if (order == null) {
                log.info("订单{}不存在", orderNo);
                return "fail";
            }

            // 2.支付宝交易号,交易状态
            String trade_no = params.get("trade_no");
            String trade_status = params.get("trade_status");


            if (order.getPayStatus() == 1) {
                log.info("订单 {} 已经支付", orderNo);
                return "success";
            }
            //4支付成功修改状态产生支付日志
            if (trade_status.equals("TRADE_SUCCESS")) {

                //4.1修改订单状态
//                userTransferService.updateMasterIsPay(master, params);
                //4.2发送消息处理
                //发送支付成功处理消息
//                rabbitTemplate.convertAndSend(MallOrderProducerConfig.EXCHANGE, MallOrderProducerConfig.ROUTINGKEY, JsonUtil.objectToJson(master.getOrderNo()));
            }
            return "success";
        } catch (Exception e) {
            log.error("####支付宝异步通知出现异常,ERROR:", e);
            return "fail";
        } finally {
            log.info("#####支付宝异步通知synCallBack#####结束,params:{}", params);
        }
    }

    @Override
    public void wechatAsynCallBack(Map<String, String> result) {
       //数据校验
        weChatPayImpl.isSucccess(result);
        //校验签名
        isValidSign(result);

        //商户订单号
        String ordersSn = result.get("out_trade_no");
        //实际支付的订单金额:单位 分
        String amountpaid = result.get("total_fee");
        if(StringUtils.isEmpty(amountpaid)||StringUtils.isEmpty(ordersSn)){
            throw new MsgException(ResponseCodeEnum.PAY_INVALID_ORDER_PARAM);
        }
        //将分转换成元-实际支付金额:元
        BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);

        //如果订单金额和传过来的金额不等 抛出金额不对
        //修改订单状态


    }

    private  void isValidSign(Map<String, String> data){
        try {
            String sign = data.get("sign");

            //重新生成签名和传过来的签名比较
            String sign1 = WXPayUtil.generateSignature(data, weChatPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            String sign2 = WXPayUtil.generateSignature(data, weChatPayConfig.getKey(), WXPayConstants.SignType.MD5);
            if(!StringUtils.equals(sign,sign1)&&!StringUtils.equals(sign,sign2)){
                throw new MsgException(ResponseCodeEnum.PAY_INVALID_SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new MsgException(ResponseCodeEnum.PAY_INVALID_SIGN_ERROR);
        }

    }


}
