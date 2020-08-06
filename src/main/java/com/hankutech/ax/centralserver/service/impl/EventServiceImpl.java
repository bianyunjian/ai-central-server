package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.biz.code.*;
import com.hankutech.ax.centralserver.biz.data.AIResultWrapper;
import com.hankutech.ax.centralserver.biz.data.AXDataManager;
import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.constant.ErrorCode;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.EventDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.dao.model.Event;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.query.HistoryEventParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.CameraEventVO;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.pojo.vo.EventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.history.HistoryEventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.realtime.RealtimeEventVO;
import com.hankutech.ax.centralserver.service.EventService;
import com.hankutech.ax.centralserver.support.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse handleUploadData(DeviceUploadParams request) throws InvalidDataException {
        BaseResponse resp = new BaseResponse();
        String time = request.getTime();
        LocalDateTime eventTime = LocalDateTime.parse(time, fromFormatter);
        int deviceId = request.getDeviceId();
        // 检测设备是否存在
        Device device = _deviceDao.selectById(deviceId);
        if (null == device) {
            throw new InvalidDataException(MessageFormat.format("设备ID={0}不存在", deviceId)).with(ErrorCode.DEVICE_NOT_EXIST);
        }
        String flag = device.getDeviceScenario();
        // debug1: ScenarioFlag.valueOf参数应为int
        ScenarioFlag scenarioFlag = StringUtils.isEmpty(flag) ? ScenarioFlag.EMPTY : ScenarioFlag.valueOf(Integer.parseInt(flag));

        List<Event> batchList = new ArrayList<>();
        for (CameraEventVO ev : request.getCameraList()) {
            int cameraId = ev.getCameraId();
            // 检测相机是否存在
//            //TODO
//            cameraId = 1;
            Camera camera = _cameraDao.selectById(cameraId);
            if (null == camera) {
                throw new InvalidDataException(MessageFormat.format("相机ID={0}不存在", cameraId)).with(ErrorCode.CAMERA_NOT_EXIST);
            }
            int cameraNumber = _cameraDao.selectById(cameraId).getAxCameraNumber();
            List<EventVO> list = ev.getEvents();
            if (null == list || list.isEmpty()) {
                log.warn("相机ID={} 无事件", cameraId);
                continue;
            }
            //log.info("相机ID={} 有事件数量{}", cameraId,list.size());
            for (EventVO e : list) {
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
                newEventEntity.setEventTime(Date.from(eventTime.atZone(ZoneId.systemDefault()).toInstant()));
                newEventEntity.setEventType(eventType);
                newEventEntity.setEventTypeValue(eventTypeValue);
                newEventEntity.setDescription(description);
                newEventEntity.setEventImagePath(imgFilePath == null ? "" : imgFilePath);
                // 加入批量list
                batchList.add(newEventEntity);
//
//                int affectRowCount = _eventDao.insert(newEventEntity);
//                if (affectRowCount > 0) {
//                    resp.success("Add new Event Data OK", newEventEntity.getEventId());
//                } else {
//                    resp.fail("Add new Event Data failed");
//                }
            }
        }
        if (!batchList.isEmpty()) {
            _eventDao.batchInsert(batchList);
        }
        resp.success("事件上传成功");
        return resp;
    }

    @Override
    public List<RealtimeEventVO> getRealtimeEvent(CameraVO cameraVO) {
        List<RealtimeEventVO> result = new ArrayList<>();
        Integer cameraNumber = cameraVO.getAxCameraNumber();

        AITaskType[] taskTypes = new AITaskType[]{
                AITaskType.BOX, AITaskType.FACE, AITaskType.GARBAGE, AITaskType.PERSON
        };
        for (AITaskType t : taskTypes
        ) {
            AIResultWrapper aiResult = AXDataManager.getLatestAIResult(cameraNumber, t);
            if (aiResult.getAiResult() != AIBoxResultType.EMPTY) {
                RealtimeEventVO vo4Box = new RealtimeEventVO();
                vo4Box.setEventTime(aiResult.getEventTime());
                vo4Box.setEventType(t.toString().toLowerCase());
                vo4Box.setEventTypeValue(aiResult.getAiResult().getValue());
                vo4Box.setDescription(aiResult.getAiResult().getDescription());
                result.add(vo4Box);
            }
        }
        return result;
    }


    @Override
    public PagedData<HistoryEventVO> getHistoryEvent(List<Integer> deviceIdList, LocalDateTime startTime, LocalDateTime endTime, PagedParams pagedParams) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Event.COL_DEVICE_ID, deviceIdList);
        queryWrapper.between(Event.COL_EVENT_TIME, startTime, endTime);
        queryWrapper.orderByAsc(Event.COL_EVENT_ID);


        IPage<Event> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        iPage = _eventDao.selectPage(iPage, queryWrapper);

        PagedData<HistoryEventVO> data = new PagedData<>();
        data.setTotal(iPage.getTotal());
        if (iPage.getTotal() > 0) {
            List<HistoryEventVO> list = new ArrayList<>();
            for (Event evt : iPage.getRecords()) {
                list.add(new HistoryEventVO(evt));
            }
            data.setList(list);
        }
        return data;

    }

    @Override
    public PagedData<HistoryEventVO> queryHistoryEvent(PagedParams pagedParams, HistoryEventParams queryParams) {
        pagedParams.setStart((pagedParams.getPageNum() - 1) * pagedParams.getPageSize());
        List<HistoryEventVO> list = _eventDao.getTableList(pagedParams, queryParams);
        long total = _eventDao.getTableTotal(queryParams);
        PagedData<HistoryEventVO> data = new PagedData<>();
        data.setTotal(total);
        if (total > 0) {
            data.setList(list);
        }
        return data;
    }


    private String getImageName(int deviceId, int cameraId, String eventType, LocalDateTime time) {
        return deviceId + "-" + cameraId + "-" + eventType + "-" + time.format(formatter);
    }

    private String SaveEventImage(String imgFileName, String imageBase64) {
        if (StringUtils.isEmpty(imageBase64)) {
            return null;
        }
        String imgFormat = Common.IMAGE_FORMAT;
        String imgFilePath = Common.IMAGE_FOLDER_PATH + imgFileName + "." + imgFormat;

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
                if (resultValue > 0) {
                    return AIFaceResultType.FACE_PASS;
                }
                return AIFaceResultType.EMPTY;
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
