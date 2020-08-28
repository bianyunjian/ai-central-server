package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "camera")
public class Camera {
    /**
     * 相机ID
     */
    @TableId(value = "camera_id", type = IdType.AUTO)
    private Integer cameraId;

    /**
     * 相机名称，具有唯一性
     */
    @TableField(value = "camera_name")
    private String cameraName;

    /**
     * RTSP连接地址
     */
    @TableField(value = "rtsp_url")
    private String rtspUrl;


    /**
     * 备注描述
     */
    @TableField(value = "description")
    private String description;

    public static final String COL_CAMERA_ID = "camera_id";

    public static final String COL_CAMERA_NAME = "camera_name";

    public static final String COL_RTSP_URL = "rtsp_url";


    public static final String COL_DESCRIPTION = "description";
}