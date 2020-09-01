package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "device")
public class Device {
    /**
     * IOT设备ID
     */
    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    /**
     * IOT设备名称，具有唯一性
     */
    @TableField(value = "device_name")
    private String deviceName;

    /**
     * IOT设备状态：0-正常，1-断线
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 设备使用的场景, 关联 com.hankutech.ax.centralserver.biz.code.ScenarioFlag
     */
    @TableField(value = "device_scenario")
    private String deviceScenario;

    /**
     * 备注描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 艾信 plc的编号， 用于plc与中心服务器通讯时标识自己
     */
    @TableField(value = "ax_plc_id")
    private int axPlcId;

    /**
     * app编号， 用于app与中心服务器通讯时标识自己
     */
    @TableField(value = "app_id")
    private int appId;

    /**
     * 设备楼栋分组编号， 相同楼栋分组编号的设备中， 只能有一个是活动状态
     */
    @TableField(value = "device_group_id")
    private Integer deviceGroupId;

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_DEVICE_NAME = "device_name";

    public static final String COL_STATUS = "status";

    public static final String COL_DEVICE_SCENARIO = "device_scenario";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_AX_PLC_ID = "ax_plc_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_DEVICE_GROUP_ID = "device_group_id";
}