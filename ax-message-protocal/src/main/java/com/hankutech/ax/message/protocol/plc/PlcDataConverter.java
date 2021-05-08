package com.hankutech.ax.message.protocol.plc;


import com.hankutech.ax.message.protocol.MessageSource;

public class PlcDataConverter {
    /**
     * 解析字节数据为AXPLCRequest
     *
     * @param convertedData
     * @return
     */
    public static PlcRequest parseRequest(int[] convertedData) {

        if (convertedData == null || convertedData.length != 10) {
            return null;
        }

        PlcRequest axRequest = new PlcRequest();
        //  X1标示消息来源
        axRequest.setMessageSource(MessageSource.valueOf(convertedData[0]));

        //  字节X2,字节X3标示艾信PLC的编号 x2 +(x3*255)
        int c1 = convertedData[1];
        int c2 = convertedData[2];
        int plcNumber = c1 + c2 * 255;
        axRequest.setPlcNumber(plcNumber);

        //X4标示消息类型
        axRequest.setMessageType(PlcMessageType.valueOf(convertedData[3]));
        //X5标示数据
        axRequest.setPayload(convertedData[4]);


        // 字节X6,字节X7标示摄像头编号 x6 +(x7*255)
        int c5 = convertedData[5];
        int c6 = convertedData[6];
        int cameraNumber = c5 + c6 * 255;
        axRequest.setCameraNumber(cameraNumber);
        return axRequest;
    }

    /**
     * 转换AXResponse为字节形式
     *
     * @param resp
     * @return
     */
    public static int[] convertResponse(PlcResponse resp) {

        int[] resultArray = new int[10];

        // X1标示消息来源
        resultArray[0] = resp.getMessageSource().getValue();

        // //  字节X2,字节X3标示艾信PLC的编号 x2 +(x3*255)
        int plcNumber = resp.getPlcNumber();
        if (plcNumber > 255) {
            resultArray[1] = plcNumber % 255;
            resultArray[2] = (int) Math.floor(plcNumber / 255);
        } else {
            resultArray[1] = plcNumber;
        }

        //X4标示消息类型
        resultArray[3] = resp.getMessageType().value;
        //X5标示数据
        resultArray[4] = resp.getPayload();


        // 字节X6,字节X7标示摄像头编号 x6 +(x7*255)
        int cameraNumber = resp.getCameraNumber();
        if (cameraNumber > 255) {
            resultArray[5] = cameraNumber % 255;
            resultArray[6] = (int) Math.floor(cameraNumber / 255);
        } else {
            resultArray[5] = cameraNumber;
        }
        return resultArray;
    }


}
