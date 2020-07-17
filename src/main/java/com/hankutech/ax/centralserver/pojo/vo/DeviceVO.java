package com.hankutech.ax.centralserver.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hankutech.ax.centralserver.dao.model.Device;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * 设备VO
 *
 * @author ZhangXi
 */
@Schema(name = "Device", description = "设备数据")
@ToString
public class DeviceVO extends Device {

    @Schema(description = "设备ID", example = "1")
    @JsonProperty("id")
    @Override
    public Integer getDeviceId() {
        return super.getDeviceId();
    }

    @Schema(description = "设备名称", example = "1号垃圾口检测器")
    @JsonProperty("name")
    @Override
    public String getDeviceName() {
        return super.getDeviceName();
    }


    public DeviceVO(Device device) {
        if (null != device) {
            this.setDeviceId(device.getDeviceId());
            this.setDeviceName(device.getDeviceName());
            this.setStatus(device.getStatus());
        }
    }
}
