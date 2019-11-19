package com.mall.jelly.service;

import com.mall.jelly.request.PaymentReq;
import com.mall.jelly.response.PayInfoResponse;

public interface PaymentType {
    PayInfoResponse payInfo(PaymentReq req) throws Throwable;
}
