package com.hankutech.ax.centralserver.socket;

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
        SocketServer server = new SocketServer(20001, new ByteSocketServerInitializer());
        server.start();
        Assertions.assertTrue(NettyTool.isLocalPortUsing(20001));
        TimeUnit.SECONDS.sleep(500);
        server.shutdown();
    }




}
