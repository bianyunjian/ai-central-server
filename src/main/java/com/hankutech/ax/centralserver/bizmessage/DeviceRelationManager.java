package com.hankutech.ax.centralserver.bizmessage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankutech.ax.centralserver.dao.DeviceCameraDao;
import com.hankutech.ax.centralserver.dao.model.DeviceCamera;
import com.hankutech.ax.centralserver.service.DeviceCache;
import com.hankutech.ax.message.code.AIGarbageResultType;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DeviceRelationManager {

    public DeviceRelationManager(DeviceCameraDao deviceCameraDao) {
        this.deviceCameraDao = deviceCameraDao;
    }

    public static List<Integer> getAppNumber(int plcNumber) {
        return DeviceCache.getAppNumberByPlcId(plcNumber);
    }

    public static List<Integer> getPlcNumber(int appNumber) {
        return DeviceCache.getPlcNumberByAppId(appNumber);
    }

    public static List<Integer> getDeviceIdByDeviceName(String deviceName) {
        return DeviceCache.getDeviceIdByDeviceName(deviceName);
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


    public static DeviceCameraDao deviceCameraDao;

    /**
     * 根据Plc编号和摄像头编号查找到具体的设备编号。
     * 摄像头编号大于0， 则匹配摄像头编号
     * 摄像头编号等于0， 则忽略摄像头编号
     *
     * @param plcNumber
     * @param cameraNumber
     * @return
     */
    public static List<Integer> getDeviceIdByPlcNumberAndCameraNumber(int plcNumber, int cameraNumber) {
        List<Integer> deviceIdList = DeviceCache.getDeviceNumberByPlcId(plcNumber);

        if (cameraNumber <= 0) {
            return deviceIdList;
        }


        QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeviceCamera.COL_CAMERA_ID, cameraNumber);
        queryWrapper.in(DeviceCamera.COL_DEVICE_ID, deviceIdList);
        List<DeviceCamera> deviceCamerasList = deviceCameraDao.selectList(queryWrapper);
        if (deviceCamerasList != null) {
            return deviceCamerasList.stream().map(t -> t.getDeviceId()).collect(Collectors.toList());
        }
        return null;
    }

    public static List<Integer> getDeviceIdByCameraNumber(int cameraNumber) {
        QueryWrapper<DeviceCamera> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeviceCamera.COL_CAMERA_ID, cameraNumber);
        List<DeviceCamera> deviceCamerasList = deviceCameraDao.selectList(queryWrapper);
        if (deviceCamerasList != null) {
            return deviceCamerasList.stream().map(t -> t.getDeviceId()).collect(Collectors.toList());
        }
        return null;
    }
}
