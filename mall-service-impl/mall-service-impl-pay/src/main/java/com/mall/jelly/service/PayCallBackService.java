package com.mall.jelly.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface PayCallBackService {
    String alipayAsynCallBack(HttpServletResponse response, Map<String, String> params);

    void wechatAsynCallBack(Map<String, String> result);
}
