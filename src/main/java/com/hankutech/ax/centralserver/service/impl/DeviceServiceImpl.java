package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankutech.ax.centralserver.constant.DataECoder;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.DevicePersonDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.vo.DeviceCameraConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangXi
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceDao deviceDao;
    @Resource
    private DeviceCameraDao deviceCameraDao;
    @Resource
    private DevicePersonDao devicePersonDao;
    @Resource
    private CameraDao cameraDao;

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


    private Device needExistedAndReturn(Integer id) throws InvalidDataException {
        // 检测设备数据是否存在
        Device oldOne = null;
        if (null != id) {
            oldOne = deviceDao.selectById(id);
        }
        if (null == oldOne) {
            throw new InvalidDataException(
                    MessageFormat.format("设备ID={0} 数据不存在", id)
            ).with(DataECoder.DEVICE_NOT_EXIST);
        }
        return oldOne;
    }


    private DeviceServiceImpl needNameNotRepeated(String name) throws InvalidDataException {
        // 检测设备名称是否重复
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Device.COL_DEVICE_NAME, name);
        Device repeatedOne = deviceDao.selectOne(queryWrapper);
        if (null != repeatedOne) {
            throw new InvalidDataException(
                    MessageFormat.format("设备名称：{0} 重复", name)
            ).with(DataECoder.DEVICE_REPEAT_NAME);
        }
        return this;
    }

}
