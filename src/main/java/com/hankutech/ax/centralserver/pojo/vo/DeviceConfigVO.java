package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Device;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author ZhangXi
 */
@Schema(description = "带配置信息的设备数据")
public class DeviceConfigVO extends DeviceVO {

    @Schema(description = "相机信息列表")
    private List<CameraVO> cameraList;


    public DeviceConfigVO(Device device, List<CameraVO> cameras) {
        super(device);
        this.cameraList = cameras;
    }

    public List<CameraVO> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<CameraVO> cameraList) {
        this.cameraList = cameraList;
    }
}
