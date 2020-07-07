package com.hankutech.ax.centralserver.socket;

import cn.hutool.core.lang.Assert;
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
        SocketClient client = new SocketClient("localhost", 4000, new ByteSocketClientInitializer());
        client.startConnect();
        TimeUnit.SECONDS.sleep(5);
        log.info("准备发送数据...");

        byte[] originData = new byte[]{5, 0, 1, 0, 0, 0, 0, 0, 1, 1};
        client.sendData(originData);

        TimeUnit.SECONDS.sleep(5);
        client.close();
    }

    @Test
    void testConvert() {
        int[] intData = new int[]{0, 1, 127, 128, 254, 255};
        byte[] byteData = ByteConverter.toByte(intData);
        byte[] expectByteData = new byte[]{0, 1, 127, -128, -2, -1};

        for (int i = 0; i < byteData.length; i++) {
            System.out.println(byteData[i]);
            Assert.isTrue(byteData[i] == expectByteData[i]);
        }


        int[] intData2 = ByteConverter.fromByte(byteData);
        for (int i = 0; i < intData2.length; i++) {
            System.out.println(intData2[i]);
            Assert.isTrue(intData2[i] == intData[i]);
        }
    }


}
