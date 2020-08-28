package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceDao extends BaseMapper<Device> {
}