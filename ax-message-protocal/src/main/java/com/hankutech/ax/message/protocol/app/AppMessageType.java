package com.hankutech.ax.message.protocol.app;

/**
 * 字节X4标示消息类型
 */
public enum AppMessageType {
    EMPTY(0, "UNKNOWN"),
    HAND_REQ(10, "握手请求"),
    HAND_RESP(11, "握手响应"),

    AUTH_REQ(20, "APP身份验证请求"),
    AUTH_RESP(21, "APP身份验证响应"),

    GARBAGE_DETECT_REQ(30, "APP垃圾检测请求"),
    GARBAGE_DETECT_RESP(31, "APP垃圾检测请求响应"),

    OPEN_GATE_REQ(40, "APP请求开门"),
    OPEN_GATE_RESP(41, "APP门已打开响应"),

    GATE_CLOSED_EVENT_REQ(50, "APP门已关闭"),
    GATE_CLOSED_EVENT_RESP(51, "APP门已关闭响应"),

    AUTH_ALL_PASSED_REQ(60, "验证全部通过 (人脸识别通过+垃圾检测通过）"),


    SYS_STATUS_REQ(70, "系统状态事件"),
    SYS_STATUS_RESP(71, "系统状态事件响应");

    public static AppMessageType valueOf(int value) {
        switch (value) {

            case 10:
                return HAND_REQ;
            case 11:
                return HAND_RESP;
            case 20:
                return AUTH_REQ;
            case 21:
                return AUTH_RESP;

            case 30:
                return GARBAGE_DETECT_REQ;
            case 31:
                return GARBAGE_DETECT_RESP;

            case 40:
                return OPEN_GATE_REQ;
            case 41:
                return OPEN_GATE_RESP;

            case 50:
                return GATE_CLOSED_EVENT_REQ;
            case 51:
                return GATE_CLOSED_EVENT_RESP;

            case 60:
                return AUTH_ALL_PASSED_REQ;

            case 70:
                return SYS_STATUS_REQ;
            case 71:
                return SYS_STATUS_RESP;

            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    AppMessageType(int v, String desc) {
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
