package com.hankutech.ax.centralserver.socket.plc;

import com.hankutech.ax.centralserver.bizmessage.AXMessageExchange;
import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.ByteConverter;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.message.protocol.MessageSource;
import com.hankutech.ax.message.protocol.plc.*;
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

            PlcResponse response = null;
            PlcRequest request = PlcDataConverter.parseRequest(convertedData);
            if (request.getMessageSource() != MessageSource.PLC) {
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

            int[] respData = PlcDataConverter.convertResponse(response);
            byte[] respByteData = ByteConverter.toByte(respData);

            log.info(TAG + "转换后的响应数据：{}", response.toString());
            log.info(TAG + "发送的响应数据：{}", respByteData);
            ByteBuf responseByteBuf = Unpooled.buffer(respByteData.length);
            responseByteBuf.writeBytes(respByteData);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private PlcResponse handleRequest(ChannelHandlerContext ctx, PlcRequest request) {
        PlcResponse response = null;
        PlcMessageType messageType = request.getMessageType();
        int plcNumber = request.getPlcNumber();

        switch (messageType) {
            case HAND_SHAKE_REQ:
                String plcNumberStr = String.valueOf(plcNumber);
                SocketServer.getServer(SocketConst.LISTENING_PORT_PLC).ChannelGroups().add(plcNumberStr, ctx.channel());
                response = PlcResponse.defaultEmpty();
                response.setPlcNumber(plcNumber);
                response.setMessageType(PlcMessageType.HAND_SHAKE_RESP);
                response.setPayload(PlcMessageValue.HAND_SHAKE_RESP_SUCCESS);
                break;

            case OPEN_GATE_RESP:
                AXMessageExchange.plcOpenGateResp(request);
                break;
            case GATE_CLOSED_EVENT_REQ:
                response = PlcResponse.defaultEmpty();
                response.setPlcNumber(plcNumber);
                response.setMessageType(PlcMessageType.GATE_CLOSED_EVENT_RESP);
                response.setPayload(PlcMessageValue.GATE_CLOSED_EVENT_RESP_SUCCESS);
                AXMessageExchange.plcGateClosedEvent(request);
                break;

            case RFID_EVENT_REQ:
                response = PlcResponse.defaultEmpty();
                response.setPlcNumber(plcNumber);
                response.setMessageType(PlcMessageType.RFID_EVENT_RESP);
                response.setPayload(PlcMessageValue.RFID_EVENT_RESP_SUCCESS);
                AXMessageExchange.plcRFIDEvent(request);
                break;

            case SYS_STATUS_REQ:
                response = PlcResponse.defaultEmpty();
                response.setPlcNumber(plcNumber);
                response.setMessageType(PlcMessageType.SYS_STATUS_RESP);
                response.setPayload(PlcMessageValue.SYS_STATUS_RESP_SUCCESS);
                AXMessageExchange.plcSysStatusChangeEvent(request);
                break;


            case BOX_DETECT_REQ:
                AXMessageExchange.waitForBoxDetect(request);
                break;
            default:
        }

        return response;
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
