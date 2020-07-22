package com.hankutech.ax.centralserver.pojo.vo.event.history;

import com.hankutech.ax.centralserver.dao.model.Event;
import lombok.Data;

@Data
public class HistoryEventVO extends Event {
    String deviceName;
    String cameraName;

    public HistoryEventVO(Event t) {
        this.setEventId(t.getEventId());
        this.setDeviceId(t.getDeviceId());
        this.setCameraId(t.getCameraId());
        this.setEventType(t.getEventType());
        this.setEventTypeValue(t.getEventTypeValue());
        this.setEventTime(t.getEventTime());
        this.setDescription(t.getDescription());
        this.setEventImagePath(t.getEventImagePath());
    }
}
