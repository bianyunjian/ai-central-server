package com.hankutech.ax.centralserver.menum;

/**
 * 设备状态枚举
 *
 * @author ZhangXi
 */
public enum DeviceStatus {

    /**
     *
     */
    NORMAL(0, "设备状态正常"),
    DISCONNECTED(1, "设备断线");


    private Integer statusValue;
    private String desc;

    DeviceStatus(Integer statusValue, String desc) {
        this.statusValue = statusValue;
        this.desc = desc;
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public String getDesc() {
        return desc;
    }
}
