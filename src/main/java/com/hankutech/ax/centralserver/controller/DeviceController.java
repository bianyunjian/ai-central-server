package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备管理接口
 */
@Validated
@Tag(name = "/device", description = "设备管理接口")
@Slf4j
@RequestMapping(path = "/device")
@RestController
public class DeviceController {

    private DeviceService _deviceService;


    public DeviceController(DeviceService deviceService) {
        this._deviceService = deviceService;
    }

    @Operation(summary = "获取设备列表")
    @PostMapping(path = "/getDeviceList")
    public BaseResponse<List<DeviceVO>> getDeviceList(@RequestBody @Validated DeviceQueryParams request) {
        BaseResponse<List<DeviceVO>> resp = new BaseResponse<>();
        List<DeviceVO> data = _deviceService.getDeviceList(request);
        resp.success("OK", data);
        return resp;
    }


    // Add
    // Update
    // Delete
}
