package com.hankutech.ax.centralserver.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hankutech.ax.centralserver.dao.model.Camera;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 相机VO
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CameraVO extends Camera {

    @Schema(description = "相机ID", example = "1")
    @JsonProperty("id")
    @Override
    public Integer getCameraId() {
        return super.getCameraId();
    }

    @Schema(description = "摄像头名称", example = "测试相机")
    @JsonProperty(value = "name")
    @Override
    public String getCameraName() {
        return super.getCameraName();
    }

    @Schema(description = "RTSP地址", example = "rtsp://192.168.1.234")
    @JsonProperty(value = "rtsp")
    @Override
    public String getRtspUrl() {
        return super.getRtspUrl();
    }


    public CameraVO(Camera camera) {
        if (null != camera) {
            this.setCameraId(camera.getCameraId());
            this.setCameraName(camera.getCameraName());
            this.setRtspUrl(camera.getRtspUrl());
        }
    }


}
