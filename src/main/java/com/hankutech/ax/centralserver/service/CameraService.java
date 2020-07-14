package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.pojo.query.CameraQueryParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.PagedData;

import java.util.List;

/**
 * @author ZhangXi
 */
public interface CameraService {


    CameraVO getOneById(Integer id);

    List<CameraVO> getAllList();

    PagedData<CameraVO> queryCameraTable(PagedParams pagedParams, CameraQueryParams queryParams);


    CameraVO addCamera(Camera one) throws InvalidDataException;


    CameraVO updateCamera(Camera updateOne) throws InvalidDataException;


    void deleteCamera(Integer id) throws InvalidDataException;


}
