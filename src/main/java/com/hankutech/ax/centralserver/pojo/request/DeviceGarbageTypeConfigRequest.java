package com.hankutech.ax.centralserver.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceGarbageTypeConfigRequest extends BaseRequest {


    int deviceId;
    int garbageType;

    String deviceName;
    String garbageTypeDesc;


}

