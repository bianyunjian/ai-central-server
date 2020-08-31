package com.hankutech.ax.centralserver.pojo.vo.event.realtime;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RealtimeCameraVO {
    List<RealtimeEventVO> eventList;

    public RealtimeCameraVO() {
        eventList = new ArrayList<>();
    }

}
