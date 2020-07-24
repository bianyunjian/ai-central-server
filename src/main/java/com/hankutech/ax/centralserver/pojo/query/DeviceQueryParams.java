package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.request.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceQueryParams extends QueryParams {

    @Schema(description = "用户输入的设备名称")
    private String name;

}
