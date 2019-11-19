package com.mall.jelly.callback;

import com.google.common.collect.Maps;
import com.mall.jelly.service.PayCallBackService;
import com.mall.jelly.utils.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Api("回调")
@Slf4j
@RestController
public class CallBackServiceImpl {

    @Autowired
    private PayCallBackService payCallBackService;

    /**
     * 支付宝异步回调地址
     *
     */
    @RequestMapping("/api/callBack/alipayAsynCallBack")
    public void alipayAsynCallBack(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }
        String callBack =  payCallBackService.alipayAsynCallBack(response, params);
        log.info("商城订单号{}异步回调字符串{}",params.get("out_trade_no"),callBack);
        ResponseUtil.send(callBack, response);
    }

    /**
     * 微信异步回调地址
     *
     */
    @RequestMapping(value = "/api/callBack/wechatAsynCallBack",produces = "application/xml")
    public Map<String,String> wechatAsynCallBack(@RequestBody Map<String,String>result) {

        payCallBackService.wechatAsynCallBack(result);
        HashMap<String, String> msg = Maps.newHashMap();
        msg.put("return_code","SUCCESS");
        msg.put("return_msg","OK");
        return msg;
    }

}
