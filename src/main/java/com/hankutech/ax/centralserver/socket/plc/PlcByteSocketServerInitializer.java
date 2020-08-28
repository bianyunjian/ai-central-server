package com.hankutech.ax.centralserver.socket.plc;

import com.hankutech.ax.centralserver.socket.BroadCastChannelHandler;
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
public class PlcByteSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private int fixedLengthFrame = 10;

    public PlcByteSocketServerInitializer(int fixedLengthFrame) {
        this.fixedLengthFrame = fixedLengthFrame;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(5, 5, 10));
        pipeline.addLast(new FixedLengthFrameDecoder(fixedLengthFrame));

        pipeline.addLast(new PlcByteMessageHandler());

        pipeline.addLast(new BroadCastChannelHandler());

    }
}
