package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Device;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 设备VO
 *
 * @author ZhangXi
 */
@Schema(name = "Device", description = "设备数据")
@ToString
@Data
public class DeviceVO {

    @Schema(description = "设备ID", example = "1")
    private Integer id;

    @Schema(description = "设备名称", example = "1号垃圾口检测器")
    private String name;

    @Schema(description = "设备状态", example = "1")
    private Integer status;

    @Schema(description = "使用场景", example = "1")
    private String deviceScenario;

    @Schema(description = "设备说明", example = "三楼拐角处")
    private String description;

    public DeviceVO(Device device) {
        if (null != device) {
            this.id = device.getDeviceId();
            this.name = device.getDeviceName();
            this.status = device.getStatus();
            this.deviceScenario = device.getDeviceScenario();
            this.description = device.getDescription();
        }
    }
}
