package com.hankutech.ax.centralserver.model;

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

    @TableId(type = IdType.AUTO)
    private Integer deviceId;

    private String deviceName;

    /**
     * 设备状态：0-正常，1-断线
     */
    private Integer status;

}
