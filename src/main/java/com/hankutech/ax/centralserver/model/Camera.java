package com.hankutech.ax.centralserver.model;

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

    @TableId(type = IdType.AUTO)
    private Integer cameraId;

    private String cameraName;

    private String rtspUrl;

    private String aiTypeArray;

}