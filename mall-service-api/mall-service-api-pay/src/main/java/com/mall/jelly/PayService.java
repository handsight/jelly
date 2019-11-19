package com.mall.jelly;



import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.request.PaymentReq;
import com.mall.jelly.response.PayInfoResponse;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Api(tags = "支付服务接口")
public interface PayService {

    /**
     * 根据业务类型,进行支付请求
     * @param req
     * @return
     */
    @PostMapping("/api/pay/getPayInfo")
    BaseResponse<PayInfoResponse> payInfo(@RequestBody PaymentReq req) throws Throwable;

}
