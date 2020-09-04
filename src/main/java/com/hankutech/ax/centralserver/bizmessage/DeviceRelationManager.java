package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.service.DeviceCache;
import com.hankutech.ax.message.code.AIGarbageResultType;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceRelationManager {
    public static List<Integer> getAppNumber(int plcNumber) {
        return DeviceCache.getAppNumberByPlcId(plcNumber);
    }

    public static List<Integer> getPlcNumber(int appNumber) {
        return DeviceCache.getPlcNumberByAppId(appNumber);
    }

    public static List<Integer> getAppNumberByDeviceId(int deviceId) {
        return DeviceCache.getAppNumberByDeviceId(deviceId);
    }

    public static List<Integer> getDeviceIdByDeviceGroupId(int deviceGroupId) {
        return DeviceCache.getDeviceNumberByDeviceGroupId(deviceGroupId);
    }

    public static List<Integer> getDeviceIdByPlcNumber(int plcNumber) {
        return DeviceCache.getDeviceNumberByPlcId(plcNumber);
    }

    public static List<Integer> getDeviceIdByAppNumber(int appNumber) {
        return DeviceCache.getDeviceNumberByAppId(appNumber);
    }

    public static Integer getDeviceGroupIdByDeviceId(int deviceId) {
        return DeviceCache.getDeviceGroupIdByDeviceId(deviceId);
    }

    public static List<Integer> getAppNumberByDeviceGroupId(int deviceGroupId) {
        return DeviceCache.getAppNumberByDeviceGroupId(deviceGroupId);
    }


    /**
     * 设备数据Map缓存
     */
    private static ConcurrentHashMap<Integer, AIGarbageResultType> DEVICE_GARBAGE_TYPE_MAP = new ConcurrentHashMap<>();

    public static AIGarbageResultType getDeviceGarbageType(int deviceId) {

        if (DEVICE_GARBAGE_TYPE_MAP.containsKey(deviceId)) {
            return DEVICE_GARBAGE_TYPE_MAP.get(deviceId);
        }


        return AIGarbageResultType.WET;
    }

    public static void updateDeviceGarbageType(int deviceId, AIGarbageResultType garbageResultType) {
        DEVICE_GARBAGE_TYPE_MAP.put(deviceId, garbageResultType);
    }

    public static ConcurrentHashMap<Integer, AIGarbageResultType> getDeviceGarbageTypeList() {

        return DEVICE_GARBAGE_TYPE_MAP;
    }
}
