package com.hankutech.ax.centralserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 事件表实体类
 *
 * @author ZhangXi
 */
@Data
public class Event {

    @TableId(type = IdType.UUID)
    private String eventId;

    private Integer deviceId;

    private Integer cameraId;

    private String eventType;

    private Integer eventTypeValue;

    private LocalDateTime eventTime;

    private String description;

}
