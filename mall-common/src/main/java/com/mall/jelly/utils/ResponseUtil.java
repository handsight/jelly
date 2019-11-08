package com.mall.jelly.utils;


import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class ResponseUtil {

    /**
     * 返回响应
     *
     * @return
     * @throws Exception
     */
    public static void send(String responseString, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = null;
        try {
            response.setContentLength(responseString.getBytes("utf-8").length);
            pw = response.getWriter();
            pw.write(responseString);
            pw.flush();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }


    
    
    
}
