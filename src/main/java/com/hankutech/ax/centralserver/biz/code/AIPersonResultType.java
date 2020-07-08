package com.hankutech.ax.centralserver.biz.code;

/**
 * 有无人员检测任务应答
 */
public enum AIPersonResultType implements AIResult{
    EMPTY(0,"UNKNOWN"),
    PERSON_EXIST(1, "有人"),
    NO_PERSON(2, "没人"),
    EXCEPTION(99, "异常");
    public static AIPersonResultType valueOf(int value) {
        switch (value) {

            case 1:
                return PERSON_EXIST;
            case 2:
                return NO_PERSON;

            case 99:
                return EXCEPTION;
            default:
                return EMPTY;
        }
    }
    int value;
    String description;

    AIPersonResultType(int v, String desc) {
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
