package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 人员表实体类
 *
 * @author ZhangXi
 */
@Data
public class Person {

    @TableId(type = IdType.AUTO)
    private Integer personId;

    private String personName;

    private String phoneNum;

    private String faceFtrArray;

}
