package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "event")
public class Event {
    /**
     * 事件ID，使用36位UUID
     */
    @TableId(value = "event_id", type = IdType.AUTO)
    private Integer eventId;

    /**
     * 设备ID
     */
    @TableField(value = "device_id")
    private Integer deviceId;

    /**
     * 相机ID
     */
    @TableField(value = "camera_id")
    private Integer cameraId;

    /**
     * 事件类型
     */
    @TableField(value = "event_type")
    private String eventType;

    /**
     * 预定义事件值，与事件类型有关
     */
    @TableField(value = "event_type_value")
    private Integer eventTypeValue;

    /**
     * 事件发生的时间
     */
    @TableField(value = "event_time")
    private Date eventTime;

    /**
     * 事件描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 事件关联的图片路径
     */
    @TableField(value = "event_image_path")
    private String eventImagePath;

    public static final String COL_EVENT_ID = "event_id";

    public static final String COL_DEVICE_ID = "device_id";

    public static final String COL_CAMERA_ID = "camera_id";

    public static final String COL_EVENT_TYPE = "event_type";

    public static final String COL_EVENT_TYPE_VALUE = "event_type_value";

    public static final String COL_EVENT_TIME = "event_time";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_EVENT_IMAGE_PATH = "event_image_path";
}