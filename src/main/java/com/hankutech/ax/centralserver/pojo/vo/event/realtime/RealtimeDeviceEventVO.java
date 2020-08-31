package com.hankutech.ax.centralserver.pojo.vo.event.realtime;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RealtimeDeviceEventVO {
    String deviceName;
    Integer deviceId;
    List<RealtimeEventVO> eventList;

    public RealtimeDeviceEventVO() {
        eventList = new ArrayList<>();
    }

}
