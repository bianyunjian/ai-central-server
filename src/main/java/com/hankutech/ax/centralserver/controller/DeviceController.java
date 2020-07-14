package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.pojo.query.DeviceParams;
import com.hankutech.ax.centralserver.pojo.query.DeviceUploadParams;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.DeviceCameraConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * lot端边缘设备接口
 */
@Validated
@Tag(name = "/device", description = "lot端边缘设备接口")
@Slf4j
@RequestMapping(path = "/device")
@RestController
public class DeviceController {

    @Operation(summary = "边缘设备从中心服务器获取配置")
    @PostMapping(path = "/getDeviceConfig")
    public BaseResponse<DeviceConfigVO> getDeviceConfig(@RequestBody @Validated DeviceParams request) {
        BaseResponse<DeviceConfigVO> resp = new BaseResponse<>();

        DeviceConfigVO data = new DeviceConfigVO();
        data.setDeviceName("d001");
        ArrayList<DeviceCameraConfigVO> list = new ArrayList<>();
        DeviceCameraConfigVO newVO = new DeviceCameraConfigVO();
        newVO.setName("camera001");
        newVO.setId(1);
        newVO.setRtsp("rtsp://192.168.1.1/channel01");
        newVO.setAi(new String[]{"box", "face", "garbage", "person"});
        list.add(newVO);
        data.setDeviceCameraConfigList(list);
        resp.success("OK", data);
        return resp;
    }


    @Operation(summary = "边缘设备从中心服务器获取获取人脸库")
    @PostMapping(path = "/getPersonLibrary")
    public BaseResponse<PersonLibraryVO> getPersonLibrary(@RequestBody @Validated DeviceParams request) {
        BaseResponse<PersonLibraryVO> resp = new BaseResponse<>();

        PersonLibraryVO data = new PersonLibraryVO();
        data.setDeviceName("d001");
        ArrayList<PersonVO> list = new ArrayList<>();
        PersonVO newVO = new PersonVO();
        newVO.setId(10001);
        newVO.setName("messy");
        newVO.setFaceFtrArray(new float[]{-1.00f, 0f, 1f, 1.5f, 2.00001f, 4.02f});
        list.add(newVO);
        data.setPersonList(list);
        resp.success("OK", data);
        return resp;
    }


    @Operation(summary = "边缘设备上传识别结果")
    @PostMapping(path = "/uploadData")
    public BaseResponse uploadData(@RequestBody @Validated DeviceUploadParams request) {
        BaseResponse resp = new BaseResponse<>();

        resp.success("OK", request);
        return resp;
    }

}
