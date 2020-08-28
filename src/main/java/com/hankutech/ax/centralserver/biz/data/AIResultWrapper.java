package com.hankutech.ax.centralserver.biz.data;

import com.alibaba.fastjson.JSON;
import com.hankutech.ax.centralserver.biz.code.AIBoxResultType;
import com.hankutech.ax.centralserver.biz.code.AIResult;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public class AIResultWrapper {
    private AIResult aiResult;
    private LocalDateTime eventTime;
    private HashMap<String, String> extProperty;

    public AIResultWrapper() {
        aiResult = AIBoxResultType.EMPTY;
    }


    public AIResultWrapper(AIResult result, LocalDateTime time) {
        this.aiResult = result;
        this.eventTime = time;
    }

    public String getExtProperty() {
        if (extProperty == null || extProperty.size() == 0) return "";
        return JSON.toJSONString(extProperty);
    }
}
