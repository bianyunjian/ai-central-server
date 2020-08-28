package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.message.code.AITaskType;
import com.hankutech.ax.message.code.ScenarioFlag;
import lombok.Data;

import java.util.HashMap;

@Data
public class AIDataItem {
    private Device device;
    private ScenarioFlag scenarioFlag;
    private HashMap<AITaskType, AIResultWrapper> AITaskResultMap;

    AIDataItem() {
        AITaskResultMap = new HashMap<>();
    }

}
