package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;

import java.util.List;

public interface DeviceService {
    DeviceConfigVO getDeviceConfig(String deviceName);


    List<DeviceVO> getDeviceList(DeviceQueryParams request);
}
