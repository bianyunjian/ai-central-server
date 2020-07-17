package com.hankutech.ax.centralserver.pojo.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DeviceConfigVO {
    private Integer deviceId;
    private String deviceName;
    private ArrayList<DeviceCameraConfigVO> deviceCameraConfigList;
}
