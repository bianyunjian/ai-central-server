package com.hankutech.ax.centralserver.biz;

import com.hankutech.ax.centralserver.biz.code.AITaskType;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import com.hankutech.ax.centralserver.biz.code.SysRunFlag;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AXRequest {

    /**
     * 字节X1标示艾信控制器系统状态
     */
    SysRunFlag sysRunFlag;

    /**
     * 摄像头的编号
     * 由X3, X4两个字节组合而成
     */
    int CameraNumber;

    /**
     * 字节X9标示场景应用
     */
    ScenarioFlag scenarioFlag;

    /**
     * X10标示任务类型
     */
    AITaskType taskType;


}
