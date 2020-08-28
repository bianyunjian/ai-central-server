package com.hankutech.ax.message.code;

/**
 * 安全防护检测任务应答
 */
public enum AIBoxResultType implements AIResult {

    EMPTY(0, "UNKNOWN"),
    NO_COVER(1, "周转箱未盖盖子"),
    NO_SPECIFIED_BOX(2, "非专用周转箱"),
    WRONG_TRIGGER(3, "有人误触发"),
    BOX_OK_GREEN(81, "绿色周转箱无异常"),
    BOX_OK_BLUE(82, "蓝色周转箱无异常"),
    BOX_OK_GRAY(83, "灰色周转箱无异常"),
    BOX_OK_RED(84, "红色周转箱无异常");

    public static AIBoxResultType valueOf(int value) {
        switch (value) {

            case 1:
                return NO_COVER;
            case 2:
                return NO_SPECIFIED_BOX;
            case 3:
                return WRONG_TRIGGER;
            case 81:
                return BOX_OK_GREEN;
            case 82:
                return BOX_OK_BLUE;
            case 83:
                return BOX_OK_GRAY;
            case 84:
                return BOX_OK_RED;
            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    AIBoxResultType(int v, String desc) {
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
