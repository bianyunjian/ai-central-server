package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.dao.model.Event;
import com.hankutech.ax.centralserver.exception.InvalidParamException;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.request.AbstractObjectRequest;
import com.hankutech.ax.centralserver.pojo.request.BaseRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.DeviceConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceWithPersonVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.PAData;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备控制器
 *
 * @author ZhangXi
 */
@Validated
@Tag(name = "/device", description = "设备接口")
@RequestMapping(path = "/device")
@RestController
public class DeviceController {

    @Operation(summary = "根据ID获取设备")
    @GetMapping(path = "/{id}")
    public DeviceResponse getById(@PathVariable("id") @NotNull Integer id) {
        //todo
        DeviceResponse response = new DeviceResponse();
        return response;
    }


    @Operation(summary = "获取所有设备")
    @GetMapping(path = "/all")
    public DeviceListResponse getAll() {
        //todo
        DeviceListResponse response = new DeviceListResponse();
        return response;
    }

    @Operation(summary = "分页查询设备")
    @PostMapping(path = "/table")
    public DevicePagedResponse queryTable(@RequestBody @Validated DeviceQueryRequest request) {
        //todo
        DevicePagedResponse response = new DevicePagedResponse();
        return response;
    }

    @Operation(summary = "获取人脸库")
    @PostMapping(path = "/getPersonLibrary")
    public DeviceFaceResponse getPersonLibrary(@RequestBody @Validated DeviceNameRequest request) {
        //todo
        DeviceFaceResponse response = new DeviceFaceResponse();
        return response;
    }

    @Operation(summary = "获取设备配置信息")
    @PostMapping(path = "/getDeviceConfig")
    public DeviceConfigResponse getDeviceConfig(@RequestBody @Validated DeviceNameRequest request) {
        //todo
        DeviceConfigResponse response = new DeviceConfigResponse();
        return response;
    }


    @Operation(summary = "上传识别结果")
    @PostMapping(path = "/uploadData")
    public BaseResponse uploadRecognizeData(@RequestBody @Validated UploadDataRequest request) {
        //todo
        BaseResponse response = new BaseResponse();
        return response;
    }



















    @Schema(description = "设备响应数据")
    private static class DeviceResponse extends BaseResponse<DeviceVO> {}

    @Schema(description = "设备列表响应数据")
    private static class DeviceListResponse extends BaseResponse<List<DeviceVO>> {}

    @Schema(description = "设备分页响应数据")
    private static class DevicePagedResponse extends BaseResponse<PagedData<DeviceVO>> {}

    @Schema(description = "设备配置响应数据")
    private static class DeviceConfigResponse extends BaseResponse<DeviceConfigVO> {}

    @Schema(description = "带人脸库的设备响应数据")
    private static class DeviceFaceResponse extends BaseResponse<DeviceWithPersonVO> {}

    @Schema(description = "设备分页查询请求数据")
    private static class DeviceQueryRequest extends QueryRequest<DeviceQueryParams> {}

    @Schema(description = "设备名称请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class DeviceNameRequest extends BaseRequest {
        @NotBlank
        @Schema(description = "设备名称", example = "d001", required = true)
        private String deviceName;
    }

    @EqualsAndHashCode(callSuper = true)
    @Schema(description = "设备上传数据请求")
    @Data
    private static class UploadDataRequest extends AbstractObjectRequest<List<Event>> {
        @NotBlank
        @Schema(description = "设备名称", example = "d001", required = true)
        private String deviceName;
        @NotBlank
        @Schema(description = "数据上传时间", example = "2020-06-15 10:10:10,123", required = true)
        private String time;
        @Valid
        @Schema(description = "摄像头列表")
        private List<SimpleCamera> cameraList;

        @Override
        protected void validate() throws InvalidParamException {
            //todo 校验时间，事件类型等
        }

        @Override
        protected void format() throws InvalidParamException {

        }

        @Override
        protected List<Event> buildData() {
            List<Event> data = new ArrayList<>();
            if (null != cameraList && !cameraList.isEmpty()) {
                for (SimpleCamera camera : cameraList) {
                    String name = camera.getName();
                    for (SimpleEvent event : camera.getEvents()) {
                        Event e = new Event();
                    }
                }
            }
            return null;
        }
    }

    @Schema(description = "识别结果中的事件数据")
    @Data
    private static class SimpleEvent {
        @NotBlank
        @Schema(description = "事件类型", example = "box", required = true)
        private String type;
        @NotNull
        @Schema(description = "事件数值，具体数值需要根据type做匹配", example = "1", required = true)
        private Integer value;
    }

    @Schema(description = "识别结果中的摄像头数据")
    @Data
    private static class SimpleCamera {
        @NotBlank
        @Schema(description = "摄像头名称", example = "camera001", required = true)
        private String name;
        @Valid
        @Schema(description = "检测到的事件列表")
        private List<SimpleEvent> events;
    }








}
