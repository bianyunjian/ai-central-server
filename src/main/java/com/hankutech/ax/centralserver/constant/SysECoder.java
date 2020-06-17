package com.hankutech.ax.centralserver.constant;

import com.hankutech.ax.centralserver.support.ErrorCoder;

/**
 * 系统错误码枚举类
 *
 * @author ZhangXi
 */
public enum SysECoder implements ErrorCoder {

    /**
     * 系统错误码
     */
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
        return null;
    }

    public String getEnumDesc() {
        return enumDesc;
    }
}
