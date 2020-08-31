package com.hankutech.ax.message.protocol.plc;
public class PlcMessageValue {
    /**
     * 异常
     */
    public static int EXCEPTION = 99;

    public static int HAND_SHAKE_REQ = 1;
    public static int HAND_SHAKE_RESP_SUCCESS = 1;


    /**
     * 开门请求 1=请求开门
     */
    public static int OPEN_GATE_REQ = 1;


    /**
     * 开门请求响应,1=门已成功打开
     */
    public static int OPEN_GATE_RESP_SUCCESS = 1;

    /**
     * 门已关闭事件 1=门已关闭
     */
    public static int GATE_CLOSED_EVENT_REQ = 1;
    public static int GATE_CLOSED_EVENT_RESP_SUCCESS = 1;


    /**
     * RFID刷卡事件 1=RFID刷卡通过
     */
    public static int RFID_EVENT_REQ = 1;
    public static int RFID_EVENT_RESP_SUCCESS = 1;


    /**
     * 系统状态事件 1=正常， 2= 繁忙中， 3=维护 ，4=故障
     */
    public static int SYS_STATUS_REQ_NORMAL = 1;
    public static int SYS_STATUS_REQ_BUSY = 1;
    public static int SYS_STATUS_REQ_MAINTAIN = 1;
    public static int SYS_STATUS_REQ_ERROR = 1;

    public static int SYS_STATUS_RESP_SUCCESS = 1;


}
