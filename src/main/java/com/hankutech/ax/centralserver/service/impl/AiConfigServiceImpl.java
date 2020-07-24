package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankutech.ax.centralserver.constant.ErrorCode;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.DeviceDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.AiConfigQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.response.SelectOption;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigFormVO;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigVO;
import com.hankutech.ax.centralserver.service.AiConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("aiConfigService")
public class AiConfigServiceImpl implements AiConfigService {

    @Resource
    private CameraDao cameraDao;
    @Resource
    private DeviceDao deviceDao;
    @Resource
    private DeviceCameraDao deviceCameraDao;

    @Override
    public AiConfigFormVO getFormPreparedData() {
        AiConfigFormVO data = new AiConfigFormVO();
        List<Device> devices = deviceDao.selectList(null);
        if (null != devices && !devices.isEmpty()) {
            List<SelectOption> deviceOptions = new ArrayList<>(devices.size());
            for (Device device : devices) {
                deviceOptions.add(new SelectOption(device.getDeviceId().toString(), device.getDeviceName()));
            }
            data.setDeviceOptions(deviceOptions);
        }
        List<Camera> cameras = cameraDao.selectList(null);
        if (null != cameras && !cameras.isEmpty()) {
            List<SelectOption> cameraOptions = new ArrayList<>(cameras.size());
            for (Camera camera : cameras) {
                cameraOptions.add(new SelectOption(camera.getCameraId().toString(), camera.getCameraName()));
            }
            data.setCameraOptions(cameraOptions);
        }
        return data;
    }

    @Override
    public PagedData<AiConfigVO> queryAiConfigTable(PagedParams pagedParams, AiConfigQueryParams queryParams) {
        int start = (pagedParams.getPageNum()-1) * pagedParams.getPageSize();
        pagedParams.setStart(start);
        List<AiConfigVO> list = deviceCameraDao.queryAiConfigList(pagedParams, queryParams);
        long total = deviceCameraDao.queryAiConfigTotal(queryParams);
        PagedData<AiConfigVO> data = new PagedData<>();
        data.setList(list);
        data.setTotal(total);
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AiConfigVO addAiConfig(DeviceCamera deviceCamera) throws InvalidDataException {
        // 检查是否有重复
        //fixme 检查相机与摄像头id是否都存在
        needNotRepeatPair(deviceCamera);
        // 新增配置
        deviceCamera.setId(UUID.randomUUID().toString());
        deviceCameraDao.insert(deviceCamera);
        // 返回vo
        return deviceCameraDao.getAiConfigById(deviceCamera.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AiConfigVO updateAiConfig(DeviceCamera deviceCamera) throws InvalidDataException {
        // 检查是否存在，且是否已配置过匹配摄像头算法
        //fixme 检查相机与摄像头id是否都存在
        DeviceCamera oldOne = needNotRepeatPair(deviceCamera).needExistedAndReturn(deviceCamera.getId());
        // 更新配置
        oldOne.setAiTypeArray(deviceCamera.getAiTypeArray());
        deviceCameraDao.updateById(oldOne);
        // 返回vo
        return deviceCameraDao.getAiConfigById(oldOne.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAiConfig(String deviceCameraId) throws InvalidDataException {
        // 检测是否存在
        needExisted(deviceCameraId);
        // 删除数据
        deviceCameraDao.deleteById(deviceCameraId);
    }

    //==================================================================================================================

    protected AiConfigServiceImpl needExisted(String id) throws InvalidDataException {
        needExistedAndReturn(id);
        return this;
    }

    protected DeviceCamera needExistedAndReturn(String id) throws InvalidDataException {
        DeviceCamera existOne = deviceCameraDao.selectById(id);
        if (null == existOne) {
            throw new InvalidDataException(MessageFormat.format("算法配置不存在，id={0}", id))
                    .with(ErrorCode.AI_CONFIG_NOT_EXIST);
        }
        return existOne;
    }

    protected AiConfigServiceImpl needNotRepeatPair(DeviceCamera dc) throws InvalidDataException {
        QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeviceCamera.COL_DEVICE_ID, dc.getDeviceId());
        queryWrapper.eq(DeviceCamera.COL_CAMERA_ID, dc.getCameraId());
        List<DeviceCamera> repeatList = deviceCameraDao.selectList(queryWrapper);
        if (null != repeatList && !repeatList.isEmpty()) {
            if (repeatList.size() == 1 && null != dc.getId() && dc.getId().equals(repeatList.get(0).getId())) {
                // 排除自身
                return this;
            }
            throw new InvalidDataException(
                    MessageFormat.format("设备ID={0}已配置摄像头ID={1}算法", dc.getDeviceId(), dc.getCameraId())
            ).with(ErrorCode.AI_CONFIG_PAIR_REPEAT);
        }
        return this;
    }

}
