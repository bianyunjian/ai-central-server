package com.hankutech.ax.centralserver.constant;

public class Common {
    /**
     * 服务名称
     */
    public static final String SERVICE_NAME = "ax_central_server";

    /**
     * 事件图片的保存格式
     */
    public static String IMAGE_FORMAT = "jpg";
    /**
     * 事件图片的保存目录
     */
    public static String IMAGE_FOLDER_PATH = "./eventImage/";


    /**
     * 事件的有效时间窗口(秒), 超过该时间后,事件被认为过期无效了.

     */
    public static int EVENT_OBSOLETE_SECONDS = 5;
}
