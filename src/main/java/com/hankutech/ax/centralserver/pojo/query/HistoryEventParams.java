package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.request.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class HistoryEventParams extends QueryParams {

    String deviceName;
//    @NotNull
    @Schema(description = "开始时间", example = "2000-01-01 00:00:00", required = true)
    String startTime;
//    @NotNull
    @Schema(description = "结束时间", example = "2020-12-01 00:00:00", required = true)
    String endTime;
}
