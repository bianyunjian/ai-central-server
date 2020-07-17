package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 事件表实体类
 *
 */
@Data
public class Event {

    @TableId(type = IdType.AUTO)
    private int eventId;

    private int deviceId;

    private int cameraId;

    private String eventType;

    private Integer eventTypeValue;

    private LocalDateTime eventTime;

    private String description;
    private String eventImagePath;
}
