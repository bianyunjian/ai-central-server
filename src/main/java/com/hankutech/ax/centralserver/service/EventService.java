package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;

public interface EventService {
    BaseResponse handleUploadData(DeviceUploadParams request);
}
