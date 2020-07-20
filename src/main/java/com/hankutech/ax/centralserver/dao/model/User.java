package com.hankutech.ax.centralserver.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "`user`")
public class User {
    @TableId(value = "user_name", type = IdType.INPUT)
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "display_name")
    private String displayName;

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_DISPLAY_NAME = "display_name";
}