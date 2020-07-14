package com.hankutech.ax.centralserver.pojo.request;

import com.hankutech.ax.centralserver.exception.InvalidParamException;

/**
 * 实体数据请求抽象类
 *
 * @author ZhangXi
 */
public abstract class AbstractObjectRequest<T> extends BaseRequest {

    /**
     * 获取有效数据
     * 经过参数校验及格式化处理后，返回组装好的请求数据
     *
     * @return T
     * @throws InvalidParamException 参数无效异常
     */
    public T obtainValidData() throws InvalidParamException {
        validate();
        format();
        return buildData();
    }

    /**
     * 自定义参数校验
     *
     * @throws InvalidParamException 参数无效异常
     */
    protected abstract void validate() throws InvalidParamException;

    /**
     * 自定义数据格式化
     *
     * @throws InvalidParamException 参数无效异常
     */
    protected abstract void format() throws InvalidParamException;

    /**
     * 构建对象
     * @return T
     */
    protected abstract T buildData();

}
