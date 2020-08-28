package com.hankutech.ax.centralserver.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SocketConst {

    public static int LISTENING_PORT_PLC = 0;
    public static int LISTENING_PORT_APP = 0;
    public static int FIXED_LENGTH_FRAME = 0;

    @Autowired
    public void initSocketServerListeningPort(@Value("${app.socket.server.port}") int port, @Value("${app.socket.server.fixed-length-frame}")

            int fixedLengthFrame) {
        LISTENING_PORT_PLC = port;
        LISTENING_PORT_APP = LISTENING_PORT_PLC + 1;
        FIXED_LENGTH_FRAME = fixedLengthFrame;
        System.out.println("socket server for PLC listen port=" + LISTENING_PORT_PLC);
        System.out.println("socket server for App listen port=" + LISTENING_PORT_APP);
        System.out.println("socket server fixedLengthFrame=" + FIXED_LENGTH_FRAME);
    }
}
