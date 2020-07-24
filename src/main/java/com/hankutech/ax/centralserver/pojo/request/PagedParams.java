package com.hankutech.ax.centralserver.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分页参数
 *
 * @author ZhangXi
 */
@Schema(description = "分页参数")
@Data
public class PagedParams {

    @NotNull
    @Schema(description = "当前页码", example = "1", required = true)
    private Integer pageNum;

    @NotNull
    @Schema(description = "每页数据量", example = "10", required = true)
    private Integer pageSize;

    @Hidden
    @JsonIgnore
    private Integer start;

}
