package com.hankutech.ax.centralserver.config;

import com.hankutech.ax.centralserver.constant.SysECoder;
import com.hankutech.ax.centralserver.support.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 *
 * @author ZhangXi
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleException(Exception e) {
        log.error("处理未知异常", e);
        BaseResponse response = new BaseResponse();
        String message = "";
        response.fail(message, SysECoder.INNER_ERROR);
        return response;
    }

}
