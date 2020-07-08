package com.hankutech.ax.centralserver.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SocketConst {

    public static int LISTENING_PORT = 0;
    public static int FIXED_LENGTH_FRAME = 0;

    @Autowired
    public void initSocketServerListeningPort(@Value("${app.socket.server.port}") int port, @Value("${app.socket.server.fixedLengthFrame}") int fixedLengthFrame) {
        LISTENING_PORT = port;
        FIXED_LENGTH_FRAME = fixedLengthFrame;
        System.out.println("socket server listen port=" + LISTENING_PORT);
        System.out.println("socket server fixedLengthFrame=" + FIXED_LENGTH_FRAME);
    }
}
