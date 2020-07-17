package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Iot设备表实体类
 *
 * @author ZhangXi
 */
@Data
public class Device {

    public static final String COL_DEVICE_ID = "device_id";

    @TableId(type = IdType.AUTO)
    private Integer deviceId;

    public static final String COL_DEVICE_NAME = "device_name";

    private String deviceName;

    /**
     * 设备状态：0-正常，1-断线
     */
    private Integer status;

}
