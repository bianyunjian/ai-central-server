package com.hankutech.ax.centralserver.biz;

import com.hankutech.ax.centralserver.biz.code.AITaskType;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import com.hankutech.ax.centralserver.biz.code.SysRunFlag;

public class AXDataConverter {
    public static AXRequest parseRequest(int[] convertedData) {

        if (convertedData == null || convertedData.length != 10) {
            return null;
        }

        AXRequest axRequest = new AXRequest();
        //  X1标示艾信控制器系统状态，5标示停止，8标示运行
        axRequest.setSysRunFlag(SysRunFlag.valueOf(convertedData[0]));
        //  X9标示场景应用，1标示中型物流护士站点，2标示气力式垃圾站点投递口；
        axRequest.setScenarioFlag(ScenarioFlag.valueOf(convertedData[8]));
        //  X10标示任务类型
        axRequest.setTaskType(AITaskType.valueOf(convertedData[9]));

        //  字节X3标示第一组摄像头的编号,字节X4标示第二组摄像头的编号
        int c1 = convertedData[2];
        int c2 = convertedData[3];
        int cameraNumber = c1 + c2;
        axRequest.setCameraNumber(cameraNumber);

        return axRequest;
    }

    public static int[] convertResponse(AXResponse resp) {

        int[] resultArray = new int[10];

        // X1标示视频智能算法控制器状态，5标示停止，8标示运行；
        resultArray[0] = resp.getSysRunFlag().getValue();

        // 字节X3标示第一组摄像头的编号,字节X4标示第二组摄像头的编号
        int cameraNumber = resp.getCameraNumber();
        if (cameraNumber > 255) {
            resultArray[2] = 255;
            resultArray[3] = cameraNumber % 255;
        } else {
            resultArray[2] = cameraNumber;
        }

        // 字节X8标示应用场景，1标示中型物流护士站点，2标示气动物流投递口
        resultArray[7] = resp.getScenarioFlag().getValue();

        // 字节X9标示任务类型
        resultArray[8] = resp.getTaskType().getValue();

        // 字节X10标示检测结果
        resultArray[9] = resp.getAiResult().getValue();

        return resultArray;
    }


}
