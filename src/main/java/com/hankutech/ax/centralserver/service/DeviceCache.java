package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.dao.model.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceCache {

    /**
     * 设备数据Map缓存
     */
    private static ConcurrentHashMap<String, Device> DEVICE_MAP = new ConcurrentHashMap<>();


    public static boolean isEmpty() {
        return DEVICE_MAP.isEmpty();
    }


    public static int deviceSize() {
        return DEVICE_MAP.size();
    }

    public static Map<String, Device> getAll() {
        return DEVICE_MAP;
    }


    public static void refreshCache(Device device, boolean isDelete) {
        if (null == device) {
            return;
        }
        String deviceName = device.getDeviceName();
        if (isDelete) {
            DEVICE_MAP.remove(deviceName);
            return;
        }
        if (DEVICE_MAP.containsKey(deviceName)) {
            DEVICE_MAP.replace(deviceName, device);
        } else {
            DEVICE_MAP.put(deviceName, device);
        }
    }


    public static void refreshAllCache(List<Device> devices) {
        if (null != devices && !devices.isEmpty()) {
            Map<String, Device> dataMap = new HashMap<>();
            for (Device device : devices) {
                dataMap.put(device.getDeviceName(), device);
            }
            DEVICE_MAP.clear();
            DEVICE_MAP.putAll(dataMap);
        }
    }

    //==========================================================================================

    public static List<Integer> getAppNumberByPlcId(int plcId) {
        List<Integer> appIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (plcId == device.getAxPlcId()) {
                    appIds.add(device.getAxPlcId());
                }
            }
        }
        return appIds;
    }


    public static List<Integer> getPlcNumberByAppId(int appId) {
        List<Integer> plcIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (appId == device.getAppId()) {
                    plcIds.add(device.getAxPlcId());
                }
            }
        }
        return plcIds;
    }


    public static List<Integer> getAppNumberByDeviceId(int deviceId) {
        List<Integer> appIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getDeviceId() == deviceId) {
                    appIds.add(device.getAppId());
                }
            }
        }
        return appIds;
    }


    public static List<Integer> getDeviceNumberByDeviceGroupId(int deviceGroupId) {
        List<Integer> deviceIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getDeviceGroupId() == deviceGroupId) {
                    deviceIds.add(device.getDeviceId());
                }
            }
        }
        return deviceIds;
    }


    public static List<Integer> getDeviceNumberByPlcId(int plcId) {
        List<Integer> deviceIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getAxPlcId() == plcId) {
                    deviceIds.add(device.getDeviceId());
                }
            }
        }
        return deviceIds;
    }


    public static List<Integer> getDeviceNumberByAppId(int appId) {
        List<Integer> deviceIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getAppId() == appId) {
                    deviceIds.add(device.getDeviceId());
                }
            }
        }
        return deviceIds;
    }

    public static Integer getDeviceGroupIdByDeviceId(int deviceId) {

        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getDeviceId() == deviceId) {
                    return device.getDeviceGroupId();
                }
            }
        }
        return 0;
    }

    public static List<Integer> getAppNumberByDeviceGroupId(int deviceGroupId) {
        List<Integer> appIds = new ArrayList<>();
        if (!DEVICE_MAP.isEmpty()) {
            for (Device device : DEVICE_MAP.values()) {
                if (device.getDeviceGroupId() == deviceGroupId) {
                    appIds.add(device.getAppId());
                }
            }
        }
        return appIds;
    }
}
