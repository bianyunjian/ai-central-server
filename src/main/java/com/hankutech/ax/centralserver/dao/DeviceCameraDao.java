package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备&相机DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface DeviceCameraDao extends BaseMapper<DeviceCamera> {


    void batchInsertList(List<DeviceCamera> list);



}
