package com.hankutech.ax.centralserver.bizmessage;

import java.util.ArrayList;
import java.util.List;

public class DeviceRelationManager {
    public static List<Integer> getAppNumber(int plcNumber) {
        //TODO
        List<Integer> list = new ArrayList<>();
        list.add(plcNumber);
        return list;
    }

    public static List<Integer> getPlcNumber(int appNumber) {
        //TODO
        List<Integer> list = new ArrayList<>();
        list.add(appNumber);
        return list;
    }

    public static List<Integer> getDeviceIdByPlcNumber(int plcNumber) {
        //TODO
        List<Integer> list = new ArrayList<>();
        list.add(plcNumber);
        return list;
    }

    public static List<Integer> getDeviceIdByAppNumber(int appNumber) {
        //TODO
        List<Integer> list = new ArrayList<>();
        list.add(appNumber);
        return list;
    }
}
