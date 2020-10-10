package com.hankutech.ax.centralserver.constant;

public class Common {

    /**
     * 服务名称
     */
    public static String SERVICE_NAME = "ax_central_server";

    /**
     * 是否同步人脸数据到专用的face数据表中,该配置项用于集成人脸识别的独立服务, 默认为true
     */
    public static boolean FACE_SYNC_DATA = true;

    /**
     * 人脸服务的通知地址
     */
    public static String FACE_NOTIFY_SERVICE_URL = "http://localhost:9002/runtime/v1/image-classify/face/reload";

    /**
     * 事件图片的保存格式
     */
    public static String IMAGE_FORMAT = "png";
    /**
     * 事件图片的保存目录
     */
    public static String IMAGE_FOLDER_PATH = "./eventImage/";


    /**
     * 事件的有效时间窗口(秒), 超过该时间后,事件被认为过期无效了.
     */
    public static int EVENT_OBSOLETE_SECONDS = 5;
}
