package com.hankutech.ax.centralserver.support.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;

/**
 * @author ZhangXi
 */
@Schema(description = "数据分页查询请求")
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRequest<K extends QueryParams> extends BaseRequest {

    /**
     * 分页参数
     */
    @Valid
    @Schema(description = "分页参数")
    private PagedParams pagedParams;

    /**
     * 排序参数
     */
    @Schema(description = "排序参数")
    private OrderParams orderParams;

    /**
     * 查询参数
     */
    @Schema(description = "查询参数")
    private K queryParams;

}
