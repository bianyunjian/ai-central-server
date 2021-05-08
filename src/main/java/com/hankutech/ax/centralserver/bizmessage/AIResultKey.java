package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.message.code.AITaskType;
import lombok.Data;

@Data
public class AIResultKey {
    private AITaskType aiTaskType;
    private int cameraNumber;
}
