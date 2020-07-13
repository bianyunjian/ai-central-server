package com.hankutech.ax.centralserver.constant;

import com.hankutech.ax.centralserver.support.ErrorCoder;

/**
 * @author ZhangXi
 */
public enum DataECoder implements ErrorCoder {

    /** 相机数据错误码 */
    CAMERA_NOT_EXIST(100101, "相机数据不存在"),
    CAMERA_EXIST(100102, "相机数据已存在"),
    CAMERA_REPEAT_NAME(100103, "相机名称重复"),
    CAMERA_HAS_DEVICE(100104, "相机已关联设备");

    private Integer errorCode;
    private String desc;

    DataECoder(Integer errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    @Override
    public Integer get() {
        return null;
    }
}
