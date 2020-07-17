package com.hankutech.ax.centralserver.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;


@Schema(description = "带配置信息的设备数据")
@Data
public class DeviceConfigVO {

    private Integer deviceId;
    private String deviceName;
    private ArrayList<DeviceCameraConfigVO> deviceCameraConfigList;


}
