package com.hankutech.ax.centralserver.socket;

import com.hankutech.ax.centralserver.socket.plc.PlcByteMessageHandler;
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

public class PlcSimulatorTest {

    @Test
    public void testStartLongRunner() throws NettyClientException, InterruptedException {

        SocketClient client = new SocketClient("127.0.0.1", 5000, new PlcByteSocketClientInitializer());
        client.startConnect();

        byte[] handshake = {2, 1, 0, 10, 1, 0, 0, 0, 0, 0};
        ByteBuf bytebuf = Unpooled.buffer(10);
        bytebuf.writeBytes(handshake);
        client.sendData(bytebuf);

        TimeUnit.SECONDS.sleep(3000);
        client.close();
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
            log.info("接收的原始字节数据：{}", bytes);
            super.channelRead(ctx, msg);
        }
    }

}
