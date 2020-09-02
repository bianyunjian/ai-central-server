package com.hankutech.ax.centralserver.socket;

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

public class AppSimulatorTest {

    @Test
    public void testStartWebGarbage() throws NettyClientException, InterruptedException {
        SocketClient client = new SocketClient("127.0.0.1", 5001, new AppByteSocketClientInitializer());
        client.startConnect();

        // 发送握手消息
        byte[] handshake = {3, 1, 0, 10, 1, 0, 0, 0, 0, 0};
        sendBytes(client, handshake);

        TimeUnit.SECONDS.sleep(300);
        client.close();

        // todo 执行测试流程



    }


    private void sendBytes(SocketClient client, byte[] bytes) {
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
            log.info("接收的原始字节数据：{}", bytes);

            super.channelRead(ctx, msg);
        }


    }








}
