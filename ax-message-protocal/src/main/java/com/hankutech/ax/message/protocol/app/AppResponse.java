package com.hankutech.ax.message.protocol.app;

import com.hankutech.ax.message.protocol.MessageSource;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AppResponse {

    /**
     * 字节X1标示标示消息来源
     */
    MessageSource messageSource = MessageSource.CENTRAL_SERVER;

    /**
     * App的编号
     * 由X2, X3两个字节组合而成
     */
    int appNumber;

    /**
     * 字节X4标示消息类型
     */
    AppMessageType messageType;

    /**
     * X5标示数据
     */
    int payload;

    public static AppResponse defaultEmpty() {
        AppResponse instance = new AppResponse();
        instance.setMessageSource(MessageSource.CENTRAL_SERVER);
        instance.setMessageType(AppMessageType.EMPTY);
        return instance;
    }

    /**
     * 验证是否有效的数据结构
     *
     * @return
     */
    public boolean isValid() {
        if (this.getMessageSource().equals(MessageSource.EMPTY)
                || this.getAppNumber() <= 0
                || this.getMessageType().equals(AppMessageType.EMPTY)) {
            return false;
        }

        return true;
    }

}
