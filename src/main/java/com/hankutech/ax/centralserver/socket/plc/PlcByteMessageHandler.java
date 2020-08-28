package com.hankutech.ax.centralserver.socket.plc;

import com.hankutech.ax.centralserver.bizmessage.AXMessageExchange;
import com.hankutech.ax.centralserver.socket.ByteConverter;
import com.hankutech.ax.message.protocol.plc.PlcDataConverter;
import com.hankutech.ax.message.protocol.plc.PlcRequest;
import com.hankutech.ax.message.protocol.plc.PlcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 字节数据处理器
 */
@Slf4j
public class PlcByteMessageHandler extends ChannelInboundHandlerAdapter {

    private static String TAG = "[PlcByteMessageHandler]";

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
            log.info(TAG + "接收的原始字节数据：{}", bytes);

            int[] convertedData = ByteConverter.fromByte(bytes);
            log.info(TAG + "转换后的数据：{}", convertedData);

            PlcResponse response = PlcResponse.defaultEmpty();
            PlcRequest request = PlcDataConverter.parseRequest(convertedData);
            if (request != null && request.isValid()) {
                log.info(TAG + "解析后的请求数据：{}", request.toString());
                response = AXMessageExchange.query(request);
                if (response != null) {
                    log.info(TAG + "查询到的响应数据：{}", response.toString());
                }
            }
            if (response == null) {
                response = PlcResponse.defaultEmpty();
            }
            if (response.isValid() == false) {
                log.error(TAG + "未能正确处理请求数据，request={},response={}", request, response);
            }
            int[] respData = PlcDataConverter.convertResponse(response);

            byte[] respByteData = ByteConverter.toByte(respData);

            log.info(TAG + "转换后的响应数据：{}", response.toString());
            log.info(TAG + "发送的响应数据：{}", respByteData);
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
