package com.hankutech.ax.centralserver.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 复杂查询中的排序参数项
 *
 * @author ZhangXi
 */
@Data
public class OrderItem {

    @Schema(description = "排序列名", example = "")
    private String name;

    @Schema(description = "排序方向", example = "")
    private String order;

}
