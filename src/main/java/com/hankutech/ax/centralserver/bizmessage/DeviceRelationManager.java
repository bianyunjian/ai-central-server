package com.hankutech.ax.centralserver.bizmessage;

import java.util.ArrayList;
import java.util.List;

public class DeviceRelationManager {
    public static List<Integer> getAppNumber(int plcNumber) {
        List<Integer> list = new ArrayList<>();
        list.add(plcNumber);
        return list;
    }

    public static List<Integer> getPlcNumber(int appNumber) {
        List<Integer> list = new ArrayList<>();
        list.add(appNumber);
        return list;
    }

    public static int getDeviceIdByPlcNumber(int plcNumber) {
        return plcNumber;
    }

    public static int getDeviceIdByAppNumber(int appNumber) {
        return appNumber;
    }
}
