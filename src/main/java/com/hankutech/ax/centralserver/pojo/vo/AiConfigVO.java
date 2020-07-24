package com.hankutech.ax.centralserver.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AI算法配置VO
 */
@Schema(description = "设备算法配置")
@Data
public class AiConfigVO {

    @Schema(description = "配置ID", example = "2e548651-939b-4951-a7a7-c2a7e562c0f7")
    private String id;

    @Schema(description = "设备ID", example = "1")
    private Integer deviceId;

    @Schema(description = "设备名称", example = "测试设备")
    private String deviceName;

    @Schema(description = "相机ID", example = "1")
    private Integer cameraId;

    @Schema(description = "相机名称", example = "测试相机")
    private String cameraName;

    @Schema(description = "算法项", example = "box,garbage")
    private String aiTypeArray;

}
