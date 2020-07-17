package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;

import java.util.List;

/**
 * 设备Service层实现类
 *
 * @author ZhangXi
 */
public interface DeviceService {


    DeviceConfigVO getDeviceConfig(String deviceName);

    List<DeviceVO> getDeviceList(DeviceQueryParams request);


    /**
     * 根据ID获取设备
     * @param id 设备ID
     * @return {@link DeviceVO}
     */
    DeviceVO getOneById(Integer id);

    /**
     * 获取所有设备列表
     * @return {@link List<DeviceVO>}
     */
    List<DeviceVO> getAllList();

    /**
     * 分页查询设备
     * @param pagedParams {@link PagedParams}
     * @param queryParams {@link DeviceQueryParams}
     * @return {@link PagedData<DeviceVO>}
     */
    PagedData<DeviceVO> queryDeviceTable(PagedParams pagedParams, DeviceQueryParams queryParams);

    /**
     * 新增设备
     * @param newOne {@link Device}
     * @return {@link DeviceVO}
     * @throws InvalidDataException 数据异常
     */
    DeviceVO addDevice(Device newOne) throws InvalidDataException;

    /**
     * 修改设备
     * @param updateOne {@link Device}
     * @return {@link DeviceVO}
     * @throws InvalidDataException 数据异常
     */
    DeviceVO updateDevice(Device updateOne) throws InvalidDataException;

    /**
     * 删除设备
     * @param id 设备ID
     */
    void deleteDevice(Integer id) throws InvalidDataException;

}
