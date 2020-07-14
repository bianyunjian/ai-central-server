package com.hankutech.ax.centralserver.dao;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * Model实体类超类
 * 用于匹配通用数据库字段
 *
 * @author ZhangXi
 */
@Data
public class BaseModel {

    /**
     * 逻辑删除标记
     * 用于不会真正删除数据的数据表
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rowVersion;

}
