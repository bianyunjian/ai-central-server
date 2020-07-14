package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DeviceConfigVO {
    String deviceName;
    ArrayList<DeviceCameraConfigVO> deviceCameraConfigList;
}
