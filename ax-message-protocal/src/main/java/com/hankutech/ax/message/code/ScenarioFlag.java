package com.hankutech.ax.message.code;

/**
 * 字节X9标示场景应用
 */
public enum ScenarioFlag {
    EMPTY(0,"UNKNOWN"),
    ZX(1, "中型物流护士站点"),
    QL(2, "气力式垃圾站点投递口");
    public static ScenarioFlag valueOf(int value) {
        switch (value) {

            case 1:
                return ZX;
            case 2:
                return QL;

            default:
                return EMPTY;
        }
    }
    int value;
    String description;

    ScenarioFlag(int v, String desc) {
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
