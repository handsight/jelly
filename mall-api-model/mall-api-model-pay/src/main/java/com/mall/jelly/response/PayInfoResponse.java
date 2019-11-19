package com.mall.jelly.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("支付信息")
public class PayInfoResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("业务类型 （1商城支付,2其他支付）")
    private Integer type;

    @ApiModelProperty("支付方式：1支付宝，2微信,3其他")
    private Integer paymentMethod;

    @ApiModelProperty("阿里支付html")
    private  String aliPayInfoHtml;

    @ApiModelProperty("微信支付预支付交易会话标识")
    private String prepayId;

}
