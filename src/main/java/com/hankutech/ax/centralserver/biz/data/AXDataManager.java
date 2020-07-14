package com.hankutech.ax.centralserver.biz.data;

import com.hankutech.ax.centralserver.biz.code.AIResult;
import com.hankutech.ax.centralserver.biz.code.AITaskType;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import com.hankutech.ax.centralserver.biz.protocol.AXRequest;
import com.hankutech.ax.centralserver.biz.protocol.AXResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AXDataManager {
    public static AXResponse query(AXRequest request) {

        AXResponse response = new AXResponse();

        response.setSysRunFlag(request.getSysRunFlag());
        response.setCameraNumber(request.getCameraNumber());
        response.setScenarioFlag(request.getScenarioFlag());
        response.setTaskType(request.getTaskType());

        AIResultWrapper aiResultWrapper = getLatestAIResult(request.getCameraNumber(), request.getTaskType());
        response.setAiResult(aiResultWrapper);

        return response;
    }

    private static ConcurrentHashMap<Integer, AXDataItem> _dataCacheMap = new ConcurrentHashMap<>();

    /**
     * get all cached data
     *
     * @return
     */
    public static ConcurrentHashMap<Integer, AXDataItem> getAll() {
        return _dataCacheMap;
    }

    /**
     * get latest cached data by camera and taskType
     *
     * @param cameraNumber
     * @param taskType
     * @return
     */
    public static AIResultWrapper getLatestAIResult(int cameraNumber, AITaskType taskType) {

        if (_dataCacheMap.size() == 0 || _dataCacheMap.containsKey(cameraNumber) == false) {
            return new AIResultWrapper();
        }

        AXDataItem data = _dataCacheMap.get(cameraNumber);
        HashMap<AITaskType, AIResultWrapper> aiTaskResultMap = data.getAITaskResultMap();

        return aiTaskResultMap.getOrDefault(taskType, new AIResultWrapper());
    }


    /**
     * update cached ai data
     *
     * @param cameraNumber
     * @param scenarioFlag
     * @param taskType
     * @param aiResult
     * @param dateTime
     */
    public static void updateAIResult(int cameraNumber, ScenarioFlag scenarioFlag, AITaskType taskType, AIResult aiResult, LocalDateTime dateTime) {

        AIResultWrapper aiResultWrapper = new AIResultWrapper(aiResult, dateTime);
        if (_dataCacheMap.containsKey(cameraNumber) == false) {
            AXDataItem newData = new AXDataItem();
            newData.setCameraNumber(cameraNumber);
            newData.setScenarioFlag(scenarioFlag);
            HashMap<AITaskType, AIResultWrapper> map = new HashMap<>();
            map.put(taskType, aiResultWrapper);
            newData.setAITaskResultMap(map);
            _dataCacheMap.put(cameraNumber, newData);
            System.out.println("add new AXDataItem for cameraNumber[" + cameraNumber + "]");

        } else {

            AXDataItem updateData = _dataCacheMap.get(cameraNumber);
            updateData.setScenarioFlag(scenarioFlag);

            HashMap<AITaskType, AIResultWrapper> map = updateData.getAITaskResultMap();
            if (map.containsKey(taskType)) {
                map.replace(taskType, aiResultWrapper);
            } else {
                map.put(taskType, aiResultWrapper);
            }
            System.out.println("update AXDataItem for cameraNumber[" + cameraNumber + "]");
        }
    }
}
