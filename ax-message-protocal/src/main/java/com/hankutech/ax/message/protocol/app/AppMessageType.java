package com.hankutech.ax.message.protocol.app;

/**
 * 字节X4标示消息类型
 */
public enum AppMessageType {
    EMPTY(0, "UNKNOWN"),
    HAND_SHAKE_REQ(10, "握手请求"),//0x0A
    HAND_SHAKE_RESP(11, "握手响应"),//0x0B

    APP_REQUIRE_OPEN_GATE_REQ(20, "验证通过-APP请求开门"), //0x14
    APP_REQUIRE_OPEN_GATE_RESP(21, "APP门已打开响应"),//0x15

    GATE_CLOSED_EVENT_REQ(30, "APP门已关闭"),//0x1E
    GATE_CLOSED_EVENT_RESP(31, "APP门已关闭响应"),//0x1F

    SYS_STATUS_REQ(50, "系统状态事件"),//0x32
    SYS_STATUS_RESP(51, "系统状态事件响应"),//0x33


    AUTH_ALL_PASSED_REQ(60, "验证全部通过 (人脸识别通过+垃圾检测通过）"),//0x10

    AUTH_REQ(70, "APP身份验证请求"),//0x46
    AUTH_RESP(71, "APP身份验证响应"),//0x47

    GARBAGE_DETECT_REQ(80, "APP垃圾检测请求"),//0x50
    GARBAGE_DETECT_RESP(81, "APP垃圾检测请求响应"),//0x51

    APP_START_PROCESS_REQ(90, "APP开始投递请求"),//0x5A
    APP_START_PROCESS_RESP(91, "APP开始投递响应"),//0x5B

    APP_FINISH_PROCESS_REQ(100, "APP完成投递请求"),//0x64
    APP_FINISH_PROCESS_RESP(101, "APP完成投递响应");//0x65

    public static AppMessageType valueOf(int value) {
        switch (value) {

            case 10:
                return HAND_SHAKE_REQ;
            case 11:
                return HAND_SHAKE_RESP;
            case 20:
                return APP_REQUIRE_OPEN_GATE_REQ;
            case 21:
                return APP_REQUIRE_OPEN_GATE_RESP;

            case 30:
                return GATE_CLOSED_EVENT_REQ;
            case 31:
                return GATE_CLOSED_EVENT_RESP;


            case 50:
                return SYS_STATUS_REQ;
            case 51:
                return SYS_STATUS_RESP;

            case 60:
                return AUTH_ALL_PASSED_REQ;

            case 70:
                return AUTH_REQ;
            case 71:
                return AUTH_RESP;

            case 80:
                return GARBAGE_DETECT_REQ;
            case 81:
                return GARBAGE_DETECT_RESP;


            case 90:
                return APP_START_PROCESS_REQ;
            case 91:
                return APP_START_PROCESS_RESP;

            case 100:
                return APP_FINISH_PROCESS_REQ;
            case 101:
                return APP_FINISH_PROCESS_RESP;

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
