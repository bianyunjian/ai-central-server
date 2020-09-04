package com.hankutech.ax.message.code;

/**
 * 垃圾分类检测任务应答
 */
public enum AIGarbageResultType implements AIResult {
    //    EMPTY(0,"UNKNOWN"),
    DRY(1, "干垃圾"),//（灰色垃圾袋）
    WET(2, "湿垃圾"),//（黑色垃圾袋）
    RECYCLABLE(3, "可回收垃圾"),//（绿色垃圾袋）
    HAZARDOUS(4, "有害垃圾"),//（红色垃圾袋）

    WHITE_QUILT(11, "被服"), // 被服暂时定义为白色垃圾
    BLUE(12, ""), // fixme 蓝色垃圾之前定义垃圾类型？

    OTHERS(99, "其他异常");//(未知垃圾类型)

    public static AIGarbageResultType valueOf(int value) {
        switch (value) {
            case 1:
                return DRY;
            case 2:
                return WET;
            case 3:
                return RECYCLABLE;
            case 4:
                return HAZARDOUS;
            case 99:
                return OTHERS;
            default:
                return OTHERS;
        }
    }

    int value;
    String description;

    AIGarbageResultType(int v, String desc) {
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
