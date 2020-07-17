package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.dao.model.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 相机DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface CameraDao extends BaseMapper<Camera> {

    /**
     *
     * @param deviceId
     * @return
     */
    List<CameraVO> getListByDeviceId(@Param("deviceId") Integer deviceId);



}
