package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.pojo.response.SelectOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "AI配置表单界面所需数据")
@Data
public class AiConfigFormVO {

    @Schema(description = "设备下拉选列表")
    private List<SelectOption> deviceOptions;

    @Schema(description = "相机下拉选列表")
    private List<SelectOption> cameraOptions;

}
