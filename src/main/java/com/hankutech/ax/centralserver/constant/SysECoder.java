package com.hankutech.ax.centralserver.constant;

/**
 * 系统错误码枚举类
 *
 * @author ZhangXi
 */
public enum SysECoder implements ErrorCoder {

    /**
     * 系统错误码
     */
    DEFAULT_NO_ERROR(0, "默认无错误"),
    INNER_ERROR(100001, "系统内部错误"),
    PARAM_INVALID(100002, "参数校验无效");

    private Integer enumValue;
    private String enumDesc;

    SysECoder(Integer enumValue, String enumDesc) {
        this.enumValue = enumValue;
        this.enumDesc = enumDesc;
    }

    @Override
    public Integer get() {
        return this.enumValue;
    }

    public String getEnumDesc() {
        return enumDesc;
    }
}
