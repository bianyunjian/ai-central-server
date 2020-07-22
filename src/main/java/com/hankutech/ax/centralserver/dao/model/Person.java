package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "person")
public class Person {
    /**
     * 人员ID
     */
    @TableId(value = "person_id", type = IdType.AUTO)
    private Integer personId;

    /**
     * 人员名称
     */
    @TableField(value = "person_name")
    private String personName;

    /**
     * 手机号码，具有唯一性
     */
    @TableField(value = "phone_num")
    private String phoneNum;

    /**
     * 人脸特征向量数组,使用逗号分隔
     */
    @TableField(value = "face_ftr_array")
    private String faceFtrArray;

    /**
     * 照片base64
     */
    @TableField(value = "image")
    private String image;

    public static final String COL_PERSON_ID = "person_id";

    public static final String COL_PERSON_NAME = "person_name";

    public static final String COL_PHONE_NUM = "phone_num";

    public static final String COL_FACE_FTR_ARRAY = "face_ftr_array";

    public static final String COL_IMAGE = "image";
}