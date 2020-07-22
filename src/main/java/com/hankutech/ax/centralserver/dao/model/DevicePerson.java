package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "device_person")
public class DevicePerson {
    /**
     * 设备与人员联合ID，使用36位UUID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 设备表ID
     */
    @TableField(value = "device_id")
    private Integer deviceId;

    /**
     * 人员表ID
     */
    @TableField(value = "person_id")
    private Integer personId;

    public static final String COL_ID = "id";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_PERSON_ID = "person_id";
}