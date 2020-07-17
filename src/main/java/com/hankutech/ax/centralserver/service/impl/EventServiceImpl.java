package com.hankutech.ax.centralserver.service.impl;

import com.hankutech.ax.centralserver.biz.code.*;
import com.hankutech.ax.centralserver.biz.data.AXDataManager;
import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.EventDao;
import com.hankutech.ax.centralserver.dao.model.Event;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.CameraEventVO;
import com.hankutech.ax.centralserver.pojo.vo.EventVO;
import com.hankutech.ax.centralserver.service.EventService;
import com.hankutech.ax.centralserver.support.base.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EventServiceImpl implements EventService {
    DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    @Resource
    private EventDao _eventDao;
    @Resource
    private DeviceDao _deviceDao;
    @Resource
    private CameraDao _cameraDao;

    @Override
    public BaseResponse handleUploadData(DeviceUploadParams request) {
        BaseResponse resp = new BaseResponse();
        String time = request.getTime();
        LocalDateTime eventTime = LocalDateTime.parse(time, fromFormatter);
        int deviceId = request.getDeviceId();
        String flag = _deviceDao.selectById(deviceId).getDeviceScenario();
        ScenarioFlag scenarioFlag = StringUtils.isEmpty(flag) ? ScenarioFlag.EMPTY : ScenarioFlag.valueOf(flag);

        for (CameraEventVO ev :
                request.getCameraList()) {

            int cameraId = ev.getCameraId();
            int cameraNumber = _cameraDao.selectById(cameraId).getAxCameraNumber();

            for (EventVO e :
                    ev.getEvents()) {

                //解析识别结果
                String aiTaskType = e.getType();
                Integer aiResultValue = e.getValue();
                String imageBase64 = e.getImageBase64();
                String eventType = getEventType(aiTaskType);
                Integer eventTypeValue = getEventTypeValue(eventType, aiResultValue);
                String description = getEventDescription(eventType, aiResultValue);
                String imgName = getImageName(deviceId, cameraId, eventType, eventTime
                );
                String imgFilePath = SaveEventImage(imgName, imageBase64);

//                更新缓存中， 缓存中只保留 当前最新的结果
                AITaskType aiTaskTypeEnum = AITaskType.valueOf(eventType.toUpperCase());
                AIResult aiResult = getEventAIResult(eventType, aiResultValue);
                AXDataManager.updateAIResult(cameraNumber, scenarioFlag, aiTaskTypeEnum, aiResult, eventTime);

//              持久化到数据库中
                Event newEventEntity = new Event();
                newEventEntity.setCameraId(cameraId);
                newEventEntity.setDeviceId(deviceId);

                newEventEntity.setEventTime(eventTime);
                newEventEntity.setEventType(eventType);
                newEventEntity.setEventTypeValue(eventTypeValue);
                newEventEntity.setDescription(description);
                newEventEntity.setEventImagePath(imgFilePath);

                int affectRowCount = _eventDao.insert(newEventEntity);
                if (affectRowCount > 0) {
                    resp.success("Add new Event Data OK", newEventEntity.getEventId());
                } else {
                    resp.fail("Add new Event Data failed");
                }
            }
        }

        return resp;
    }

    private String getImageName(int deviceId, int cameraId, String eventType, LocalDateTime time) {
        return deviceId + "-" + cameraId + "-" + eventType + "-" + time.format(formatter);
    }

    private String SaveEventImage(String imgFileName, String imageBase64) {
        if (StringUtils.isEmpty(imageBase64)) {
            return null;
        }
        String imgFilePath = Common.IMAGE_FOLDER_PATH + imgFileName;
        String imgFormat = Common.IMAGE_FORMAT;
        return ImageUtil.base64ToImage(imageBase64, imgFilePath, imgFormat);

    }

    private String getEventDescription(String eventType, Integer aiResultValue) {
        AITaskType aiTaskType = AITaskType.valueOf(eventType.toUpperCase());
        String desc = aiTaskType.getDescription() + "-";

        AIResult aiResult = getEventAIResult(eventType, aiResultValue);
        desc += aiResult.getDescription();
        return desc;
    }

    private AIResult getEventAIResult(String eventType, Integer aiResultValue) {
        Integer resultValue = getEventTypeValue(eventType, aiResultValue);
        AITaskType aiTaskType = AITaskType.valueOf(eventType.toUpperCase());

        switch (aiTaskType) {
            case BOX:
                return AIBoxResultType.valueOf(resultValue);

            case GARBAGE:
                return AIGarbageResultType.valueOf(resultValue);

            case FACE:
                return AIFaceResultType.valueOf(resultValue);

            case PERSON:
                return AIPersonResultType.valueOf(resultValue);
        }

        return AIBoxResultType.EMPTY;
    }

    private Integer getEventTypeValue(String eventType, Integer aiResultValue) {
        return aiResultValue;
    }

    private String getEventType(String aiTaskType) {
        return aiTaskType;

    }
}
