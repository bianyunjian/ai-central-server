package com.hankutech.ax.centralserver.biz;

import com.hankutech.ax.centralserver.biz.code.AIBoxResultType;
import com.hankutech.ax.centralserver.biz.code.AIFaceResultType;
import com.hankutech.ax.centralserver.biz.code.AIGarbageResultType;
import com.hankutech.ax.centralserver.biz.code.AIPersonResultType;

public class AXDataManager {
    public static AXResponse query(AXRequest request) {

        //TODO
        AXResponse response = new AXResponse();

        response.setSysRunFlag(request.getSysRunFlag());
        response.setCameraNumber(request.getCameraNumber());
        response.setScenarioFlag(request.getScenarioFlag());
        response.setTaskType(request.getTaskType());

        switch (request.getTaskType()) {

            case SECURITY_BOX:
                response.setAiResult(AIBoxResultType.NO_COVER);
                break;
            case FACE:
                response.setAiResult(AIFaceResultType.FACE_PASS);
                break;
            case GARBAGE:
                response.setAiResult(AIGarbageResultType.WET);
                break;
            case PERSON:
                response.setAiResult(AIPersonResultType.PERSON_EXIST);
                break;
            default:
                response.setAiResult(AIBoxResultType.EMPTY);
                break;
        }

        return response;
    }
}
