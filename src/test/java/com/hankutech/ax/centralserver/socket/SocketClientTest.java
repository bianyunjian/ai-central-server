package com.hankutech.ax.centralserver.socket;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * Socket 客户端测试
 *
 * @author ZhangXi
 */
@Slf4j
class SocketClientTest {

    @Test
    void testByteClient() throws Exception {
        SocketClient client = new SocketClient("localhost", 20001, new ByteSocketClientInitializer());
        client.startConnect();
        TimeUnit.SECONDS.sleep(5);
        log.info("准备发送数据...");
        client.sendData(new byte[]{1,1,1,1,1,0,1,5,1,1});
        client.sendData(new byte[]{1,9,1,1,1,0,1,1,1,1});
        client.sendData(new byte[]{1,1,1,1,1,0,99,1,1,1});
        TimeUnit.SECONDS.sleep(5);
        client.close();
    }



    void test() {

    }



}
