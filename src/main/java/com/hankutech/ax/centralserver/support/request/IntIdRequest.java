package com.hankutech.ax.centralserver.support.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * ID请求数据
 *
 * @author ZhangXi
 */
@Schema(description = "ID请求数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class IntIdRequest extends BaseRequest {

    @NotNull
    @Schema(description = "数据ID", example = "1")
    private Integer id;

}
