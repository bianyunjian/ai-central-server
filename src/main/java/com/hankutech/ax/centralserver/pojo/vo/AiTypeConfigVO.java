package com.hankutech.ax.centralserver.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 摄像头算法类型配置
 *
 * @author ZhangXi
 */
@Schema(description = "摄像头算法类型配置")
@Data
public class AiTypeConfigVO {

    @NotNull
    @Schema(description = "AI算法类型列表", example = "[\"box\",\"face\"]", required = true)
    private String[] aiTypes;

    @NotNull
    @Schema(description = "相机ID", example = "0", required = true)
    private Integer cameraId;

}
