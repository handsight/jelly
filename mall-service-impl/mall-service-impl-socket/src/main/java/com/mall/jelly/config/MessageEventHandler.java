package com.mall.jelly.config;


import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.mall.jelly.constants.Prefix;
import com.mall.jelly.enums.ResponseCodeEnum;
import com.mall.jelly.utils.Global;
import com.mall.jelly.utils.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Auther: Administrator
 * @Date: 2018/11/19 00:22
 * @Description:
 */
@Component
@Slf4j
public class MessageEventHandler {

    public static SocketIOServer socketIoServer;

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        socketIoServer = server;
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.secretKey.api_token_secret}")
    private String app_token_secret;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if (StringUtils.isNotBlank(token)) {
            Map<String, Object> resultMap = Jwt.validToken(token, app_token_secret);
            ResponseCodeEnum responseCodeEnum = (ResponseCodeEnum) resultMap.get("state");
            if (responseCodeEnum == null || responseCodeEnum != ResponseCodeEnum.SUCCESS) {
                log.info("客户端:{},token错误:{}", client.getRemoteAddress(), token);
                return;
            }
            Map<String, Long> map = (Map<String, Long>) resultMap.get("data");
            Long userId = map.get("userId");
            Global.set("userId", userId);
            stringRedisTemplate.opsForHash().put(Prefix.USER_SESSION_TOKEN, userId.toString(), client.getSessionId().toString());
        } else {
            stringRedisTemplate.opsForHash().put(Prefix.USER_SESSION_TOKEN, client.getSessionId().toString(), client.getSessionId().toString());
        }
        Global.set("locale", client.getHandshakeData().getHttpHeaders().get("Accept-Language"));
        Set<String> keys = stringRedisTemplate.keys("user:session:*");
        log.info("客户端:{}已连接,ip为：{},socket连接总数量为{}", client.getSessionId(), client.getRemoteAddress(), (keys.size()));
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        stringRedisTemplate.opsForHash().delete(Prefix.USER_SESSION_TOKEN, client.getSessionId().toString());
        stringRedisTemplate.opsForHash().delete(Prefix.USER_SESSION_NO_TOKEN, client.getSessionId().toString());
        Set<String> keys = stringRedisTemplate.keys("user:session:*");
        log.info("客户端:{}断开连接,ip为：{},socket连接总数量为{}", client.getSessionId(), client.getRemoteAddress(), (keys.size()));
    }

    /**
     * 向客户端推送购买
     * 参数1：事件名
     * 参数2：数据
     */
    public void sendMessageAll(String name, Object data) {
        Map<Object, Object> tokenList = stringRedisTemplate.opsForHash().entries(Prefix.USER_SESSION_TOKEN);

        if(tokenList!=null){
            tokenList.values().stream().forEach(v -> {
                socketIoServer.getClient(JSONObject.parseObject(v.toString(),UUID.class)).sendEvent(name, JSONObject.toJSONString(data));




                log.info("socket推送购买消息{}到有token客户端ip{}", JSONObject.toJSON(data), socketIoServer.getClient(JSONObject.parseObject(v.toString(), UUID.class)).getRemoteAddress());
            });
        }
        Map<Object, Object> noTokenList = stringRedisTemplate.opsForHash().entries(Prefix.USER_SESSION_NO_TOKEN);
        if(noTokenList!=null){
            noTokenList.values().stream().forEach(v -> {
                socketIoServer.getClient(JSONObject.parseObject(v.toString(),UUID.class)).sendEvent(name, JSONObject.toJSONString(data));
                log.info("socket推送购买消息{}到无token客户端ip{}:", JSONObject.toJSON(data), socketIoServer.getClient( JSONObject.parseObject(v.toString(), UUID.class)).getRemoteAddress());
            });
        }
    }


    @OnEvent(value = "mallWatch") //为事件名称
    public void onEvent(SocketIOClient client,AckRequest request, String event) {
//        WatchEvent watch = JsonUtil.jsonToBean(event, WatchEvent.class);
//        log.info("客户端ip{},mallWatch携带的token为{}", client.getRemoteAddress(), watch.getToken());
//
//        List<WatchVo> watchs = mOrderDetailDao.findMallWatch(1);
//        String json = JsonUtil.objectToJson(watchs);
//        socketIoServer.getClient(client.getSessionId()).sendEvent("mallWatch", json);
//        log.info("socket接收到前端mallWatch消息后，推送数据{}到客户端{}", JsonUtil.objectToJson(watchs), client.getRemoteAddress());

    }

}
