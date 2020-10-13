package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.query.HistoryEventParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.event.history.HistoryEventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.realtime.RealtimeEventVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface EventService {
    BaseResponse handleUploadData(DeviceUploadParams request) throws InvalidDataException;

    List<RealtimeEventVO> getRealtimeEvent(int deviceId);

    PagedData<HistoryEventVO> getHistoryEvent(List<Integer> deviceIdList, LocalDateTime startTime, LocalDateTime endTime, PagedParams pagedParams);


    PagedData<HistoryEventVO> queryHistoryEvent(PagedParams pagedParams, HistoryEventParams queryParams);


    HashMap<String, String> getCurrentFaceResult(String deviceName);
}
