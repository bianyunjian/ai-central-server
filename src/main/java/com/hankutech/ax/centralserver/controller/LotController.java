package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.DeviceParams;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.query.FaceResultQueryParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import com.hankutech.ax.centralserver.service.EventService;
import com.hankutech.ax.centralserver.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * lot端边缘设备接口
 */
@Validated
@Tag(name = "/lot", description = "lot端边缘设备接口")
@Slf4j
@RequestMapping(path = "/lot")
@RestController
public class LotController {

    private DeviceService _deviceService;
    private PersonService _personService;
    private EventService _eventService;

    public LotController(DeviceService deviceService, PersonService personService, EventService eventService) {
        this._deviceService = deviceService;
        this._personService = personService;
        this._eventService = eventService;
    }


    @Operation(summary = "边缘设备从中心服务器获取配置")
    @PostMapping(path = "/getDeviceConfig")
    public BaseResponse<DeviceConfigVO> getDeviceConfig(@RequestBody @Validated DeviceParams request) {
        BaseResponse<DeviceConfigVO> resp = new BaseResponse<>();

        List<String> aiCameraFilter = new ArrayList<>();
        if (StringUtils.isEmpty(request.getSupportAI())) {
            aiCameraFilter.add("person");
            aiCameraFilter.add("garbage");
        } else {
            aiCameraFilter.addAll(Arrays.stream(request.getSupportAI().trim().split(",")).collect(Collectors.toList()));
        }
        DeviceConfigVO data = _deviceService.getDeviceConfig(request.getDeviceName(), aiCameraFilter);
        resp.success("OK", data);
        return resp;
    }


    @Operation(summary = "边缘设备从中心服务器获取获取人脸库")
    @PostMapping(path = "/getPersonLibrary")
    public BaseResponse<PersonLibraryVO> getPersonLibrary(@RequestBody @Validated DeviceParams request) {
        BaseResponse<PersonLibraryVO> resp = new BaseResponse<>();
        PersonLibraryVO data = _personService.getPersonLibrary();

        resp.success("OK", data);

        return resp;
    }


    @Operation(summary = "边缘设备上传识别结果")
    @PostMapping(path = "/uploadData")
    public BaseResponse uploadData(@RequestBody @Validated DeviceUploadParams request) throws InvalidDataException {
        log.info("收到事件：{}", request.toString());
        BaseResponse resp = _eventService.handleUploadData(request);
        return resp;
    }

    @Operation(summary = "边缘设备从中心服务器获取当前人脸识别的结果")
    @PostMapping(path = "/getCurrentFaceResult")
    public BaseResponse getCurrentFaceResult(@RequestBody @Validated FaceResultQueryParams request) {
        BaseResponse<HashMap<String, String>> resp = new BaseResponse<>();
        HashMap<String, String> faceResult = _eventService.getCurrentFaceResult(request.getDeviceName());

        if (faceResult != null) {
            resp.success("found valid face", faceResult);
        } else {
            resp.fail("not found valid face");
        }
        return resp;
    }
}
