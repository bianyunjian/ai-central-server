package com.hankutech.ax.centralserver.model;

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

    @TableId(type = IdType.UUID)
    private String id;

    private Integer deviceId;

    private Integer cameraId;

}
