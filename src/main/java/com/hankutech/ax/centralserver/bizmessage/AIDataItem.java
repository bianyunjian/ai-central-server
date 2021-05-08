package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.message.code.ScenarioFlag;
import lombok.Data;

import java.util.HashMap;

@Data
public class AIDataItem {
    private Device device;
    private ScenarioFlag scenarioFlag;
    private HashMap<AIResultKey, AIResultWrapper> AITaskResultMap;

    AIDataItem() {
        AITaskResultMap = new HashMap<>();
    }

}
