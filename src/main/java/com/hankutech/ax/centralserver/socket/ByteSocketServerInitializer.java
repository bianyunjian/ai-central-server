package com.hankutech.ax.centralserver.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Byte Socket服务端初始化配置
 *
 * @author ZhangXi
 */
public class ByteSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private int fixedLengthFrame = 10;

    public ByteSocketServerInitializer(int fixedLengthFrame) {
        this.fixedLengthFrame = fixedLengthFrame;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(5, 5, 10));
        pipeline.addLast(new FixedLengthFrameDecoder(fixedLengthFrame));

        pipeline.addLast(new ByteMessageHandler());

        pipeline.addLast(new BroadCastChannelHandler());

    }
}
