package com.hankutech.ax.message.code;

/**
 * 字节X10标示任务类型
 */
public enum AITaskType {
    EMPTY(0, "UNKNOWN"),
    BOX(1, "安全防护检测和周转箱颜色检测任务"),
    FACE(2, "人脸识别检测任务"),
    GARBAGE(3, "垃圾分类检测任务"),
    PERSON(4, "有无人员检测任务");

    public static AITaskType valueOf(int value) {
        switch (value) {

            case 1:
                return BOX;
            case 2:
                return FACE;
            case 3:
                return GARBAGE;
            case 4:
                return PERSON;
            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    AITaskType(int v, String desc) {
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
