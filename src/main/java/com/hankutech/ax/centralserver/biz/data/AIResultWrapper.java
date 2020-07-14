package com.hankutech.ax.centralserver.biz.data;

import com.hankutech.ax.centralserver.biz.code.AIBoxResultType;
import com.hankutech.ax.centralserver.biz.code.AIResult;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AIResultWrapper {
    private AIResult aiResult;
    private LocalDateTime dateTime;

    public AIResultWrapper() {
        aiResult = AIBoxResultType.EMPTY;
    }


    public AIResultWrapper(AIResult result, LocalDateTime time) {
        this.aiResult = result;
        this.dateTime = time;
    }
}
