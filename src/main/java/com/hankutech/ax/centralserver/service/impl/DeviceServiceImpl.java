package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankutech.ax.centralserver.constant.DataECoder;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.DevicePersonDao;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

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
