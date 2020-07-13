package com.hankutech.ax.centralserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 设备人员关联表实体类
 *
 * @author ZhangXi
 */
@Data
public class DevicePerson {

    @TableId(type = IdType.UUID)
    private String id;

    private Integer deviceId;

    private Integer personId;

}
