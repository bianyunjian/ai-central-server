package com.hankutech.ax.centralserver.constant;

import com.hankutech.ax.centralserver.support.StringGetter;
import com.hankutech.ax.centralserver.support.response.BaseResponse;

/**
 * 响应状态枚举类
 * 用来匹配 {@link BaseResponse}的status属性值
 *
 * @author ZhangXi
 */
public enum ResponseStatus implements StringGetter {

    /**
     * 响应状态
     */
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    UNAUTHORIZED("UNAUTHORIZED"),
    DENIED("DENIED");

    private String enumValue;

    ResponseStatus(String enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public String get() {
        return this.enumValue;
    }

}
