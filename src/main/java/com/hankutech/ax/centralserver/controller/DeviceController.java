package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.dao.model.Device;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.exception.InvalidParamException;
import com.hankutech.ax.centralserver.pojo.dto.DeviceConfigDTO;
import com.hankutech.ax.centralserver.pojo.query.DeviceQueryParams;
import com.hankutech.ax.centralserver.pojo.request.AbstractObjectRequest;
import com.hankutech.ax.centralserver.pojo.request.IntIdRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.AiTypeConfigVO;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Operation(summary = "根据ID获取设备")
    @GetMapping(path = "/{id}")
    public DeviceResponse getById(@PathVariable("id") @NotNull Integer id) {
        DeviceVO data = deviceService.getOneById(id);
        DeviceResponse response = new DeviceResponse();
        response.success("获取设备数据成功", data);
        return response;
    }

    @Operation(summary = "获取所有设备")
    @GetMapping(path = "/all")
    public DeviceListResponse getAll() {
        List<DeviceVO> data = deviceService.getAllList();
        DeviceListResponse response = new DeviceListResponse();
        response.success("获取设备列表成功", data);
        return response;
    }

    @Operation(summary = "分页查询设备")
    @PostMapping(path = "/table")
    public DevicePagedResponse queryTable(@RequestBody @Validated DeviceQueryRequest request) {
        PagedData<DeviceVO> data = deviceService.queryDeviceTable(request.getPagedParams(), request.getQueryParams());
        DevicePagedResponse response = new DevicePagedResponse();
        response.success("分页查询设备列表成功", data);
        return response;
    }

    @Operation(summary = "新增设备")
    @PostMapping(path = "/add")
    public DeviceResponse addDevice(@RequestBody @Validated DeviceAddRequest request)
            throws InvalidParamException, InvalidDataException {
        DeviceVO data = deviceService.addDevice(request.obtainValidData());
        DeviceResponse response = new DeviceResponse();
        response.success("新增设备成功", data);
        return response;
    }

    @Operation(summary = "修改设备")
    @PostMapping(path = "/update")
    public DeviceResponse updateDevice(@RequestBody @Validated DeviceUpdateRequest request)
            throws InvalidParamException, InvalidDataException {
        DeviceVO data = deviceService.updateDevice(request.obtainValidData());
        DeviceResponse response = new DeviceResponse();
        response.success("修改设备成功", data);
        return response;
    }


    @Operation(summary = "删除设备")
    @PostMapping(path = "/delete")
    public BaseResponse deleteDevice(@RequestBody @Validated IntIdRequest request) throws InvalidDataException {
        deviceService.deleteDevice(request.getId());
        BaseResponse response = new BaseResponse();
        response.success("删除设备成功");
        return response;
    }


    @Operation(summary = "获取算法类型列表")
    @GetMapping(path = "/aiTypes")
    public BaseResponse<Map<String, String>> getAiTypes() {
        BaseResponse<Map<String, String>> response = new BaseResponse<>();
        Map<String, String> data = new HashMap<>(4);
        data.put("box", "周转箱与安全防护检测算法");
        data.put("face", "人脸识别检测算法");
        data.put("garbage", "垃圾袋分类检测算法");
        data.put("person", "有无人员检测算法");
        response.success("获取算法类型列表成功", data);
        return response;
    }

    @Operation(summary = "配置设备")
    @PostMapping(path = "/config")
    public BaseResponse configDevice(@RequestBody @Validated DeviceConfigRequest request) throws InvalidDataException {
        DeviceConfigDTO dto = request.buildData();
        deviceService.configDevice(dto);
        BaseResponse response = new BaseResponse();
        response.success("设备配置成功");
        return response;
    }


    //==================================================================================================================

    @Schema(description = "设备响应数据")
    private static class DeviceResponse extends BaseResponse<DeviceVO> {
    }

    @Schema(description = "设备列表响应数据")
    private static class DeviceListResponse extends BaseResponse<List<DeviceVO>> {
    }

    @Schema(description = "设备分页响应数据")
    private static class DevicePagedResponse extends BaseResponse<PagedData<DeviceVO>> {
    }

    @Schema(description = "设备分页查询请求数据")
    private static class DeviceQueryRequest extends QueryRequest<DeviceQueryParams> {
    }

    @Schema(description = "新增设备请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class DeviceAddRequest extends AbstractObjectRequest<Device> {
        @NotBlank
        @Schema(description = "设备名称", example = "测试设备0001", required = true)
        private String name;

        @NotNull
        @Schema(description = "设备状态", example = "0", required = true)
        private Integer status;

        @NotBlank
        @Schema(description = "设备使用场景", example = "1", required = true)
        private String scenario;

        @Schema(description = "设备说明", example = "三楼拐角")
        private String description;

        @NotNull
        @Schema(description = "PLC ID", example = "001")
        private Integer axPlcId;

        @NotNull
        @Schema(description = "安卓APP ID", example = "002")
        private Integer appId;

        @NotNull
        @Schema(description = "设备分组ID", example = "10")
        private Integer deviceGroupId;

        @Override
        protected void validate() throws InvalidParamException {
        }

        @Override
        protected void format() throws InvalidParamException {
        }

        @Override
        protected Device buildData() {
            Device model = new Device();
            model.setDeviceName(this.name);
            model.setStatus(this.status);
            model.setDeviceScenario(this.scenario);
            model.setDescription(this.description);
            model.setAxPlcId(this.axPlcId);
            model.setAppId(this.appId);
            model.setDeviceGroupId(this.deviceGroupId);
            return model;
        }
    }

    @Schema(description = "修改设备请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class DeviceUpdateRequest extends DeviceAddRequest {
        @NotNull
        @Schema(description = "设备ID", example = "1", required = true)
        private Integer id;

        @Override
        protected Device buildData() {
            Device model = super.buildData();
            model.setDeviceId(this.id);
            return model;
        }
    }

    @Schema(description = "配置设备请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class DeviceConfigRequest extends AbstractObjectRequest<DeviceConfigDTO> {

        @NotNull
        @Schema(description = "设备ID", example = "1", required = true)
        private Integer deviceId;

        @Valid
        @NotNull
        @Schema(description = "检测算法类型列表", required = true)
        private List<AiTypeConfigVO> aiConfig;


        @Override
        protected void validate() throws InvalidParamException {

        }

        @Override
        protected void format() throws InvalidParamException {

        }

        @Override
        protected DeviceConfigDTO buildData() {
            DeviceConfigDTO dto = new DeviceConfigDTO();
            dto.setDeviceId(this.deviceId);
            dto.setAiTypeConfig(this.aiConfig);
            return dto;
        }
    }

}
