package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.dao.model.Camera;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.exception.InvalidParamException;
import com.hankutech.ax.centralserver.pojo.query.CameraQueryParams;
import com.hankutech.ax.centralserver.pojo.request.AbstractObjectRequest;
import com.hankutech.ax.centralserver.pojo.request.IntIdRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.CameraVO;
import com.hankutech.ax.centralserver.service.CameraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 相机控制器
 *
 * @author ZhangXi
 */
@Validated
@Tag(name = "/camera", description = "相机接口")
@Slf4j
@RequestMapping(path = "/camera")
@RestController
public class CameraController {

    private final CameraService cameraService;

    @Autowired
    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @Operation(summary = "根据ID获取相机")
    @GetMapping(path = "/{id}")
    public CameraResponse getById(@PathVariable("id") @NotNull Integer id) {
        CameraVO data = cameraService.getOneById(id);
        CameraResponse response = new CameraResponse();
        response.success("获取相机数据成功", data);
        return response;
    }

    @Operation(summary = "获取所有相机")
    @GetMapping(path = "/all")
    public CameraListResponse getAll() {
        List<CameraVO> data = cameraService.getAllList();
        CameraListResponse response = new CameraListResponse();
        response.success("获取所有相机成功", data);
        return response;
    }

    @Operation(summary = "分页查询相机")
    @PostMapping(path = "/table")
    public CameraPagedResponse queryCameraTable(@RequestBody @Validated CameraQueryRequest request) {
        PagedData<CameraVO> data = cameraService.queryCameraTable(request.getPagedParams(), request.getQueryParams());
        CameraPagedResponse response = new CameraPagedResponse();
        response.success("分页查询相机成功", data);
        return response;
    }

    @Operation(summary = "新增相机")
    @PostMapping(path = "/add")
    public CameraResponse addCamera(@RequestBody @Validated CameraAddRequest request)
            throws InvalidParamException, InvalidDataException {
        Camera model = request.obtainValidData();
        CameraVO data = cameraService.addCamera(model);
        CameraResponse response = new CameraResponse();
        response.success("新增相机成功", data);
        return response;
    }

    @Operation(summary = "修改相机")
    @PostMapping(path = "/update")
    public CameraResponse updateCamera(@RequestBody @Validated CameraUpdateRequest request)
            throws InvalidParamException, InvalidDataException {
        Camera model = request.obtainValidData();
        CameraVO data = cameraService.updateCamera(model);
        CameraResponse response = new CameraResponse();
        response.success("修改相机成功", data);
        return response;
    }


    @Operation(summary = "删除相机")
    @PostMapping(path = "/delete")
    public BaseResponse deleteCamera(@RequestBody @Validated IntIdRequest request) throws InvalidDataException {
        Integer id = request.getId();
        cameraService.deleteCamera(id);
        BaseResponse response = new BaseResponse();
        response.success("删除相机成功");
        return response;
    }


    //==================================================================================================================

    @Schema(description = "相机响应数据")
    private static class CameraResponse extends BaseResponse<CameraVO> {
    }

    @Schema(description = "相机列表响应数据")
    private static class CameraListResponse extends BaseResponse<List<CameraVO>> {
    }

    @Schema(description = "相机分页列表响应数据")
    private static class CameraPagedResponse extends BaseResponse<PagedData<CameraVO>> {
    }

    @Schema(description = "新增相机请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class CameraAddRequest extends AbstractObjectRequest<Camera> {

        @NotBlank
        @Schema(description = "相机名称", example = "D栋三楼通道左侧", required = true)
        private String name;

        @NotBlank
        @Schema(description = "rtsp连接地址", example = "rtsp://192.168.1.234", required = true)
        private String rtsp;

        @Schema(description = "相机说明", example = "三楼楼梯口")
        private String description;

        @Override
        protected void validate() throws InvalidParamException {
            //todo
        }

        @Override
        protected void format() throws InvalidParamException {
            //todo 剔除空格?
        }

        @Override
        protected Camera buildData() {
            Camera model = new Camera();
            model.setCameraName(this.name);
            model.setRtspUrl(this.rtsp);
            model.setDescription(this.description);
            return model;
        }
    }

    @Schema(description = "修改相机请求数据")
    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class CameraUpdateRequest extends CameraAddRequest {

        @NotNull
        @Schema(description = "相机ID", example = "1", required = true)
        private Integer id;

        @Override
        protected Camera buildData() {
            Camera model = super.buildData();
            model.setCameraId(this.id);
            return model;
        }
    }

    @Schema(description = "相机查询请求数据")
    private static class CameraQueryRequest extends QueryRequest<CameraQueryParams> {
    }

}
