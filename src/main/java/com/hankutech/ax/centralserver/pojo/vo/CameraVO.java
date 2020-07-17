package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Camera;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 相机VO
 *
 * @author ZhangXi
 */
@Schema(description = "相机数据")
@Data
public class CameraVO {

    @Schema(description = "相机ID", example = "1")
    private Integer id;

    @Schema(description = "摄像头名称", example = "测试相机")
    private String name;

    @Schema(description = "RTSP地址", example = "rtsp://192.168.1.234")
    private String rtsp;

    public CameraVO(Camera camera) {
        if (null != camera) {
            this.id = camera.getCameraId();
            this.name = camera.getCameraName();
            this.rtsp = camera.getRtspUrl();
        }
    }

}
