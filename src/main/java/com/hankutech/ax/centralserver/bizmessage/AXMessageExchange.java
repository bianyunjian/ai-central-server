package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.constant.SocketConst;
import com.hankutech.ax.centralserver.socket.ByteConverter;
import com.hankutech.ax.centralserver.socket.SocketServer;
import com.hankutech.ax.message.code.*;
import com.hankutech.ax.message.protocol.MessageSource;
import com.hankutech.ax.message.protocol.app.AppDataConverter;
import com.hankutech.ax.message.protocol.app.AppMessage;
import com.hankutech.ax.message.protocol.app.AppMessageType;
import com.hankutech.ax.message.protocol.app.AppMessageValue;
import com.hankutech.ax.message.protocol.plc.*;
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
            AppMessage appMessage = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
            AppMessage appMessage = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
            AppMessage appMessage = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
    public static void waitForAuth(AppMessage request) {
        log.debug("waitForAuth");

        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByAppNumber(request.getAppNumber());
        Integer deviceId = deviceIdList.get(0);

        //RFID 验证
        if (request.getPayload() == AppMessageValue.AUTH_REQ_RFID) {
            RFIDDataItem rfidDataItem = AIDataManager.getLatestRFIDResultByDevice(deviceId);

            //验证成功
            if (rfidDataItem != null) {
                AppMessage response = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
                AppMessage response = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
    public static void waitForGarbageDetect(AppMessage request) {
        log.info("waitForGarbageDetect");
        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByAppNumber(request.getAppNumber());
        Integer deviceId = deviceIdList.get(0);
        log.info("waitForGarbageDetect deviceId=" + deviceId);
        AIResultWrapper aiData = AIDataManager.getLatestAIResultByDevice(deviceId, AITaskType.GARBAGE);
//        aiData.setAiResult(AIGarbageResultType.DRY);
        //验证成功
        if (aiData != null && aiData.getAiResult() != AIEmpty.EMPTY) {
            AppMessage response = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
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
        } else {
            log.error("waitForGarbageDetect 未找到有效数据");
        }
    }

    /**
     * 检测通过后， APP 会向中心算法控制器发送一个【APP请求开门】消息;
     * 中心算法控制器会向艾信PLC下达开门指令， 等待艾信PLC回复
     *
     * @param request
     */
    public static void waitForAppRequireOpenGate(AppMessage request) {
        log.debug("waitForAppRequireOpenGate");
        int appNumber = request.getAppNumber();
        List<Integer> plcNumberList = DeviceRelationManager.getPlcNumber(appNumber);

        for (Integer plcNumber : plcNumberList
        ) {
            PlcResponse plcMessage = PlcResponse.defaultEmpty();
            plcMessage.setPlcNumber(plcNumber);
            plcMessage.setMessageType(PlcMessageType.OPEN_GATE_REQ);
            plcMessage.setPayload(PlcMessageValue.OPEN_GATE_REQ);
            sendMessage2Plc(plcNumber, plcMessage);
        }
    }


    private static void sendMessage2App(int appNumber, AppMessage response) {
        try {


            Channel channel = SocketServer.getServer(SocketConst.LISTENING_PORT_APP).ChannelGroups().get(String.valueOf(appNumber));
            if (channel != null && channel.isWritable()) {

                int[] respData = AppDataConverter.convert(response);

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

    public static AppMessage notifyAppProcessStatusChanged(AppMessage request) {
        int currentAppNumber = request.getAppNumber();

        List<Integer> deviceIds = DeviceRelationManager.getDeviceIdByAppNumber(currentAppNumber);
        if (deviceIds != null && deviceIds.size() > 0) {
            int deviceGroupId = DeviceRelationManager.getDeviceGroupIdByDeviceId(deviceIds.get(0));
            List<Integer> appNumberList = DeviceRelationManager.getAppNumberByDeviceGroupId(deviceGroupId);
            //当其中某个APP在使用时， 中心算法控制器会向同组的其他APP发送【系统状态事件】消息（数据=繁忙）。
            //当其中某个APP使用完成后， 中心算法控制器会向同组的其他APP发送【系统状态事件】消息（数据=正常）
            for (Integer ap : appNumberList
            ) {
                if (ap == currentAppNumber) {
                    continue;
                }
                AppMessage sysStatusRequest = new AppMessage();
                sysStatusRequest.setMessageSource(MessageSource.CENTRAL_SERVER);
                sysStatusRequest.setAppNumber(ap);
                sysStatusRequest.setMessageType(AppMessageType.SYS_STATUS_REQ);
                int payload = 0;
                if (request.getMessageType() == AppMessageType.APP_START_PROCESS_REQ) {
                    payload = SysRunFlag.BUSY.getValue();
                }
                if (request.getMessageType() == AppMessageType.APP_FINISH_PROCESS_REQ) {
                    payload = SysRunFlag.NORMAL.getValue();
                }
                sysStatusRequest.setPayload(payload);
                sendMessage2App(ap, sysStatusRequest);

            }
        }
        AppMessage response = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
        response.setAppNumber(currentAppNumber);
        response.setMessageType(AppMessageType.valueOf(request.getMessageType().getValue() + 1));
        response.setPayload(AppMessageValue.APP_PROCESS_RESP_SUCCESS);
        return response;
    }

    public static AppMessage handshake4app(AppMessage request) {
        int appNumber = request.getAppNumber();
        AppMessage response = AppMessage.defaultEmpty(MessageSource.CENTRAL_SERVER);
        response.setAppNumber(appNumber);
        response.setMessageType(AppMessageType.HAND_SHAKE_RESP);
        response.setPayload(AppMessageValue.HAND_SHAKE_RESP_SUCCESS);
        response.setExtData(getCurrentGarbageType(appNumber).getValue());
        return response;
    }

    /**
     * 获取当前支持的垃圾类型
     *
     * @param appNumber
     * @return
     */
    private static AIGarbageResultType getCurrentGarbageType(int appNumber) {
        List<Integer> deviceIds = DeviceRelationManager.getDeviceIdByAppNumber(appNumber);
        Integer deviceId = 0;
        if (deviceIds != null && deviceIds.size() > 0) {
            deviceId = deviceIds.get(0);
        }
        return DeviceRelationManager.getDeviceGarbageType(deviceId);

    }


    /**
     * plc向中心算法控制器询问周转箱检测的状态信息， 中心服务器向PLC回复[周转箱检测响应】消息
     *
     * @param request
     */
    public static void waitForBoxDetect(PlcRequest request) {
        log.debug("waitForBoxDetect");
        List<Integer> deviceIdList = DeviceRelationManager.getDeviceIdByPlcNumber(request.getPlcNumber());
        Integer deviceId = deviceIdList.get(0);

        AIResultWrapper aiData = AIDataManager.getLatestAIResultByDevice(deviceId, AITaskType.BOX);

        if (aiData != null && aiData.getAiResult() != AIEmpty.EMPTY) {
            PlcResponse response = PlcResponse.defaultEmpty();
            response.setMessageSource(MessageSource.CENTRAL_SERVER);
            response.setMessageType(PlcMessageType.BOX_DETECT_RESP);
            response.setPlcNumber(request.getPlcNumber());
            response.setPayload(aiData.getAiResult().getValue());
            sendMessage2Plc(request.getPlcNumber(), response);
        }
    }
}
