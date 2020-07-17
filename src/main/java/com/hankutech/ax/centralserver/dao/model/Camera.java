package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 相机表实体类
 *
 * @author ZhangXi
 */
@Data
public class Camera {

    public static final String COL_CAMERA_NAME = "camera_name";

    @TableId(type = IdType.AUTO)
    private Integer cameraId;

    private String cameraName;

    private String rtspUrl;

    private Integer axCameraNumber;
}
