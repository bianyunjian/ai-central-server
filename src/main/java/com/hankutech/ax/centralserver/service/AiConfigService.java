package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.AiConfigQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigFormVO;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigVO;

/**
 * 算法配置Service接口
 */
public interface AiConfigService {


    AiConfigFormVO getFormPreparedData();


    PagedData<AiConfigVO> queryAiConfigTable(PagedParams pagedParams, AiConfigQueryParams queryParams);


    AiConfigVO addAiConfig(DeviceCamera deviceCamera) throws InvalidDataException;


    AiConfigVO updateAiConfig(DeviceCamera deviceCamera) throws InvalidDataException;


    void deleteAiConfig(String deviceCameraId) throws InvalidDataException;

}
