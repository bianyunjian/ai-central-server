package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.message.protocol.app.AppRequest;
import com.hankutech.ax.message.protocol.app.AppResponse;
import com.hankutech.ax.message.protocol.plc.PlcRequest;
import com.hankutech.ax.message.protocol.plc.PlcResponse;

public class AXMessageExchange {

    public static PlcResponse query(PlcRequest request) {

        PlcResponse response = PlcResponse.defaultEmpty();

        response.setPlcNumber(request.getPlcNumber());
        response.setMessageType(request.getMessageType());


        //TODO
//        AIResultWrapper aiResultWrapper = getLatestAIResult(request.getPlcNumber(), request.getMessageType());
//        response.setPayload(0);

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


}
