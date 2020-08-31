package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.ByteConverter;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.message.code.AIFaceResultType;
import com.hankutech.ax.message.code.AIResult;
import com.hankutech.ax.message.code.AITaskType;
import com.hankutech.ax.message.protocol.app.*;
import com.hankutech.ax.message.protocol.plc.PlcDataConverter;
import com.hankutech.ax.message.protocol.plc.PlcMessageType;
import com.hankutech.ax.message.protocol.plc.PlcRequest;
import com.hankutech.ax.message.protocol.plc.PlcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class AXMessageExchange {


    private static final String TAG = "[AXMessageExchange]";

    /**
     * 中心算法控制器会向艾信PLC下达开门指令.
     * 艾信PLC回复后，中心算法控制器向APP发送【APP门已打开】的消息
     *
     * @param request
     */
    public static void plcOpenGateResp(PlcRequest request) {
        log.debug("plcOpenGateResp");
        int plcNumber = request.getPlcNumber();
        List<Integer> appNumberList = DeviceRelationManager.getAppNumber(plcNumber);

        for (Integer appNumber : appNumberList
        ) {
            AppResponse appMessage = AppResponse.defaultEmpty();
            appMessage.setAppNumber(appNumber);
            appMessage.setMessageType(AppMessageType.APP_REQUIRE_OPEN_GATE_RESP);
            appMessage.setPayload(request.getPayload());
            sendMessage2App(appNumber, appMessage);
        }
    }


    /**
     * 当艾信PLC 处于 故障或者维护状态时， 艾信PLC会向中心算法控制器发送【系统状态事件】， 中心算法控制器接收到消息后，会回复一个【系统状态事件响应】消息， 同时会转发该消息到关联的APP， 设置APP的状态。
     * 艾信PLC恢复正常后， 需要向中心算法控制器发送包含正常状态的【系统状态事件】， 或者重新连接中心算法控制器。
     *
     * @param request
     */
    public static void plcSysStatusChangeEvent(PlcRequest request) {
        log.debug("plcSysStatusChangeEvent");
        int plcNumber = request.getPlcNumber();
        List<Integer> appNumberList = DeviceRelationManager.getAppNumber(plcNumber);

        for (Integer appNumber : appNumberList
        ) {
            AppResponse appMessage = AppResponse.defaultEmpty();
            appMessage.setAppNumber(appNumber);
            appMessage.setMessageType(AppMessageType.SYS_STATUS_REQ);
            appMessage.setPayload(request.getPayload());
            sendMessage2App(appNumber, appMessage);
        }

    }

    /**
     * 中心算法控制器接收到RFID刷卡事件后，更新当前的AI数据缓存
     *
     * @param request
     */
    public static void plcRFIDEvent(PlcRequest request) {
        log.debug("plcRFIDEvent");
        int plcNumber = request.getPlcNumber();
        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByPlcNumber(plcNumber);
        //update RFID Data
        for (int deviceId : deviceIdList
        ) {
            AIDataManager.updateRFIDResult(deviceId, LocalDateTime.now());
        }

    }

    /**
     * 当工作人员投递垃圾完成后， 手动关上投递口的门。
     * 中心算法控制器再接收到艾信PLC关门消息后，向APP发送一个【APP门已关闭】消息
     *
     * @param request
     */
    public static void plcGateClosedEvent(PlcRequest request) {
        log.debug("plcGateClosedEvent");
        int plcNumber = request.getPlcNumber();
        List<Integer> appNumberList = DeviceRelationManager.getAppNumber(plcNumber);

        for (Integer appNumber : appNumberList
        ) {
            AppResponse appMessage = AppResponse.defaultEmpty();
            appMessage.setAppNumber(appNumber);
            appMessage.setMessageType(AppMessageType.GATE_CLOSED_EVENT_REQ);
            appMessage.setPayload(request.getPayload());
            sendMessage2App(appNumber, appMessage);
        }
    }


    /**
     * 中心算法控制器在找到了有效的身份验证信息后， 向APP回复一个【APP身份验证响应】消息
     *
     * @param request
     */
    public static void waitForAuth(AppRequest request) {
        log.debug("waitForAuth");

        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByAppNumber(request.getAppNumber());
        Integer deviceId = deviceIdList.get(0);

        //RFID 验证
        if (request.getPayload() == AppMessageValue.AUTH_REQ_RFID) {
            RFIDDataItem rfidDataItem = AIDataManager.getLatestRFIDResultByDevice(deviceId);

            //验证成功
            if (rfidDataItem != null) {
                AppResponse response = AppResponse.defaultEmpty();
                response.setPayload(AppMessageValue.AUTH_RESP_RFID_SUCCESS);
                response.setMessageType(AppMessageType.AUTH_RESP);
                response.setAppNumber(request.getAppNumber());
                sendMessage2App(request.getAppNumber(), response);
            }
        }

        //人脸验证
        if (request.getPayload() == AppMessageValue.AUTH_REQ_FACE) {

            AIResultWrapper aiData = AIDataManager.getLatestAIResultByDevice(deviceId, AITaskType.FACE);
            //验证成功
            if (aiData != null && aiData.getAiResult().getValue() == AIFaceResultType.FACE_PASS.getValue()) {
                AppResponse response = AppResponse.defaultEmpty();
                response.setPayload(AppMessageValue.AUTH_RESP_FACE_SUCCESS);
                response.setMessageType(AppMessageType.AUTH_RESP);
                response.setAppNumber(request.getAppNumber());
                sendMessage2App(request.getAppNumber(), response);
            }
        }
    }

    /**
     * 中心算法控制器在找到了有效的垃圾检测信息后， 向APP回复一个【APP垃圾检测响应】消息
     *
     * @param request
     */
    public static void waitForGarbageDetect(AppRequest request) {
        log.debug("waitForGarbageDetect");
        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByAppNumber(request.getAppNumber());
        Integer deviceId = deviceIdList.get(0);

        AIResultWrapper aiData = AIDataManager.getLatestAIResultByDevice(deviceId, AITaskType.GARBAGE);
        //验证成功
        if (aiData != null) {
            AppResponse response = AppResponse.defaultEmpty();
            response.setMessageType(AppMessageType.GARBAGE_DETECT_RESP);
            response.setAppNumber(request.getAppNumber());
            //判断垃圾检测类型是否一致
            int reqiredGarbageType = request.getExtData();
            AIResult detectGarbageType = aiData.getAiResult();

            response.setExtData(detectGarbageType.getValue());
            if (detectGarbageType.getValue() == reqiredGarbageType) {
                response.setPayload(AppMessageValue.GARBAGE_DETECT_RESP_SUCCESS);
            } else {
                response.setPayload(AppMessageValue.GARBAGE_DETECT_RESP_FAILURE);
            }
            sendMessage2App(request.getAppNumber(), response);
        }
    }

    /**
     * 检测通过后， APP 会向中心算法控制器发送一个【APP请求开门】消息;
     * 中心算法控制器会向艾信PLC下达开门指令， 等待艾信PLC回复
     *
     * @param request
     */
    public static void waitForAppRequireOpenGate(AppRequest request) {
        log.debug("waitForAppRequireOpenGate");
        int appNumber = request.getAppNumber();
        List<Integer> plcNumberList = DeviceRelationManager.getPlcNumber(appNumber);

        for (Integer plcNumber : plcNumberList
        ) {
            PlcResponse plcMessage = PlcResponse.defaultEmpty();
            plcMessage.setPlcNumber(plcNumber);
            plcMessage.setMessageType(PlcMessageType.OPEN_GATE_REQ);
            sendMessage2Plc(plcNumber, plcMessage);
        }
    }


    private static void sendMessage2App(int appNumber, AppResponse response) {
        try {


            Channel channel = SocketServer.getServer(SocketConst.LISTENING_PORT_APP).ChannelGroups().get(String.valueOf(appNumber));
            if (channel != null && channel.isWritable()) {

                int[] respData = AppDataConverter.convertResponse(response);

                byte[] respByteData = ByteConverter.toByte(respData);

                log.info(TAG + "[sendMessage2App]" + "转换后的响应数据：{}", response.toString());
                log.info(TAG + "[sendMessage2App]" + "发送的响应数据：{}", respByteData);
                ByteBuf responseByteBuf = Unpooled.buffer(respByteData.length);
                responseByteBuf.writeBytes(respByteData);

                channel.writeAndFlush(responseByteBuf);
            } else {
                log.error(TAG + "[sendMessage2Plc]" + "未找到有效Channel：{}", appNumber);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void sendMessage2Plc(int plcNumber, PlcResponse response) {
        try {


            Channel channel = SocketServer.getServer(SocketConst.LISTENING_PORT_PLC).ChannelGroups().get(String.valueOf(plcNumber));
            if (channel != null && channel.isWritable()) {
                int[] respData = PlcDataConverter.convertResponse(response);

                byte[] respByteData = ByteConverter.toByte(respData);

                log.info(TAG + "[sendMessage2Plc]" + "转换后的响应数据：{}", response.toString());
                log.info(TAG + "[sendMessage2Plc]" + "发送的响应数据：{}", respByteData);
                ByteBuf responseByteBuf = Unpooled.buffer(respByteData.length);
                responseByteBuf.writeBytes(respByteData);
                channel.writeAndFlush(responseByteBuf);
            } else {
                log.error(TAG + "[sendMessage2Plc]" + "未找到有效Channel：{}", plcNumber);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
