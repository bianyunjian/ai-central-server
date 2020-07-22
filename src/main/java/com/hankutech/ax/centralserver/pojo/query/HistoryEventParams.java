package com.hankutech.ax.centralserver.pojo.query;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryEventParams {
    String deviceName;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
