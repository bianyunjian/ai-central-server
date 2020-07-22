package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "device_camera")
public class DeviceCamera {
    /**
     * 设备与相机联合ID，使用36位UUID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 设备表ID
     */
    @TableField(value = "device_id")
    private Integer deviceId;

    /**
     * 相机表ID
     */
    @TableField(value = "camera_id")
    private Integer cameraId;

    /**
     * AI检测算法类型数组，使用逗号分隔
     */
    @TableField(value = "ai_type_array")
    private String aiTypeArray;

    public static final String COL_ID = "id";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_CAMERA_ID = "camera_id";

    public static final String COL_AI_TYPE_ARRAY = "ai_type_array";
}