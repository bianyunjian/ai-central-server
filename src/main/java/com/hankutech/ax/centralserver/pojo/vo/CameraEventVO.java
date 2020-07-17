package com.hankutech.ax.centralserver.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CameraEventVO {
    private int cameraId;
    private List<EventVO> events;
}
