package com.hankutech.ax.centralserver.tool;

import java.util.List;

/**
 * AI检测算法类型工具类
 *
 * @author ZhangXi
 */
public final class AiTypeTool {

    private final static String SEPARATOR = ",";


    /**
     * 将AI算法类型从字符串转为字符串数组
     */
    public static String[] transAiTypesToArray(String types) {
        if (null != types && types.length() > 0) {
            return types.contains(SEPARATOR) ? types.split(SEPARATOR) : new String[]{types};
        } else {
            return null;
        }
    }

    /**
     * 将AI算法类型从数组转为字符串
     */
    public static String transAiTypesToString(String[] aiTypes) {
        if (null != aiTypes && aiTypes.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String type : aiTypes) {
                sb.append(SEPARATOR).append(type);
            }
            return sb.toString().substring(1);
        } else {
            return null;
        }
    }

}
