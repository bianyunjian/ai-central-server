package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import com.hankutech.ax.centralserver.menum.DeviceStatus;
import lombok.Data;

/**
 * Iot设备表实体类
 *
 * @author ZhangXi
 */
@Data
public class Device {

    public static final String COL_DEVICE_ID = "device_id";
    public static final String COL_DEVICE_NAME = "device_name";

    @TableId(type = IdType.AUTO)
    private Integer deviceId;

    private String deviceName;

    /**
     * 设备状态：0-正常，1-断线
     * 建议使用枚举类 {@link DeviceStatus} 修改状态值
     */
    private Integer status;


    /**
     * 设备使用的场景, 关联 {@link ScenarioFlag}
     */
    private  String deviceScenario;
}
