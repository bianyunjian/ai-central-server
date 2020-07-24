package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.constant.ErrorCode;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.CameraQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.service.CameraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 相机Service层实现类
 *
 * @author ZhangXi
 */
@Service("cameraService")
public class CameraServiceImpl implements CameraService {

    @Resource
    private CameraDao cameraDao;
    @Resource
    private DeviceCameraDao deviceCameraDao;

    @Override
    public CameraVO getOneById(Integer id) {
        Camera camera = cameraDao.selectById(id);
        return null == camera ? null : new CameraVO(camera);
    }

    @Override
    public List<CameraVO> getAllList() {
        List<Camera> list = cameraDao.selectList(null);
        if (null != list && !list.isEmpty()) {
            List<CameraVO> result = new ArrayList<>();
            for (Camera camera : list) {
                result.add(new CameraVO(camera));
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public PagedData<CameraVO> queryCameraTable(PagedParams pagedParams, CameraQueryParams queryParams) {
        IPage<Camera> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Camera.COL_CAMERA_NAME, queryParams.getName());
        iPage = cameraDao.selectPage(iPage, queryWrapper);
        PagedData<CameraVO> data = new PagedData<>();
        data.setTotal(iPage.getTotal());
        if (iPage.getTotal() > 0) {
            List<CameraVO> list = new ArrayList<>();
            for (Camera camera : iPage.getRecords()) {
                list.add(new CameraVO(camera));
            }
            data.setList(list);
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CameraVO addCamera(Camera newOne) throws InvalidDataException {
        // 检测名称重复
        needNotRepeated(newOne);
        // 插入数据
        newOne.setCameraId(null);
        cameraDao.insert(newOne);
        return new CameraVO(newOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CameraVO updateCamera(Camera updateOne) throws InvalidDataException {
        // 检测待修改数据是否存在，名称是否重复
        needNotRepeated(updateOne).needExistedAndReturn(updateOne.getCameraId());
        // 修改数据
        cameraDao.updateById(updateOne);
        return new CameraVO(updateOne);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCamera(Integer id) throws InvalidDataException {
        // 检测待删除数据是否存在，是否有设备关联
        needNoneRelatedDevice(id).needExistedAndReturn(id);
        // 删除数据
        cameraDao.deleteById(id);
    }

    @Override
    public List<CameraVO> getCameraListByDeviceId(Integer deviceId) {
        QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeviceCamera.COL_DEVICE_ID, deviceId);
        List<DeviceCamera> dataList = deviceCameraDao.selectList(queryWrapper);
        if (dataList != null && dataList.size() > 0) {
            List<Integer> cameraIdList = dataList.stream().map(t -> t.getCameraId()).collect(Collectors.toList());
            return getCameraListById(cameraIdList);
        }

        return null;
    }

    @Override
    public List<CameraVO> getCameraListById(List<Integer> cameraIdList) {
        QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Camera.COL_CAMERA_ID, cameraIdList);
        List<Camera> cameraList = cameraDao.selectList(queryWrapper);
        if (cameraList != null && cameraList.size() > 0) {
            return cameraList.stream().map(t -> new CameraVO(t)).collect(Collectors.toList());
        }

        return null;
    }

    //==================================================================================================================


    protected CameraServiceImpl needNotRepeated(Camera camera) throws InvalidDataException {
        // 检测名称重复
        QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Camera.COL_CAMERA_NAME, camera.getCameraName());
        List<Camera> repeatList = cameraDao.selectList(queryWrapper);
        if (null != repeatList && !repeatList.isEmpty()) {
            if (repeatList.size() == 1
                    && camera.getCameraId() != null
                    && camera.getCameraId().equals(repeatList.get(0).getCameraId())) {
                return this;
            }
            throw new InvalidDataException(
                    MessageFormat.format("相机名称：{0} 重复!", camera.getCameraName())
            ).with(ErrorCode.CAMERA_REPEAT_NAME);
        }
        return this;
    }

    protected Camera needExistedAndReturn(Integer id) throws InvalidDataException {
        // 检测数据是否存在
        Camera oldOne = null;
        if (null != id) {
            oldOne = cameraDao.selectById(id);
        }
        if (null == oldOne) {
            throw new InvalidDataException(
                    MessageFormat.format("相机ID={0} 不存在", id)
            ).with(ErrorCode.CAMERA_NOT_EXIST);
        }
        return oldOne;
    }

    protected CameraServiceImpl needNoneRelatedDevice(Integer id) throws InvalidDataException {
        // 检测相机是否关联设备
        QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeviceCamera.COL_CAMERA_ID, id);
        long count = deviceCameraDao.selectCount(queryWrapper);
        if (count > 0) {
            throw new InvalidDataException(
                    MessageFormat.format("相机ID={0} 有关联设备", id)
            ).with(ErrorCode.CAMERA_HAS_DEVICE);
        }
        return this;
    }

}
