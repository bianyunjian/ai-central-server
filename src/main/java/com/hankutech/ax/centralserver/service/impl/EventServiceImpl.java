package com.hankutech.ax.centralserver.service.impl;

import com.hankutech.ax.centralserver.dao.EventDao;
import com.hankutech.ax.centralserver.dao.model.Event;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.CameraEventVO;
import com.hankutech.ax.centralserver.pojo.vo.EventVO;
import com.hankutech.ax.centralserver.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EventServiceImpl implements EventService {

    @Resource
    private EventDao _eventDao;

    @Override
    public BaseResponse handleUploadData(DeviceUploadParams request) {
        BaseResponse resp = new BaseResponse();
        String time = request.getTime();
        String deviceName = request.getDeviceName();
        for (CameraEventVO ev :
                request.getCameraList()) {

            String cameraName = ev.getName();

            for (EventVO e :
                    ev.getEvents()) {
                String aiTaskType = e.getType();
                String aiResultValue = e.getValue();
                String imageBase64 = e.getImageBase64();


                Event newEventEntity = new Event();
//                newEventEntity.setCameraId();
//                newEventEntity.setDeviceId();
//                newEventEntity.setEventTime(time);
//                newEventEntity.setEventType();
//                newEventEntity.setEventTypeValue();
//                newEventEntity.setDescription("");


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
}
