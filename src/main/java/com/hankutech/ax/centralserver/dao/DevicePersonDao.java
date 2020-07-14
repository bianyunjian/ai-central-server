package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.DevicePerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备&人员DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface DevicePersonDao extends BaseMapper<DevicePerson> {
}
