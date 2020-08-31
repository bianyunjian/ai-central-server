package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.message.protocol.app.AppRequest;
import com.hankutech.ax.message.protocol.app.AppResponse;
import com.hankutech.ax.message.protocol.plc.PlcMessageType;
import com.hankutech.ax.message.protocol.plc.PlcRequest;
import com.hankutech.ax.message.protocol.plc.PlcResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AXMessageExchange {

    public static PlcResponse query(PlcRequest request) {

        //TODO
//        AIResultWrapper aiResultWrapper = getLatestAIResult(request.getPlcNumber(), request.getMessageType());
//        response.setPayload(0);

        PlcResponse response = PlcResponse.defaultEmpty();

        response.setPlcNumber(request.getPlcNumber());
        response.setMessageType(getResponseMessageType(request.getMessageType()));
        return response;
    }


    public static AppResponse query(AppRequest request) {

        AppResponse response = AppResponse.defaultEmpty();

        response.setAppNumber(request.getAppNumber());
        response.setMessageType(request.getMessageType());


//TODO
//        AIResultWrapper aiResultWrapper = getLatestAIResult(request.getPlcNumber(), request.getMessageType());
//        response.setPayload(0);

        return response;
    }

    private static PlcMessageType getResponseMessageType(PlcMessageType messageType) {
        return PlcMessageType.valueOf(messageType.getValue() + 1);
    }

    public static void notifyPlcMessage(PlcRequest request) {
        log.debug("notifyPlcMessage");
    }

    public static void waitForAuth(AppRequest request) {
        log.debug("waitForAuth");
    }

    public static void waitForGarbageDetect(AppRequest request) {
        log.debug("waitForGarbageDetect");
    }

    public static void waitForOpenGate(AppRequest request) {
        log.debug("waitForOpenGate");
    }
}
