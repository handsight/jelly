package com.mall.jelly.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 骑着蜗牛去旅行 on 2018/8/29.
 */
@Component
@Data
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2018010501606757";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVGSipSLvnVB/lyhoBA52MgUDdZBz4sGLA/d6zSS7HrSetZZ07RgiuyK0U3n2zwBcZSc18cV035ZYDYdtlzwURW9Xw2T6EcuuxivswdvxTnBoK2h3sINmk6ISHsuU5imHoBRUV7IuB6dn9uUc1gG4iy4SC4RyQszetaUb2iUPDqWFnyLUougTYzK10FsN4pRmlSM45ElLJuarEiU2lIugTUAMa1jj+kVnhle/0wmprj2KBYWKp+1fa3k1HLNUmF6ZVNTAbYZFe93DPGAcQuhMAzdw367UmLKx4jHcFPojDtEhywf5DAhiuTZshc3/IonIuD3QEHDdWtGwuPRX5HRcTAgMBAAECggEAejNeojCc4WKPqjTUz1P5QvmFAm9VV3ap2ee5KVSn5LxEstd/4N7rquWLBnm8qSCdTfqxUndPocqoAymnOnBMhQex92720HOoPaHdcsFy2cwthAt1Tt01xPVV+ipjuZ9z00inGYsAZIAYTN4YQP98RURs3SBH20koLxcENHgYdDoTVchY/YRwBqca8bn1k3sIlf9Yvh7TlLnp/IxUjxJmPdiom2JP2wf3GNLBUQALg9XHLAOtP7LsqBsPJZe4IUAVx1LLpAKrmoeM88tVrFI7hYGbsIYjD7r2Yrk6hR+QbikwTuJ9CVytsvt0ohnuhZ/i4M4nE0lpO9CpUJ2vELi3gQKBgQDYPvaSF6wCIZcifh3uwgNQPPxCA1cHDhRMQe02lRRCzB5ixMUdUXie7VhYSub3e+529F65V1z6psK6sfqTOfH17O94OZUDb5eaZVyj9cEiQdGw68Nj4D/HNtn72Gu2+cJIMMEBzdpM6Vh1SgyxYA+mrXqgr8UQ7wHXQM59kadXcwKBgQCwghH1lxsBPhcx6MQTJoPpysiaSohNHgStCEZkdxL59XtHFCOuFKv6v2y3wkCoLDK07JLnEuMWfZJpQjT3RePAZ58V3QWkqb9UzOuVUkb/mRvxaMOE13p2t0aci3m8YaEZ2c5fLlCzvatwX7wOpYJjVWprGZ8HMQqzCto45sUZ4QKBgGDB63n69ZAOj51zaNlIHktsvkKbU/loJ1TnushNdD9fLclr2fcAWURjVsBK44WhAlZfiXLM0GGm9EKtGD2AE+/CEHRu72oWKHOBHy6N69j0MmtDUq4BzQfKV0cE002P+4pppjK0dRUs49O3Sm6jWN1vigsUGs005FH4QWyWG5ZZAoGAcayjIlaZlrhFOehddrPkWso1XWDI3VmKy2E77HHfb8mQBlvLCs7XX5quyr245FXqpiiCB31bcN2YUF/KOsHxBrX1d6ArhTupNFTxF+6wOOaDX3sICGK5xQEN4b3VvePmW/6w8Q2j/c4WPj35oDkpew3EhosupvQHnv1LQ0AkCiECgYB6+klwbkeSYD8pDJolL7bQNSOw+sI07Xw9UX1eDu/g4XjRpUA3GaIlrwgDN8DUnS85O6gBAqTrosULn+smW/LGbPsXL1TcRSLVDd1wtAqdToB2D+oROsidZSOxO09ucdAsJOgEfas0a4tysKt85HaJVGeMWYW/IolfCWkqusP1yQ==";

    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgAsybxeQ4iRAHAk4YeUi7p78t6o0plOsnHHTUS2kAiW9iXH/uJNYqLpXMrCPdoCEuxfG3U8OBXmrhpjANwBO+wcuBAkC2Yx9XpQ1hGP7ebzQyYpeVtRZuOV9eVdQafpJI1euCz+fg6suX5EOnb03dRiN2GsjebXFqKXycclBKeRSelObZhLOMw2jzq95XACrgKWs8clqXiEEvM1ewin3R7QuGK+NRaLmkkcFDTMdWu3owSJc9t117RTk7fJGpbK46nLgWQUe8CTjQqeZ89tpYrlfnSvJtWB42jEMq9Qg21IfzWD6ZiLe+F7UCS8RNzm91ZtgwaDUzB51ruLtGsUGowIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";


    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url;
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url;


    @Value("${alipayconfig.notify_url}")
    public  String notifyUrl;

    @Value("${alipayconfig.return_url}")
    public  String returnUrl;

    @PostConstruct
    public void setURL(){
        notify_url= this.notifyUrl;
        return_url= this.returnUrl;
    }


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord
     *            要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
