package com.hankutech.ax.centralserver.bizmessage;

import com.alibaba.fastjson.JSON;
import com.hankutech.ax.message.code.AIEmpty;
import com.hankutech.ax.message.code.AIResult;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@ToString
public class AIResultWrapper {

    private int cameraId;
    private AIResult aiResult;
    private LocalDateTime eventTime;
    private HashMap<String, String> extProperty;

    public AIResultWrapper() {
        aiResult = AIEmpty.EMPTY;
    }


    public AIResultWrapper(int cameraId, AIResult result, LocalDateTime time) {
        this.cameraId = cameraId;
        this.aiResult = result;
        this.eventTime = time;
    }

    public String getExtProperty() {
        if (extProperty == null || extProperty.size() == 0) return "";
        return JSON.toJSONString(extProperty);
    }
}
