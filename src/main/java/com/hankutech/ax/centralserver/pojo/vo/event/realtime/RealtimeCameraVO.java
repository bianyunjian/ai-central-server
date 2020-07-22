package com.hankutech.ax.centralserver.pojo.vo.event.realtime;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RealtimeCameraVO {
    String cameraName;
    Integer cameraId;
    List<RealtimeEventVO> eventList;

    public RealtimeCameraVO() {
        eventList = new ArrayList<>();
    }

}
