package com.hankutech.ax.message.protocol.app;

public class AppMessageValue {
    /**
     * 异常
     */
    public final int EXCEPTION = 99;

    public final int HAND_SHAKE_REQ = 1;
    public final int HAND_SHAKE_RESP_SUCCESS = 1;


    /**
     * APP身份验证请求 1=要求RFID验证， 2=要求人脸验证， 3=要求二维码验证
     */
    public final int AUTH_REQ_RFID = 1;
    public final int AUTH_REQ_FACE = 2;
    public final int AUTH_REQ_QRCODE = 3;

    /**
     * APP身份验证请求  1=RFID验证成功 ， 2=人脸验证成功， 3=二维码验证成功
     */
    public final int AUTH_RESP_RFID_SUCCESS = 1;
    public final int AUTH_RESP_FACE_SUCCESS = 2;
    public final int AUTH_RESP_QRCODE_SUCCESS = 3;

    /**
     * APP垃圾检测请求 1=要求垃圾检测
     */
    public final int GARBAGE_DETECT_REQ = 1;
    /**
     * APP垃圾检测响应 1=垃圾检测成功
     */
    public final int GARBAGE_DETECT_RESP_SUCCESS = 1;
    /**
     * APP垃圾检测 1=干垃圾（灰色垃圾袋），2=湿垃圾（黑色垃圾袋），3=可回收垃圾（绿色垃圾袋），4=有害垃圾（红色垃圾袋），99=未知垃圾类型
     */
    public final int GARBAGE_DRY_GRAY = 1;
    public final int GARBAGE_WET_BLACK = 2;
    public final int GARBAGE_RECYCLE_GREEN = 3;
    public final int GARBAGE_HAZARDOUS_RED = 4;
    public final int GARBAGE_OTHERS = 99;

    /**
     * APP请求开门  1=要求开门
     */
    public final int APP_OPEN_GATE_REQ = 1;
    /**
     * APP门已打开响应  1=门已成功打开
     */
    public final int APP_OPEN_GATE_RESP_SUCCESS = 1;

    /**
     * 门已关闭事件 1=门已关闭
     */
    public final int GATE_CLOSED_EVENT_REQ = 1;
    public final int GATE_CLOSED_EVENT_RESP_SUCCESS = 1;


    /**
     * 验证全部通过 (人脸识别通过+垃圾检测通过）
     */
    public final int AUTH_ALL_PASSED_REQ = 1;
    public final int AUTH_ALL_PASSED_RESP_SUCCESS = 1;


    /**
     * 系统状态事件 1=正常， 2= 繁忙中， 3=维护 ，4=故障
     */
    public final int SYS_STATUS_REQ_NORMAL = 1;
    public final int SYS_STATUS_REQ_BUSY = 1;
    public final int SYS_STATUS_REQ_MAINTAIN = 1;
    public final int SYS_STATUS_REQ_ERROR = 1;

    public final int SYS_STATUS_RESP_SUCCESS = 1;


}
