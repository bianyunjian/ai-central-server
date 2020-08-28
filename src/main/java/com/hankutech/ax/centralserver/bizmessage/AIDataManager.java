package com.hankutech.ax.centralserver.bizmessage;


import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.message.code.AIBoxResultType;
import com.hankutech.ax.message.code.AIResult;
import com.hankutech.ax.message.code.AITaskType;
import com.hankutech.ax.message.code.ScenarioFlag;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AIDataManager {

    private static ConcurrentHashMap<Integer, AIDataItem> deviceAiDataCacheMap = new ConcurrentHashMap<>();

    /**
     * get all cached data
     *
     * @return
     */
    public static ConcurrentHashMap<Integer, AIDataItem> getAll() {
        return deviceAiDataCacheMap;
    }

    /**
     * get latest cached data by deviceId and taskType
     *
     * @param deviceId
     * @param taskType
     * @param cameraId
     * @return
     */
    public static AIResultWrapper getLatestAIResultByDevice(int deviceId, AITaskType taskType, int cameraId) {

        if (deviceAiDataCacheMap.size() == 0 || deviceAiDataCacheMap.containsKey(deviceId) == false) {
            return new AIResultWrapper();
        }

        AIDataItem data = deviceAiDataCacheMap.get(deviceId);
        HashMap<AITaskType, AIResultWrapper> aiTaskResultMap = data.getAITaskResultMap();

        AIResultWrapper result = aiTaskResultMap.getOrDefault(taskType, new AIResultWrapper());
        if (result.getCameraId() != cameraId) {
            return new AIResultWrapper();
        }
        //检查最新事件的时间是否已经超过设定的阈值
        if (checkIfEventObsolete(result.getEventTime())) {
            result.setAiResult(AIBoxResultType.EMPTY);
        }
        return result;
    }

    /**
     * 检查最新事件的时间是否已经超过设定的阈值.
     * 爱信系统获取数据时, 需要判断事件的有效时间.
     * 应该只对爱信系统返回有效时间内的最新事件数据.
     *
     * @param eventTime
     * @return
     */
    private static boolean checkIfEventObsolete(LocalDateTime eventTime) {
        if (eventTime == null) return true;
        LocalDateTime obTime = LocalDateTime.now().minusSeconds(Common.EVENT_OBSOLETE_SECONDS);
        return eventTime.isBefore(obTime);
    }


    /**
     * update cached ai data
     *
     * @param device
     * @param cameraId
     * @param scenarioFlag
     * @param taskType
     * @param aiResult
     * @param dateTime
     */
    public static void updateAIResult(Device device, int cameraId, ScenarioFlag scenarioFlag, AITaskType taskType, AIResult aiResult, LocalDateTime dateTime, String eventType, String eventTypeValue) {

        AIResultWrapper aiResultWrapper = new AIResultWrapper(cameraId, aiResult, dateTime);
        if (StringUtils.isEmpty(eventTypeValue) == false) {
            HashMap<String, String> extProperty = new HashMap<>();
            extProperty.put("eventType", eventType);
            extProperty.put("eventTypeValue", eventTypeValue);
            aiResultWrapper.setExtProperty(extProperty);
        }

        Integer deviceId = device.getDeviceId();
        if (deviceAiDataCacheMap.containsKey(deviceId) == false) {
            AIDataItem newData = new AIDataItem();
            Device d = new Device();
            d.setDeviceId(device.getDeviceId());
            d.setDeviceGroupId(device.getDeviceGroupId());
            d.setAxPlcId(device.getAxPlcId());
            d.setAppId(device.getAppId());
            d.setDeviceName(device.getDeviceName());
            newData.setDevice(d);

            newData.setScenarioFlag(scenarioFlag);

            HashMap<AITaskType, AIResultWrapper> map = new HashMap<>();
            map.put(taskType, aiResultWrapper);
            newData.setAITaskResultMap(map);
            deviceAiDataCacheMap.put(deviceId, newData);
            System.out.println("add new AXDataItem for device[" + device.getDeviceId() + "-" + device.getDeviceName() + "] cameraId[" + cameraId + "]");

        } else {

            AIDataItem updateData = deviceAiDataCacheMap.get(deviceId);
            updateData.setScenarioFlag(scenarioFlag);

            HashMap<AITaskType, AIResultWrapper> map = updateData.getAITaskResultMap();
            if (map.containsKey(taskType)) {
                map.replace(taskType, aiResultWrapper);
            } else {
                map.put(taskType, aiResultWrapper);
            }
            System.out.println("update AXDataItem for device[" + device.getDeviceId() + "-" + device.getDeviceName() + "] cameraId[" + cameraId + "]");
        }
    }


}
