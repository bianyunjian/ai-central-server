package com.hankutech.ax.message.protocol.app;


import com.hankutech.ax.message.protocol.MessageSource;

public class AppDataConverter {
    /**
     * 解析字节数据为AppRequest
     *
     * @param convertedData
     * @return
     */
    public static AppRequest parseRequest(int[] convertedData) {

        if (convertedData == null || convertedData.length != 10) {
            return null;
        }

        AppRequest appRequest = new AppRequest();
        //  X1标示消息来源
        appRequest.setMessageSource(MessageSource.valueOf(convertedData[0]));

        //  字节X2,字节X3标示App的编号 x2 +(x3*255)
        int c1 = convertedData[1];
        int c2 = convertedData[2];
        int appNumber = c1 + c2 * 255;
        appRequest.setAppNumber(appNumber);

        //X4标示消息类型
        appRequest.setMessageType(AppMessageType.valueOf(convertedData[3]));
        //X5标示数据
        appRequest.setPayload(convertedData[4]);
        return appRequest;
    }

    /**
     * 转换AppResponse为字节形式
     *
     * @param resp
     * @return
     */
    public static int[] convertResponse(AppResponse resp) {

        int[] resultArray = new int[10];

        // X1标示消息来源
        resultArray[0] = resp.getMessageSource().getValue();

        // //  字节X2,字节X3标示App的编号 x2 +(x3*255)
        int appNumber = resp.getAppNumber();
        if (appNumber > 255) {
            resultArray[1] = appNumber % 255;
            resultArray[2] = (int) Math.floor(appNumber / 255);
        } else {
            resultArray[1] = appNumber;
        }

        //X4标示消息类型
        resultArray[3] = resp.getMessageType().value;
        //X5标示数据
        resultArray[4] = resp.getPayload();

        return resultArray;
    }


}
