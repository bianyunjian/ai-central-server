package com.hankutech.ax.message.protocol.plc;
/**
 * 字节X4标示消息类型
 */
public enum PlcMessageType {
    EMPTY(0, "UNKNOWN"),
    HAND_SHAKE_REQ(10, "握手请求"),
    HAND_SHAKE_RESP(11, "握手响应"),

    OPEN_GATE_REQ(20, "开门请求"),
    OPEN_GATE_RESP(21, "开门响应"),

    GATE_CLOSED_EVENT_REQ(30, "门已关闭事件"),
    GATE_CLOSED_EVENT_RESP(31, "门已关闭事件响应"),

    RFID_EVENT_REQ(40, "RFID刷卡事件"),
    RFID_EVENT_RESP(41, "RFID刷卡事件响应"),

    SYS_STATUS_REQ(50, "系统状态事件"),
    SYS_STATUS_RESP(51, "系统状态事件响应");

    public static PlcMessageType valueOf(int value) {
        switch (value) {

            case 10:
                return HAND_SHAKE_REQ;
            case 11:
                return HAND_SHAKE_RESP;
            case 20:
                return OPEN_GATE_REQ;
            case 21:
                return OPEN_GATE_RESP;

            case 30:
                return GATE_CLOSED_EVENT_REQ;
            case 31:
                return GATE_CLOSED_EVENT_RESP;

            case 40:
                return RFID_EVENT_REQ;
            case 41:
                return RFID_EVENT_RESP;

            case 50:
                return SYS_STATUS_REQ;
            case 51:
                return SYS_STATUS_RESP;


            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    PlcMessageType(int v, String desc) {
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
