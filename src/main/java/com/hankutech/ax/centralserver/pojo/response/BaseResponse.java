package com.hankutech.ax.centralserver.pojo.response;

import com.hankutech.ax.centralserver.constant.ResponseStatus;
import com.hankutech.ax.centralserver.constant.SysECoder;
import com.hankutech.ax.centralserver.constant.ErrorCoder;
import lombok.Data;

/**
 * HTTP基础响应类
 *
 * @author ZhangXi
 */
@Data
public class BaseResponse<T> {

    private static final String DEFAULT_MESSAGE = "";

    private ResponseStatus status;

    private Integer errorCode;

    private T data;

    private String message;


    public void success() {
        this.status = ResponseStatus.SUCCESS;
        this.errorCode = SysECoder.DEFAULT_NO_ERROR.get();
    }

    public void success(String message) {
        success();
        this.message = message;
    }

    public void success(String message, T data) {
        success(message);
        this.data = data;
    }

    public void fail() {
        this.status = ResponseStatus.FAILURE;
    }

    public void fail(String message) {
        fail();
        this.message = message;
    }

    public void fail(String message, ErrorCoder errorCode) {
        fail(message);
        if(null != errorCode) {
            this.errorCode = errorCode.get();
        }
    }


}
