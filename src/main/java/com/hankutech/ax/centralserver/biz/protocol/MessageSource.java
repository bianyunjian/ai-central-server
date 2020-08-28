package com.hankutech.ax.centralserver.biz.protocol;

/**
 * 字节X1标示消息来源
 */
public enum MessageSource {
    EMPTY(0, "UNKNOWN"),
    CENTRAL_SERVER(1, "中心算法控制器"),
    PLC(2, "艾信PLC"),
    APP(3, "APP");

    public static MessageSource valueOf(int value) {
        switch (value) {

            case 1:
                return CENTRAL_SERVER;
            case 2:
                return PLC;
            case 3:
                return APP;
            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    MessageSource(int v, String desc) {
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
