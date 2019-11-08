package com.mall.jelly.utils;

import com.mall.jelly.enums.ResponseCodeEnum;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Jwt {

    //过期时间7天
    public static final Long TIMEOUT = 1000 * 60 * 60 * 24 * 7L;
    //过期时间9小时
    public static final Long TIMEOUT_MANAGE = 1000 * 60 * 60 * 9L;

    /**
     * 初始化head部分的数据为
     * {
     * "alg":"HS256",
     * "type":"JWT"
     * }
     */
    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

    /**
     * 生成token，该方法只在用户登录成功后调用
     *
     * @param payload 可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return token字符串, 若失败则返回null
     */
    public static String createToken(Map<String, Object> payload, String secret) {
        String tokenString = null;
        // 创建一个 JWS object
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(secret));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("签名失败:" + e.getMessage());
            e.printStackTrace();
        }
        return tokenString;
    }


    /**
     * 校验token是否合法，返回Map集合,集合中主要包含    state状态码   data鉴权成功后从token中提取的数据
     * 该方法在过滤器中调用，每次请求API时都校验
     *
     * @param token
     * @return Map<String   ,       Object>
     */
    public static Map<String, Object> validToken(String token, String secret) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(secret);

            if (jwsObject.verify(verifier)) {
                // token校验成功（此时没有校验是否过期）
                JSONObject jsonOBj = payload.toJSONObject();
                resultMap.put("state", ResponseCodeEnum.SUCCESS);
                resultMap.put("data", jsonOBj);
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", ResponseCodeEnum.SIGNATURE_TIMEOUT);
                    }
                }
            } else {
                // 校验失败
                resultMap.put("state", ResponseCodeEnum.SIGNATURE_ERROR);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            // token格式不合法导致的异常
            resultMap.clear();
            resultMap.put("state", ResponseCodeEnum.SIGNATURE_ERROR);
        }
        return resultMap;
    }

    public static void main(String[] args) {

        String token = "";
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("userId", "88881");
        payload.put("phone", "100000");//用户id
        //payload.put("iat", date.getTime());//生成时间
        payload.put("ext", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);//过期时间24*7小时
        String secret = "!@#@!%$^$%&^%*^*3d990asdasyuosadsd67df11fff88d";
        token = Jwt.createToken(payload, secret);

        System.out.println("===>app token: Bearer " + token);
//        System.out.println(validToken("eyJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MzIzMjgzMjY5NzgsInVzZXJJZCI6MTAwMDA3fQ.M_eGM_Chv1uu46rmPca_vEUkzhQDpAQZOfrVXzsEELc",secret).get("state"));
    }

}
