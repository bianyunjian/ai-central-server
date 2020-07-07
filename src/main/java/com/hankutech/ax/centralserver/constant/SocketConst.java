package com.hankutech.ax.centralserver.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SocketConst {

    public static int socketServerListeningPort = 0;

    @Autowired
    public void initSocketServerListeningPort(@Value("${app.socket.server.port}") int port) {
        socketServerListeningPort = port;
        System.out.println("socket server listen port=" + socketServerListeningPort);
    }
}
