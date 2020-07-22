package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.pojo.vo.event.history.HistoryEventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.realtime.RealtimeEventVO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    BaseResponse handleUploadData(DeviceUploadParams request);

    List<RealtimeEventVO> getRealtimeEvent(CameraVO cameraVO);

    List<HistoryEventVO> getHistoryEvent(List<Integer> deviceIdList, LocalDateTime startTime, LocalDateTime endTime);
}
