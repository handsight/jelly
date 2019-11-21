package com.mall.jelly.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Administrator
 * @Date: 2018/11/19 00:17
 * @Description:
 */
@Configuration
public class SocketConfig {

    @Value("${socketconfig.hostname}")
    private String hostname;

    @Value("${socketconfig.port}")
    private Integer port;

    @Value("${socketconfig.upgradeTimeout}")
    private Integer upgradeTimeout;


    @Value("${socketconfig.pingInterval}")
    private Integer pingInterval;

    @Value("${socketconfig.pingTimeout}")
    private Integer pingTimeout;



    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(upgradeTimeout);
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(pingInterval);
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(pingTimeout);

        //跨域
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        config.setOrigin(":*:");


        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

}
