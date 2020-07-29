package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.constant.ErrorCode;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.DevicePersonDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.menum.DeviceStatus;
import com.hankutech.ax.centralserver.pojo.dto.DeviceConfigDTO;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.AiTypeConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceCameraConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import com.hankutech.ax.centralserver.tool.AiTypeTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ZhangXi
 */
@Slf4j
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
                    newVO.setAxCameraNumber(camera.getAxCameraNumber());

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

    @Override
    public DeviceVO getOneById(Integer id) {
        Device data = deviceDao.selectById(id);
        return null == data ? null : new DeviceVO(data);
    }

    @Override
    public List<DeviceVO> getAllList() {
        List<Device> list = deviceDao.selectList(null);
        if (null != list && !list.isEmpty()) {
            List<DeviceVO> result = new ArrayList<>(list.size());
            for (Device device : list) {
                result.add(new DeviceVO(device));
            }
            return result;
        }
        return null;
    }

    @Override
    public PagedData<DeviceVO> queryDeviceTable(PagedParams pagedParams, DeviceQueryParams queryParams) {
        IPage<Device> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Device.COL_DEVICE_NAME, queryParams.getName());
        iPage = deviceDao.selectPage(iPage, queryWrapper);
        PagedData<DeviceVO> data = new PagedData<>();
        data.setTotal(iPage.getTotal());
        if (iPage.getTotal() > 0) {
            List<DeviceVO> list = new ArrayList<>();
            for (Device device : iPage.getRecords()) {
                list.add(new DeviceVO(device));
            }
            data.setList(list);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DeviceVO addDevice(Device newOne) throws InvalidDataException {
        // 检测设备名称是否重复
        needNotRepeated(newOne);
        // 插入数据
        deviceDao.insert(newOne);
        return new DeviceVO(newOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DeviceVO updateDevice(Device updateOne) throws InvalidDataException {
        // 检测设备名称是否重复，设备数据是否存在
        Device oldOne = needNotRepeated(updateOne).needExistedAndReturn(updateOne.getDeviceId());
        // 更改数据
        deviceDao.updateById(updateOne);
        return new DeviceVO(updateOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDevice(Integer id) throws InvalidDataException {
        // 检查设备是否存在
        needExistedAndReturn(id);
        // 删除设备
        QueryWrapper<DeviceCamera> delRelatedCameraQueryWrapper = new QueryWrapper<>();
        delRelatedCameraQueryWrapper.eq(DeviceCamera.COL_DEVICE_ID, id);
        deviceCameraDao.delete(delRelatedCameraQueryWrapper);
        // fixme 删除其他关联
        deviceDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void configDevice(DeviceConfigDTO configDTO) throws InvalidDataException {
        Integer deviceId = configDTO.getDeviceId();
        // 检测设备是否存在
        needExistedAndReturn(deviceId);
        // 重新匹配摄像头与对应算法
        List<AiTypeConfigVO> aiConfigs = configDTO.getAiTypeConfig();
        if (null != aiConfigs && !aiConfigs.isEmpty()) {
            List<DeviceCamera> listData = new ArrayList<>(aiConfigs.size());
            for (AiTypeConfigVO config : aiConfigs) {
                Integer cameraId = config.getCameraId();
                if (null == cameraId || null == cameraDao.selectById(cameraId)) {
                    log.warn("相机ID={} 数据不存在", cameraId);
                    continue;
                }
                DeviceCamera dc = new DeviceCamera();
                dc.setId(UUID.randomUUID().toString());
                dc.setDeviceId(deviceId);
                dc.setCameraId(cameraId);
                dc.setAiTypeArray(AiTypeTool.transAiTypesToString(config.getAiTypes()));
                listData.add(dc);
            }
            if (!listData.isEmpty()) {
                // 重新匹配关系
                QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(DeviceCamera.COL_DEVICE_ID, deviceId);
                deviceCameraDao.delete(queryWrapper);
                deviceCameraDao.batchInsertList(listData);
            }
        }
    }

    @Override
    public List<DeviceVO> getDeviceListByName(String deviceName) {

        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Device.COL_DEVICE_NAME, deviceName);
        List<Device> deviceList = deviceDao.selectList(queryWrapper);

        if (deviceList != null && deviceList.size() > 0) {
            return deviceList.stream().map(t -> {
                DeviceVO v = new DeviceVO(t);

                return v;
            }).collect(Collectors.toList());

        } else {
            return null;
        }
    }


    //==================================================================================================================


    protected Device needExistedAndReturn(Integer id) throws InvalidDataException {
        // 检测设备数据是否存在
        Device oldOne = null;
        if (null != id) {
            oldOne = deviceDao.selectById(id);
        }
        if (null == oldOne) {
            throw new InvalidDataException(
                    MessageFormat.format("设备ID={0} 数据不存在", id)
            ).with(ErrorCode.DEVICE_NOT_EXIST);
        }
        return oldOne;
    }


    protected DeviceServiceImpl needNotRepeated(Device device) throws InvalidDataException {
        // 检测设备名称是否重复
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Device.COL_DEVICE_NAME, device.getDeviceName());

        List<Device> repeatList = deviceDao.selectList(queryWrapper);
        if (null != repeatList && !repeatList.isEmpty()) {
            if (repeatList.size() == 1
                    && device.getDeviceId() != null
                    && device.getDeviceId().equals(repeatList.get(0).getDeviceId())) {
                return this;
            }
            throw new InvalidDataException(
                    MessageFormat.format("设备名称：{0} 重复", device.getDeviceName())
            ).with(ErrorCode.DEVICE_REPEAT_NAME);
        }
        return this;
    }


}
