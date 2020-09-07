package com.hankutech.ax.message.code;

/**
 *
 */
public enum AIEmpty implements AIResult {

    EMPTY(0, "UNKNOWN"),
    ;

    public static AIEmpty valueOf(int value) {
        switch (value) {


            default:
                return EMPTY;
        }
    }

    int value;
    String description;

    AIEmpty(int v, String desc) {
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
