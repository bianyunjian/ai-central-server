package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.bizmessage.DeviceRelationManager;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.request.DeviceGarbageTypeConfigRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.DeviceVO;
import com.hankutech.ax.centralserver.service.DeviceService;
import com.hankutech.ax.message.code.AIGarbageResultType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 运行时设置接口
 */
@Validated
@Tag(name = "/runtime", description = "运行时设置接口")
@RequestMapping(path = "/runtime")
@RestController
public class RuntimeController {
    private final DeviceService deviceService;

    @Autowired
    public RuntimeController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Operation(summary = "获取垃圾投递类型列表")
    @GetMapping(path = "/getGarbageTypeList")
    public BaseResponse<List<AIGarbageResultTypeConfig>> getAiTypes() {
        BaseResponse<List<AIGarbageResultTypeConfig>> response = new BaseResponse<>();
        List<AIGarbageResultTypeConfig> data = new ArrayList<>(5);
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.DRY));
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.WET));
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.RECYCLABLE));
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.HAZARDOUS));
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.WHITE_QUILT));
        data.add(new AIGarbageResultTypeConfig(AIGarbageResultType.BLUE));

        response.success("获取垃圾投递类型列表成功", data);
        return response;
    }

    @Operation(summary = "获取设备当前支持的垃圾投递类型")
    @PostMapping(path = "/getDeviceGarbageTypeList")
    public BaseResponse getDeviceGarbageTypeList() throws InvalidDataException {

        ConcurrentHashMap<Integer, AIGarbageResultType> map = DeviceRelationManager.getDeviceGarbageTypeList();
        if (map == null || map.size() == 0) {
            List<DeviceVO> allDeviceList = deviceService.getAllList();
            for (DeviceVO dv : allDeviceList
            ) {
                DeviceRelationManager.updateDeviceGarbageType(dv.getId(), AIGarbageResultType.WET);
            }

            map = DeviceRelationManager.getDeviceGarbageTypeList();
        }

        List<DeviceGarbageTypeConfigRequest> list = new ArrayList<>();
        for (Integer deviceId : map.keySet()
        ) {
            DeviceGarbageTypeConfigRequest config = new DeviceGarbageTypeConfigRequest();
            config.setDeviceId(deviceId);
            config.setDeviceName(deviceService.getOneById(deviceId).getName());
            config.setGarbageType(map.get(deviceId).getValue());
            config.setGarbageTypeDesc(map.get(deviceId).getDescription());
            list.add(config);
        }
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.success("获取设备当前支持的垃圾投递类型成功");
        return response;
    }

    @Operation(summary = "配置设备当前支持的垃圾投递类型")
    @PostMapping(path = "/updateDeviceGarbageType")
    public BaseResponse updateDeviceGarbageType(@RequestBody @Validated DeviceGarbageTypeConfigRequest request) throws InvalidDataException {

        if (request != null) {

            DeviceRelationManager.updateDeviceGarbageType(request.getDeviceId(), AIGarbageResultType.valueOf(request.getGarbageType()));
        }

        BaseResponse response = new BaseResponse();
        response.success("配置设备当前支持的垃圾投递类型成功");
        return response;
    }

    @Data
    public class AIGarbageResultTypeConfig {
        int value;
        String label;

        public AIGarbageResultTypeConfig(AIGarbageResultType t) {
            value = t.getValue();
            label = t.getDescription();
        }
    }
}
