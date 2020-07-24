package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.pojo.query.AiConfigQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.request.QueryParams;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigVO;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备&相机DAO
 *
 * @author ZhangXi
 */
@Mapper
public interface DeviceCameraDao extends BaseMapper<DeviceCamera> {


    void batchInsertList(List<DeviceCamera> list);


    List<AiConfigVO> queryAiConfigList(@Param("page") PagedParams pagedParams, @Param("query") AiConfigQueryParams queryParams);


    long queryAiConfigTotal(@Param("query") AiConfigQueryParams queryParams);


    AiConfigVO getAiConfigById(@Param("id") String id);

}
