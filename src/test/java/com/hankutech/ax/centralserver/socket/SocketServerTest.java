package com.hankutech.ax.centralserver.socket;

import com.hankutech.ax.centralserver.socket.plc.PlcByteSocketServerInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * Socket 服务端测试
 *
 * @author ZhangXi
 */
class SocketServerTest {


    @Test
    void testByteServer() throws Exception {
        SocketServer server = new SocketServer(4000, new PlcByteSocketServerInitializer(10));
        server.start();
        Assertions.assertTrue(NettyTool.isLocalPortUsing(4000));
        TimeUnit.SECONDS.sleep(500);
        server.shutdown();

    }
}
