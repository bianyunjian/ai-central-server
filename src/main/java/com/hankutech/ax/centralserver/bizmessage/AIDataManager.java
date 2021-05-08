package com.hankutech.ax.centralserver.bizmessage;


import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.message.code.AIEmpty;
import com.hankutech.ax.message.code.AIResult;
import com.hankutech.ax.message.code.AITaskType;
import com.hankutech.ax.message.code.ScenarioFlag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AIDataManager {

    private static ConcurrentHashMap<Integer, AIDataItem> deviceAiDataCacheMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, RFIDDataItem> deviceRFIDDataCacheMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, QRCodeDataItem> deviceQRCodeDataCacheMap = new ConcurrentHashMap<>();


    /**
     * 根据设备编号和摄像头编号查找到具体的事件。
     * 摄像头编号大于0， 则匹配摄像头编号
     * 摄像头编号等于0， 则忽略摄像头编号
     *
     * @param deviceId
     * @param cameraNumber
     * @param taskType
     * @return
     */
    public static AIResultWrapper getLatestAIResultByDeviceIdAndCameraNumber(Integer deviceId, int cameraNumber, AITaskType taskType) {
        log.info("AIDataManager.getLatestAIResultByDeviceIdAndCameraNumber查找事件,deviceId=" + deviceId + ",cameraNumber=" + cameraNumber + ",AITaskType=" + taskType.getDescription());

        if (deviceAiDataCacheMap.size() == 0 || deviceAiDataCacheMap.containsKey(deviceId) == false) {
            return new AIResultWrapper();
        }

        AIDataItem data = deviceAiDataCacheMap.get(deviceId);
        HashMap<AIResultKey, AIResultWrapper> aiTaskResultMap = data.getAITaskResultMap();

        AIResultWrapper result = new AIResultWrapper();
        for (AIResultKey aiResultKey : aiTaskResultMap.keySet()) {
            if (aiResultKey.getAiTaskType().getValue() == taskType.getValue()) {

                if (cameraNumber <= 0) {
                    result = aiTaskResultMap.get(aiResultKey);
                    break;
                } else {
                    if (aiResultKey.getCameraNumber() == cameraNumber) {
                        result = aiTaskResultMap.get(aiResultKey);
                        break;
                    }
                }
            }
        }

        //检查最新事件的时间是否已经超过设定的阈值
        if (result.getAiResult() != AIEmpty.EMPTY) {
            if (checkIfEventObsolete(result.getEventTime())) {
                log.warn("AIDataManager.getLatestAIResultByDeviceIdAndCameraNumber查找到的事件的发生时间已经超过设定的过期时间阈值" + Common.EVENT_OBSOLETE_SECONDS + "(秒):" + result.toString());
                aiTaskResultMap.remove(taskType);
                result = null;
            }
        }
        return result;
    }

    /**
     * 获取最新的RFID刷卡事件
     *
     * @param deviceId
     * @return
     */
    public static RFIDDataItem getLatestRFIDResultByDevice(int deviceId) {

        if (deviceRFIDDataCacheMap.size() == 0 || deviceRFIDDataCacheMap.containsKey(deviceId) == false) {
            return null;
        }

        RFIDDataItem data = deviceRFIDDataCacheMap.get(deviceId);

        //检查最新事件的时间是否已经超过设定的阈值
        if (checkIfEventObsolete(data.getEventTime())) {
            return null;
        }
        return data;
    }

    /**
     * 获取最新的二维码验证事件
     *
     * @param deviceId
     * @return
     */
    public static QRCodeDataItem getLatestQRCodeResultByDevice(int deviceId) {

        if (deviceQRCodeDataCacheMap.size() == 0 || deviceQRCodeDataCacheMap.containsKey(deviceId) == false) {
            return null;
        }

        QRCodeDataItem data = deviceQRCodeDataCacheMap.get(deviceId);

        //检查最新事件的时间是否已经超过设定的阈值
        if (checkIfEventObsolete(data.getEventTime())) {
            return null;
        }
        return data;
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
        if (eventTime == null) {
            return true;
        }
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

            HashMap<AIResultKey, AIResultWrapper> map = new HashMap<>();
            AIResultKey aiResultKey = new AIResultKey();
            aiResultKey.setAiTaskType(taskType);
            aiResultKey.setCameraNumber(cameraId);
            map.put(aiResultKey, aiResultWrapper);
            newData.setAITaskResultMap(map);
            deviceAiDataCacheMap.put(deviceId, newData);
            log.debug("add new AXDataItem for device[" + device.getDeviceId() + "-" + device.getDeviceName() + "] cameraId[" + cameraId + "]");

        } else {

            AIDataItem updateData = deviceAiDataCacheMap.get(deviceId);
            updateData.setScenarioFlag(scenarioFlag);

            HashMap<AIResultKey, AIResultWrapper> map = updateData.getAITaskResultMap();
            boolean findExistKey = false;
            for (AIResultKey aiResultKey : map.keySet()) {
                if (aiResultKey.getAiTaskType().getValue() == taskType.getValue() && aiResultKey.getCameraNumber() == cameraId) {
                    map.replace(aiResultKey, aiResultWrapper);
                    findExistKey = true;
                    break;
                }
            }
            if (findExistKey == false) {
                AIResultKey aiResultKey = new AIResultKey();
                aiResultKey.setAiTaskType(taskType);
                aiResultKey.setCameraNumber(cameraId);
                map.put(aiResultKey, aiResultWrapper);
            }

            log.debug("update AXDataItem for device[" + device.getDeviceId() + "-" + device.getDeviceName() + "] cameraId[" + cameraId + "]");
        }
    }

    /**
     * update device rfid data
     *
     * @param deviceId
     * @param dateTime
     */
    public static void updateRFIDResult(int deviceId, LocalDateTime dateTime) {


        if (deviceRFIDDataCacheMap.containsKey(deviceId) == false) {
            RFIDDataItem newData = new RFIDDataItem();
            newData.setDeviceId(deviceId);
            newData.setEventTime(dateTime);
            deviceRFIDDataCacheMap.put(deviceId, newData);
            log.debug("add new RFIDDataItem for device[" + deviceId + "]");
        } else {
            RFIDDataItem updateData = deviceRFIDDataCacheMap.get(deviceId);
            updateData.setEventTime(dateTime);
            log.debug("update RFIDDataItem for device[" + deviceId + "]");
        }
    }

    /**
     * update device QRCode data
     *
     * @param deviceId
     * @param dateTime
     */
    public static void updateQRCodeResult(int deviceId, LocalDateTime dateTime) {


        if (deviceQRCodeDataCacheMap.containsKey(deviceId) == false) {
            QRCodeDataItem newData = new QRCodeDataItem();
            newData.setDeviceId(deviceId);
            newData.setEventTime(dateTime);
            deviceQRCodeDataCacheMap.put(deviceId, newData);
            log.debug("add new QRCodeDataItem for device[" + deviceId + "]");
        } else {
            QRCodeDataItem updateData = deviceQRCodeDataCacheMap.get(deviceId);
            updateData.setEventTime(dateTime);
            log.debug("update QRCodeDataItem for device[" + deviceId + "]");
        }
    }


}
