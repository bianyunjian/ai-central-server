package com.hankutech.ax.centralserver.bizmessage;

import com.hankutech.ax.centralserver.service.DeviceCache;

import java.util.ArrayList;
import java.util.List;

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
}
