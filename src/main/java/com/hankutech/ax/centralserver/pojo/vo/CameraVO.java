package com.hankutech.ax.centralserver.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hankutech.ax.centralserver.dao.model.Camera;
import io.swagger.v3.oas.annotations.Hidden;
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

    @Schema(description = "设置的AI检测算法", example = "[\"box\",\"face\"]")
    private String[] ai;

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

    @Hidden
    @JsonIgnore
    @Override
    public String getAiTypeArray() {
        return super.getAiTypeArray();
    }

    public CameraVO(Camera camera) {
        if (null != camera) {
            this.setCameraId(camera.getCameraId());
            this.setCameraName(camera.getCameraName());
            this.setRtspUrl(camera.getRtspUrl());
            this.setAiTypeArray(camera.getAiTypeArray());
            // 设置ai数组
            this.setAi(transAiTypesToArray(camera.getAiTypeArray()));
        }
    }

    /**
     * 将AI算法类型从字符串转为字符串数组
     * @param types
     * @return
     */
    public static String[] transAiTypesToArray(String types) {
        if (null != types && types.length() > 0) {
            return types.contains(",") ? types.split(",") : new String[]{types};
        } else {
            return null;
        }
    }

    /**
     * 将AI算法类型从数组转为字符串
     * @param aiTypes
     * @return
     */
    public static String transAiTypesToString(String[] aiTypes) {
        if (null != aiTypes && aiTypes.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String type : aiTypes) {
                sb.append(",").append(type);
            }
            return sb.toString().substring(1);
        } else {
            return null;
        }
    }


}
