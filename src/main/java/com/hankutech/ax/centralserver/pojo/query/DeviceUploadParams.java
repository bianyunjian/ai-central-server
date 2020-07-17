package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.vo.CameraEventVO;
import lombok.Data;

import java.util.List;

@Data
public class DeviceUploadParams {
    private String time;
    private int deviceId;
    private List<CameraEventVO> cameraList;
}
