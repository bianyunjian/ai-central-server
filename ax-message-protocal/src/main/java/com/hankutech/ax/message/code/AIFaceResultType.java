package com.hankutech.ax.message.code;

/**
 * 人脸识别输任务应答
 */
public enum AIFaceResultType implements AIResult {
    EMPTY(0, "UNKNOWN"),
    FACE_PASS(1, "人员通过识别"),
    FACE_EXCEPTION(99, "识别异常");

    public static AIFaceResultType valueOf(int value) {
        switch (value) {

            case 1:
                return FACE_PASS;
            case 99:
                return FACE_EXCEPTION;

            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    AIFaceResultType(int v, String desc) {
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
