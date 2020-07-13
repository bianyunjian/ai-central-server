package com.hankutech.ax.centralserver.exception;

import com.hankutech.ax.centralserver.support.ErrorCoder;

/**
 * 无效数据异常
 *
 * @author ZhangXi
 */
public class InvalidDataException extends Exception {

    private ErrorCoder errorCode;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException with(ErrorCoder errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorCoder getErrorCode() {
        return errorCode;
    }
}
