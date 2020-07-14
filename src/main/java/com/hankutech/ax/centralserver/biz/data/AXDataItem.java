package com.hankutech.ax.centralserver.biz.data;

import com.hankutech.ax.centralserver.biz.code.AITaskType;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import lombok.Data;

import java.util.HashMap;

@Data
public class AXDataItem {
    private int cameraNumber;
    private ScenarioFlag scenarioFlag;
    private HashMap<AITaskType, AIResultWrapper> AITaskResultMap;

    AXDataItem() {
        AITaskResultMap = new HashMap<>();
    }

}
