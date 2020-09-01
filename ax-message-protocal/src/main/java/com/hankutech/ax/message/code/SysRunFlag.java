package com.hankutech.ax.message.code;

/**
 * 系统状态
 */
public enum SysRunFlag {
    EMPTY(0, "UNKNOWN"),
    NORMAL(1, "正常"),
    BUSY(2, "繁忙中"),
    MAINTAIN(3, "维护"),
    ERROR(4, "故障");


    public static SysRunFlag valueOf(int value) {
        switch (value) {

            case 1:
                return NORMAL;
            case 2:
                return BUSY;
            case 3:
                return MAINTAIN;
            case 4:
                return ERROR;

            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    SysRunFlag(int v, String desc) {
        value = v;
        description = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
