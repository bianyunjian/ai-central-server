package com.hankutech.ax.centralserver.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仅包含ID的请求
 * @param <K> ID类型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleIdRequest<K> extends BaseRequest {

    private K id;

}
