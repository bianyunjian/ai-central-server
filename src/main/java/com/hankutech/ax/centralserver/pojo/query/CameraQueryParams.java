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
public class CameraQueryParams extends QueryParams {

    @Schema(description = "用户输入的相机名称")
    private String name;

}
