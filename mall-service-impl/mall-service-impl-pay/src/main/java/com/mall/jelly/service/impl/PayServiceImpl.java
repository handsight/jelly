package com.mall.jelly.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.PayService;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.exception.MsgException;
import com.mall.jelly.request.PaymentReq;
import com.mall.jelly.response.PayInfoResponse;
import com.mall.jelly.service.PaymentType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayServiceImpl extends BaseApiService<JSONObject> implements PayService {


    @Autowired
    private PaymentType paymentType;

    @Autowired
    private MallPayServiceImpl mallPayServiceImpl;

    @Override
    public BaseResponse<PayInfoResponse> payInfo(PaymentReq req) throws Throwable {
        if(req.getType()==null|| StringUtils.isEmpty(req.getOrderNo())){
            throw new MsgException(ResponseCodeEnum.PARAMETER_ERROR);
        }
        //todo 后期改为策略模式
        if(req.getType()==1){
            //挑战支付
            paymentType= mallPayServiceImpl;
        }else {
            throw new MsgException(ResponseCodeEnum.REQUEST_ILLEGAL);
        }
        PayInfoResponse infoResponse=paymentType.payInfo( req);
        return new BaseResponse(infoResponse);
    }
}
