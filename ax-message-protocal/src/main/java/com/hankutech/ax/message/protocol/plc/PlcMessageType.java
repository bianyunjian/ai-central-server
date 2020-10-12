package com.hankutech.ax.message.protocol.plc;

/**
 * 字节X4标示消息类型
 */
public enum PlcMessageType {
    EMPTY(0, "UNKNOWN"),
    HAND_SHAKE_REQ(10, "握手请求"),//0x0A
    HAND_SHAKE_RESP(11, "握手响应"),//0x0B

    OPEN_GATE_REQ(20, "开门请求"), //0x14
    OPEN_GATE_RESP(21, "开门响应"), //0x15

    GATE_CLOSED_EVENT_REQ(30, "门已关闭事件"),//0x1E
    GATE_CLOSED_EVENT_RESP(31, "门已关闭事件响应"),//0x1F

    RFID_EVENT_REQ(40, "RFID刷卡事件"),//0x28
    RFID_EVENT_RESP(41, "RFID刷卡事件响应"),//0x29

    SYS_STATUS_REQ(50, "系统状态事件"),//0x32
    SYS_STATUS_RESP(51, "系统状态事件响应"),//0x33

    BOX_DETECT_REQ(60, "周转箱检测请求"),//0x3C
    BOX_DETECT_RESP(61, "周转箱检测请求响应");//0x3D

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
            case 60:
                return BOX_DETECT_REQ;
            case 61:
                return BOX_DETECT_RESP;

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
