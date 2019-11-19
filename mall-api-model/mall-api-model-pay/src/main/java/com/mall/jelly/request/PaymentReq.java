package com.mall.jelly.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("支付请求")
public class PaymentReq {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("业务类型 （1商城支付,2其他支付）")
    private Integer type;

    @ApiModelProperty(value = "userId",hidden = true)
    private Long userId;

}
