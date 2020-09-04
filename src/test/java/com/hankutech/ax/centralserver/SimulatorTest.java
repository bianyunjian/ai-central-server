package com.hankutech.ax.centralserver;

import com.hankutech.ax.centralserver.socket.NettyClientException;
import com.hankutech.ax.centralserver.socket.SocketClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXi
 */
@Slf4j
public class SimulatorTest {

    public static volatile boolean APP_AUTH_OK = false;
    public static volatile boolean APP_GARBAGE_OK = false;
    public static volatile boolean APP_DOOR_OPEN = false;
    public static volatile boolean APP_DOOR_CLOSE = false;

    @Test
    public void testRfidWetGarbageSuccess() throws NettyClientException, InterruptedException {
        SocketClient appClient = new SocketClient("127.0.0.1", 5001, new AppByteSocketClientInitializer());
        appClient.startConnect();

        SocketClient plcClient = new SocketClient("127.0.0.1", 5000, new PlcByteSocketClientInitializer());
        plcClient.startConnect();

        // 发送握手消息
        byte[] appHand = {3, 1, 0, 10, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appHand);
        byte[] plcHand = {2, 1, 0, 10, 1, 0, 0, 0, 0, 0};
        sendBytes("plc", plcClient, plcHand);

        TimeUnit.SECONDS.sleep(3);

        // app发送开始投递消息
        byte[] appStart = {3, 1, 0, 90, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appStart);

        TimeUnit.SECONDS.sleep(1);

        // app发送rfid认证消息
        byte[] appAuth = {3, 1, 0, 70, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appAuth);

        TimeUnit.SECONDS.sleep(1);

        // plc发送rfid通过
        byte[] plcRfidOk = {2, 1, 0, 40, 1, 0, 0, 0, 0, 0};
        sendBytes("plc", plcClient, plcRfidOk);

        TimeUnit.SECONDS.sleep(1);

        // app轮询发送RFID认证消息
        byte[] appAuthAgain = {3, 1, 0, 70, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appAuthAgain);

        TimeUnit.SECONDS.sleep(10);

        // app发送湿垃圾检测
        byte[] appGarbage = {3, 1, 0, 80, 2, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appGarbage);

        // fixme 手动上传湿垃圾结果

        TimeUnit.SECONDS.sleep(10);

        // app发送开门请求
        byte[] appPleaseOpen = {3, 1, 0, 20, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appPleaseOpen);

        TimeUnit.SECONDS.sleep(3);

        // plc发送已开门
        byte[] plcOpened = {2, 1, 0, 21, 1, 0, 0, 0, 0, 0};
        sendBytes("plc", plcClient, plcOpened);

        TimeUnit.SECONDS.sleep(5);

        // plc发送门已关闭
        byte[] plcClosed = {2, 1, 0, 30, 1, 0, 0, 0, 0, 0};
        sendBytes("plc", plcClient, plcClosed);

        TimeUnit.SECONDS.sleep(3);

        // app发送投递完成消息
        byte[] appStop = {3, 1, 0, 100, 1, 0, 0, 0, 0, 0};
        sendBytes("app", appClient, appStop);

        TimeUnit.SECONDS.sleep(5);



        appClient.close();
        plcClient.close();

    }



    private void sendBytes(String from, SocketClient client, byte[] bytes) {
        log.info("{} 发送消息：{}", from, bytes);
        ByteBuf bytebuf = Unpooled.buffer(10);
        bytebuf.writeBytes(bytes);
        client.sendData(bytebuf);
    }

    public static class AppByteSocketClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new IdleStateHandler(5, 5, 10));
            pipeline.addLast(new FixedLengthFrameDecoder(10));

            pipeline.addLast(new AppClientByteMessageHandler());
        }
    }


    @Slf4j
    public static class AppClientByteMessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ByteBuf buf = (ByteBuf) msg;

            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(0, bytes);
            log.info("app 接收数据：{}", bytes);

            super.channelRead(ctx, msg);
        }
    }


    public static class PlcByteSocketClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new IdleStateHandler(5, 5, 10));
            pipeline.addLast(new FixedLengthFrameDecoder(10));

            pipeline.addLast(new PlcClientByteMessageHandler());
        }
    }

    @Slf4j
    public static class PlcClientByteMessageHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ByteBuf buf = (ByteBuf) msg;

            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(0, bytes);
            log.info("plc 接收的数据：{}", bytes);

            super.channelRead(ctx, msg);
        }
    }

}
