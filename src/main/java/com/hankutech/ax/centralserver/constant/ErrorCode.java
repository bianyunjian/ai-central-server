package com.hankutech.ax.centralserver.constant;

/**
 * @author ZhangXi
 */
public enum ErrorCode implements ErrorCoder {

    /**
     * 系统错误码
     */
    DEFAULT_NO_ERROR(0, "默认无错误"),
    INNER_ERROR(100001, "系统内部错误"),
    PARAM_INVALID(100002, "参数校验无效"),
    /** 相机数据错误码 */
    CAMERA_NOT_EXIST(100101, "相机数据不存在"),
    CAMERA_EXIST(100102, "相机数据已存在"),
    CAMERA_REPEAT_NAME(100103, "相机名称重复"),
    CAMERA_HAS_DEVICE(100104, "相机已关联设备"),
    /** 设备数据错误码 */
    DEVICE_NOT_EXIST(100201, "设备数据不存在"),
    DEVICE_REPEAT_NAME(100202, "设备名称重复");


    private Integer errorCode;
    private String desc;

    ErrorCode(Integer errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    @Override
    public Integer get() {
        return this.errorCode;
    }
}
