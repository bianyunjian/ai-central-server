package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.pojo.query.DeviceParams;
import com.hankutech.ax.centralserver.pojo.query.HistoryEventParams;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.pojo.vo.event.history.HistoryEventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.realtime.RealtimeDeviceEventVO;
import com.hankutech.ax.centralserver.pojo.vo.event.realtime.RealtimeEventVO;
import com.hankutech.ax.centralserver.service.CameraService;
import com.hankutech.ax.centralserver.service.DeviceService;
import com.hankutech.ax.centralserver.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询接口
 */
@Validated
@Tag(name = "/event", description = "数据查询接口")
@Slf4j
@RequestMapping(path = "/event")
@RestController
public class EventController {
    private CameraService _cameraService;
    private DeviceService _deviceService;
    private EventService _eventService;

    public EventController(EventService eventService, DeviceService deviceService, CameraService cameraService) {
        this._eventService = eventService;
        this._deviceService = deviceService;
        this._cameraService = cameraService;
    }


    @Operation(summary = "当前实时识别结果数据查询")
    @PostMapping(path = "/getRealtimeEvent")
    public BaseResponse<List<RealtimeDeviceEventVO>> getRealtimeEvent(@RequestBody @Validated DeviceParams request) {
        BaseResponse<List<RealtimeDeviceEventVO>> resp = new BaseResponse<>();
        resp.setData(new ArrayList<>());
        List<DeviceVO> deviceList = _deviceService.getDeviceListByName(request.getDeviceName());
        if (deviceList != null && deviceList.size() > 0) {

            for (DeviceVO dvo : deviceList
            ) {
                RealtimeDeviceEventVO newData = new RealtimeDeviceEventVO();
                newData.setDeviceId(dvo.getId());
                newData.setDeviceName(dvo.getName());
                resp.getData().add(newData);

                List<RealtimeEventVO> eventList = _eventService.getRealtimeEvent(dvo.getId());
                newData.setEventList(eventList);
            }
        }
        return resp;
    }


    @Operation(summary = "历史数据查询")
    @PostMapping(path = "/getHistoryEvent")
    public BaseResponse<PagedData<HistoryEventVO>> getHistoryEvent(@RequestBody @Validated QueryRequest<HistoryEventParams> request) {
        BaseResponse<PagedData<HistoryEventVO>> resp = new BaseResponse<>();

//        List<DeviceVO> deviceVOList = _deviceService.getDeviceListByName(request.getQueryParams().getDeviceName());
//        if (deviceVOList != null && deviceVOList.size() > 0) {
//            List<Integer> deviceIdList = deviceVOList.stream().map(t -> t.getId()).collect(Collectors.toList());
//            PagedData<HistoryEventVO> data = _eventService.getHistoryEvent(deviceIdList, request.getQueryParams().getStartTime(), request.getQueryParams().getEndTime(), request.getPagedParams());
//
//            if (data != null && data.getList() != null && !data.getList().isEmpty()) {
//                List<Integer> cameraIdList = data.getList().stream().map(t -> t.getCameraId()).distinct().collect(Collectors.toList());
//
//                List<CameraVO> cameraVOList = _cameraService.getCameraListById(cameraIdList);
//
//                for (HistoryEventVO h : data.getList()
//
//                ) {
//                    String deviceName = deviceVOList.stream().filter(t -> t.getId() == h.getDeviceId()).findFirst().get().getName();
//                    String cameraName = cameraVOList.stream().filter(t -> t.getId() == h.getCameraId()).findFirst().get().getName();
//                    h.setDeviceName(deviceName);
//                    h.setCameraName(cameraName);
//                }
//            }
//
//            resp.success("OK", data);
//        }
        PagedData<HistoryEventVO> data = _eventService.queryHistoryEvent(request.getPagedParams(), request.getQueryParams());
        resp.success("分页查询历史事件数据成功", data);
        return resp;
    }

}
