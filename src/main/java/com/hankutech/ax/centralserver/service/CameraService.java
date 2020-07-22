package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.CameraQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;

import java.util.List;

/**
 * 相机数据Service层接口
 *
 * @author ZhangXi
 */
public interface CameraService {

    /**
     * 根据ID获取相机
     *
     * @param id 相机ID
     * @return {@link CameraVO}
     */
    CameraVO getOneById(Integer id);

    /**
     * 获取所有相机数据
     *
     * @return {@link List<CameraVO>}
     */
    List<CameraVO> getAllList();

    /**
     * 分页查询相机数据
     *
     * @param pagedParams {@link PagedParams}
     * @param queryParams {@link CameraQueryParams}
     * @return {@link PagedData<CameraVO>}
     */
    PagedData<CameraVO> queryCameraTable(PagedParams pagedParams, CameraQueryParams queryParams);

    /**
     * 新增相机
     *
     * @param newOne {@link Camera}
     * @return {@link CameraVO}
     * @throws InvalidDataException 数据异常
     */
    CameraVO addCamera(Camera newOne) throws InvalidDataException;

    /**
     * 修改相机
     *
     * @param updateOne {@link Camera}
     * @return {@link CameraVO}
     * @throws InvalidDataException 数据异常
     */
    CameraVO updateCamera(Camera updateOne) throws InvalidDataException;

    /**
     * 删除相机
     *
     * @param id 相机ID
     * @throws InvalidDataException 数据异常
     */
    void deleteCamera(Integer id) throws InvalidDataException;

    List<CameraVO> getCameraListByDeviceId(Integer deviceId);

    List<CameraVO> getCameraListById(List<Integer> cameraIdList);
}
