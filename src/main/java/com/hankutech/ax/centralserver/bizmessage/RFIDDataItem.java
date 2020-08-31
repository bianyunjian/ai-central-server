package com.hankutech.ax.centralserver.bizmessage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RFIDDataItem {
    private int deviceId;
    private LocalDateTime eventTime;

}
