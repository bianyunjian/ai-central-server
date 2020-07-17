package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.vo.DeviceCameraConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    private DeviceDao deviceDao;
    @Resource
    private CameraDao cameraDao;
    @Resource
    private DeviceCameraDao deviceCameraDao;

    @Override
    public DeviceConfigVO getDeviceConfig(String deviceName) {

        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Device.COL_DEVICE_NAME, deviceName);
        Device device = deviceDao.selectOne(queryWrapper);
        if (device != null) {
            DeviceConfigVO data = new DeviceConfigVO();
            data.setDeviceName(device.getDeviceName());
            data.setDeviceId(device.getDeviceId());

            QueryWrapper<DeviceCamera> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq(DeviceCamera.COL_DEVICE_ID, device.getDeviceId());

            List<DeviceCamera> deviceCameraList = deviceCameraDao.selectList(queryWrapper2);
            if (deviceCameraList != null && deviceCameraList.size() > 0) {
                ArrayList<DeviceCameraConfigVO> list = new ArrayList<>();
                for (DeviceCamera dc : deviceCameraList
                ) {

                    Camera camera = cameraDao.selectById(dc.getCameraId());
                    DeviceCameraConfigVO newVO = new DeviceCameraConfigVO();
                    newVO.setName(camera.getCameraName());
                    newVO.setId(camera.getCameraId());
                    newVO.setRtsp(camera.getRtspUrl());
                    if (StringUtils.isEmpty(dc.getAiTypeArray()) == false) {
                        newVO.setAi(dc.getAiTypeArray().split(","));
                    }
                    list.add(newVO);
                }
                data.setDeviceCameraConfigList(list);
            }
            return data;
        }

        return null;
    }

    @Override
    public List<DeviceVO> getDeviceList(DeviceQueryParams request) {
        return null;
    }
}
