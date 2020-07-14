package com.hankutech.ax.centralserver.socket;

import com.hankutech.ax.centralserver.biz.protocol.AXDataConverter;
import com.hankutech.ax.centralserver.biz.data.AXDataManager;
import com.hankutech.ax.centralserver.biz.protocol.AXRequest;
import com.hankutech.ax.centralserver.biz.protocol.AXResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 字节数据处理器
 *
 * @author ZhangXi
 */
@Slf4j
public class ByteMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;

            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(0, bytes);
            log.info("接收的原始字节数据：{}", bytes);

            int[] convertedData = ByteConverter.fromByte(bytes);
            log.info("转换后的数据：{}", convertedData);

            AXResponse response = AXResponse.DefaultEmpty();
            AXRequest request = AXDataConverter.parseRequest(convertedData);
            if (request != null && request.isValid()) {
                log.info("解析后的请求数据：{}", request.toString());
                response = AXDataManager.query(request);
                if (response != null) {
                    log.info("查询到的响应数据：{}", response.toString());
                }
            }
            if (response == null) {
                response = AXResponse.DefaultEmpty();
            }
            if (response.isValid() == false) {
                log.error("未能正确处理请求数据，request={},response={}", request, response);
            }
            int[] respData = AXDataConverter.convertResponse(response);
            byte[] respByteData = ByteConverter.toByte(respData);
            log.info("转换后的响应数据：{}", response.toString());

            ByteBuf responseByteBuf = Unpooled.buffer(respByteData.length);
            responseByteBuf.writeBytes(respByteData);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
