package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.vo.CameraEventVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DeviceUploadParams {
    @Schema(description = "发生时间", example = "2020-07-17 15:00:00")
    private String time;
    @Schema(description = "发生的设备编号", example = "1")
    private int deviceId;
    private List<CameraEventVO> cameraList;
}
