package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.exception.InvalidParamException;
import com.hankutech.ax.centralserver.pojo.query.AiConfigQueryParams;
import com.hankutech.ax.centralserver.pojo.request.AbstractObjectRequest;
import com.hankutech.ax.centralserver.pojo.request.BaseRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigFormVO;
import com.hankutech.ax.centralserver.pojo.vo.AiConfigVO;
import com.hankutech.ax.centralserver.service.AiConfigService;
import com.hankutech.ax.centralserver.tool.AiTypeTool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 算法配置
 *
 * @author ZhangXi
 */
@Slf4j
@Tag(name = "/ai-config", description = "算法配置接口")
@RestController
@RequestMapping(path = "/ai-config")
public class AiConfigController {

    private final AiConfigService aiConfigService;

    @Autowired
    public AiConfigController(AiConfigService aiConfigService) {
        this.aiConfigService = aiConfigService;
    }

    @Operation(summary = "获取表单预备数据")
    @GetMapping(path = "/getFormData")
    public AiConfigFormResponse getFormData() {
        AiConfigFormVO data = aiConfigService.getFormPreparedData();
        AiConfigFormResponse response = new AiConfigFormResponse();
        response.success("获取表单数据成功", data);
        return response;
    }

    @Operation(summary = "分页查询算法配置")
    @PostMapping(path = "/table")
    public AiConfigPagedResponse queryAiConfigTable(@RequestBody @Validated AiConfigQueryRequest request) {
        PagedData<AiConfigVO> data = aiConfigService.queryAiConfigTable(request.getPagedParams(), request.getQueryParams());
        AiConfigPagedResponse response = new AiConfigPagedResponse();
        response.success("分页查询算法配置成功", data);
        return response;
    }

    @Operation(summary = "新增算法配置")
    @PostMapping(path = "/add")
    public AiConfigResponse addAiConfig(@RequestBody @Validated AiConfigAddRequest request)
            throws InvalidParamException, InvalidDataException {
        AiConfigVO data = aiConfigService.addAiConfig(request.obtainValidData());
        AiConfigResponse response = new AiConfigResponse();
        response.success("新增算法配置成功", data);
        return response;
    }

    @Operation(summary = "修改算法配置")
    @PostMapping(path = "/update")
    public AiConfigResponse updateAiConfig(@RequestBody @Validated AiConfigUpdateRequest request)
            throws InvalidParamException, InvalidDataException {
        AiConfigVO data = aiConfigService.updateAiConfig(request.obtainValidData());
        AiConfigResponse response = new AiConfigResponse();
        response.success("修改算法配置成功", data);
        return response;
    }

    @Operation(summary = "删除算法配置")
    @PostMapping(path = "/delete")
    public BaseResponse deleteAiConfig(@RequestBody @Validated StringIdRequest request)
            throws InvalidDataException {
        aiConfigService.deleteAiConfig(request.getId());
        BaseResponse response = new BaseResponse();
        response.success("删除算法配置成功");
        return response;
    }


    //==================================================================================================================


    private static class AiConfigResponse extends BaseResponse<AiConfigVO> {}

    private static class AiConfigPagedResponse extends BaseResponse<PagedData<AiConfigVO>> {}

    private static class AiConfigFormResponse extends BaseResponse<AiConfigFormVO> {}

    @Schema(description = "分页查询算法配置请求")
    private static class AiConfigQueryRequest extends QueryRequest<AiConfigQueryParams> {}

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(description = "新增算法配置请求")
    private static class AiConfigAddRequest extends AbstractObjectRequest<DeviceCamera> {
        @NotNull
        @Min(value = 1)
        @Schema(description = "设备ID", example = "1", required = true)
        private Integer deviceId;

        @NotNull
        @Min(value = 1)
        @Schema(description = "相机ID", example = "1", required = true)
        private Integer cameraId;

        @NotNull
        @Schema(description = "算法类型数组", example = "[\"box\", \"person\"]", required = true)
        private String[] aiTypeArray;

        @Override
        protected void validate() throws InvalidParamException {
            // todo 校验算法类型是否符合
        }

        @Override
        protected void format() throws InvalidParamException {}

        @Override
        protected DeviceCamera buildData() {
            DeviceCamera model = new DeviceCamera();
            model.setDeviceId(deviceId);
            model.setCameraId(cameraId);
            model.setAiTypeArray(AiTypeTool.transAiTypesToString(aiTypeArray));
            return model;
        }
    }

    @Schema(description = "修改算法配置请求")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class AiConfigUpdateRequest extends AiConfigAddRequest {
        @NotBlank
        @Schema(description = "配置项ID", example = "2e548651-939b-4951-a7a7-c2a7e562c0f7", required = true)
        private String id;

        @Override
        protected DeviceCamera buildData() {
            DeviceCamera model = super.buildData();
            model.setId(id);
            return model;
        }
    }

    @Schema(description = "字符串ID请求")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class StringIdRequest extends BaseRequest {
        @NotBlank
        @Schema(description = "数据ID", example = "3e2b8770-64c0-476b-b9d1-4e1d65732997", required = true)
        private String id;
    }

}
