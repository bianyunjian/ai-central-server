package com.hankutech.ax.centralserver.bizmessage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QRCodeDataItem {
    private int deviceId;
    private LocalDateTime eventTime;

}
