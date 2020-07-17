package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 设备相机关联表实体类
 *
 * @author ZhangXi
 */
@Data
public class DeviceCamera {

    public static final String COL_CAMERA_ID = "camera_id";
    public static final String COL_DEVICE_ID = "device_id";

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Integer deviceId;

    private Integer cameraId;

    private String aiTypeArray;
}
