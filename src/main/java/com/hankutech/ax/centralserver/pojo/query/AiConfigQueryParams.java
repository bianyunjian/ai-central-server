package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.request.QueryParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 算法配置查询参数
 */
@Schema(description = "算法配置查询参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class AiConfigQueryParams extends QueryParams {

    @NotBlank
    @Schema(description = "设备或者相机名称")
    private String name;

}
