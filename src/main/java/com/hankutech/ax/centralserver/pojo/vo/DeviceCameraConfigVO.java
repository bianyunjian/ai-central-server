package com.hankutech.ax.centralserver.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeviceCameraConfigVO {
    @Schema(description = "相机ID", example = "1")
    private Integer id;

    @Schema(description = "摄像头名称", example = "测试相机")
    private String name;

    @Schema(description = "RTSP地址", example = "rtsp://192.168.1.234")
    private String rtsp;

    @Schema(description = "设置的AI检测算法", example = "[\"box\",\"face\"]")
    private String[] ai;

    @Schema(description = "相机在爱信系统的编码", example = "1")
    private Integer  axCameraNumber;

    /**
     * 将AI算法类型从字符串转为字符串数组
     *
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
     *
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
