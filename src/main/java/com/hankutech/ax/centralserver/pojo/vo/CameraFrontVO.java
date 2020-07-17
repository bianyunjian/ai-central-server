package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Camera;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 适配前端界面API的相机VO
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CameraFrontVO extends CameraVO {

    @Schema(description = "艾信相机编码", example = "1")
    private Integer axCameraNumber;

    public CameraFrontVO(Camera camera) {
        super(camera);
        if (null != camera) {
            this.axCameraNumber = camera.getAxCameraNumber();
        }
    }
}
