package com.mall.jelly.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.InputStream;


@Data
@Component
public class WeChatPayConfig implements WXPayConfig {

    private String appID;

    //商户号
    private String mchID;

    //生成签名的密钥
    private String key;

    private int httpConnectTimeoutMs;
    private int httpReadTimeoutMs;
    private String notifyurl;

    @Override
    public InputStream getCertStream() {
        return null;
    }
}
