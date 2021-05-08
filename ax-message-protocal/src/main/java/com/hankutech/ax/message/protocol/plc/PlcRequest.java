package com.hankutech.ax.message.protocol.plc;

import com.hankutech.ax.message.protocol.MessageSource;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlcRequest {

    /**
     * 字节X1标示标示消息来源
     */
    MessageSource messageSource = MessageSource.PLC;

    /**
     * 艾信PLC的编号
     * 由X2, X3两个字节组合而成
     */
    int plcNumber;

    /**
     * 字节X4标示消息类型
     */
    PlcMessageType messageType;

    /**
     * X5标示数据
     */
    int payload;

    /**
     * 摄像头的编号
     * 由X6, X7两个字节组合而成
     */
    int cameraNumber;

    /**
     * 验证是否有效的数据结构
     *
     * @return
     */
    public boolean isValid() {
        if (this.getMessageSource().equals(MessageSource.EMPTY)
                || this.getPlcNumber() <= 0
                || this.getMessageType().equals(PlcMessageType.EMPTY)) {
            return false;
        }

        return true;
    }
}
