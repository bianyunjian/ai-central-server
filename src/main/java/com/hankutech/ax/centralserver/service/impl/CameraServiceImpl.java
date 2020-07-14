package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.constant.DataECoder;
import com.hankutech.ax.centralserver.dao.CameraDao;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.service.CameraService;
import com.hankutech.ax.centralserver.pojo.query.CameraQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.vo.response.PagedData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 相机Service层
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
        //fixme 待增加分页查询参数
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
    public CameraVO addCamera(Camera one) throws InvalidDataException {
        // 检测名称重复
        needNameNotRepeated(one.getCameraName());
        // 插入数据
        one.setCameraId(null);
        cameraDao.insert(one);
        return new CameraVO(one);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CameraVO updateCamera(Camera updateOne) throws InvalidDataException {
        // 检测待修改数据是否存在，名称是否重复
        needNameNotRepeated(updateOne.getCameraName()).needExistedAndReturn(updateOne.getCameraId());
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



    protected CameraServiceImpl needNameNotRepeated(String name) throws InvalidDataException {
        // 检测名称重复
        QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Camera.COL_CAMERA_NAME, name);
        Camera repeatedOne = cameraDao.selectOne(queryWrapper);
        if (null != repeatedOne) {
            throw new InvalidDataException(
                    MessageFormat.format("相机名称：{0} 重复!", name)
            ).with(DataECoder.CAMERA_REPEAT_NAME);
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
            ).with(DataECoder.CAMERA_NOT_EXIST);
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
            ).with(DataECoder.CAMERA_HAS_DEVICE);
        }
        return this;
    }


}
