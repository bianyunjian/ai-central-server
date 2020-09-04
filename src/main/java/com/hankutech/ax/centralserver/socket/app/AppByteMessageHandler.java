package com.hankutech.ax.centralserver.socket.app;

import com.hankutech.ax.centralserver.bizmessage.AXMessageExchange;
import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.ByteConverter;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.message.code.AIGarbageResultType;
import com.hankutech.ax.message.protocol.MessageSource;
import com.hankutech.ax.message.protocol.app.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 字节数据处理器
 */
@Slf4j
public class AppByteMessageHandler extends ChannelInboundHandlerAdapter {

    private static String TAG = "[AppByteMessageHandler]";

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

            AppMessage response = null;
            AppMessage request = AppDataConverter.parse(convertedData);
            if (request.getMessageSource() != MessageSource.APP) {
                log.error(TAG + "消息头不正确，丢弃该消息", request);
                return;
            }
            if (request != null && request.isValid()) {
                log.info(TAG + "解析后的请求数据：{}", request.toString());
                response = handleRequest(ctx, request);
            } else {
                log.error(TAG + "未能正确处理请求数据，request={}", request);
            }
            if (response == null) {
                log.warn(TAG + "没有响应数据");
                return;
            }
            if (response.isValid() == false) {
                log.error(TAG + "未能获取到正确的响应数据数据，response={}", response);
                return;
            }
            int[] respData = AppDataConverter.convert(response);

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


    private AppMessage handleRequest(ChannelHandlerContext ctx, AppMessage request) {
        AppMessage response = null;
        AppMessageType messageType = request.getMessageType();

        switch (messageType) {
            case HAND_SHAKE_REQ:
                int appNumber = request.getAppNumber();
                String appNumberStr = String.valueOf(appNumber);
                SocketServer.getServer(SocketConst.LISTENING_PORT_APP).ChannelGroups().add(appNumberStr, ctx.channel());
                response = AXMessageExchange.handshake4app(request);
                break;

            case AUTH_REQ:
                AXMessageExchange.waitForAuth(request);
                break;
            case GARBAGE_DETECT_REQ:
                AXMessageExchange.waitForGarbageDetect(request);
                break;
            case APP_REQUIRE_OPEN_GATE_REQ:
                AXMessageExchange.waitForAppRequireOpenGate(request);
                break;
            case APP_START_PROCESS_REQ:
            case APP_FINISH_PROCESS_REQ:
                response = AXMessageExchange.notifyAppProcessStatusChanged(request);
                break;
            default:
        }

        return response;
    }


}
