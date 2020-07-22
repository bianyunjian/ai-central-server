package com.hankutech.ax.centralserver.pojo.vo.event.realtime;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RealtimeEventVO {
    String eventType;
    Integer eventTypeValue;
    LocalDateTime eventTime;
    String description;
}
