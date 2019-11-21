package com.mall.jelly.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Auther: Administrator
 * @Date: 2018/11/19 00:19
 * @Description:
 */

@Component
@Order(value=1)
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {


    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... strings) throws Exception {
        server.start();
        log.info("socket.io启动成功！........................................");
    }
}
