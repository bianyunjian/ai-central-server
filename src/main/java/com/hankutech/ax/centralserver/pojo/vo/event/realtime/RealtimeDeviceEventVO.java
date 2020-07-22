package com.hankutech.ax.centralserver.pojo.vo.event.realtime;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RealtimeDeviceEventVO {
    String deviceName;
    Integer deviceId;
    List<RealtimeCameraVO> cameraList;

    public RealtimeDeviceEventVO() {
        cameraList = new ArrayList<>();
    }

}
