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

        return resultArray;
    }


    /**
     * 解析字节数据为PlcResponse
     *
     * @param convertedData
     * @return
     */
    public static PlcResponse parseResponse(int[] convertedData) {

        if (convertedData == null || convertedData.length != 10) {
            return null;
        }

        PlcResponse axPlcResponse = new PlcResponse();
        //  X1标示消息来源
        axPlcResponse.setMessageSource(MessageSource.valueOf(convertedData[0]));

        //  字节X2,字节X3标示艾信PLC的编号 x2 +(x3*255)
        int c1 = convertedData[1];
        int c2 = convertedData[2];
        int plcNumber = c1 + c2 * 255;
        axPlcResponse.setPlcNumber(plcNumber);

        //X4标示消息类型
        axPlcResponse.setMessageType(PlcMessageType.valueOf(convertedData[3]));
        //X5标示数据
        axPlcResponse.setPayload(convertedData[4]);
        return axPlcResponse;
    }

    /**
     * 转换AXRequest为字节形式
     *
     * @param request
     * @return
     */
    public static int[] convertRequest(PlcRequest request) {

        int[] resultArray = new int[10];

        // X1标示消息来源
        resultArray[0] = request.getMessageSource().getValue();

        // //  字节X2,字节X3标示艾信PLC的编号 x2 +(x3*255)
        int plcNumber = request.getPlcNumber();
        if (plcNumber > 255) {
            resultArray[1] = plcNumber % 255;
            resultArray[2] = (int) Math.floor(plcNumber / 255);
        } else {
            resultArray[1] = plcNumber;
        }

        //X4标示消息类型
        resultArray[3] = request.getMessageType().value;
        //X5标示数据
        resultArray[4] = request.getPayload();

        return resultArray;
    }

}
