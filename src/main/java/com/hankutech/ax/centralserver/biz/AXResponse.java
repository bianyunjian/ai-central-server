package com.hankutech.ax.centralserver.biz;

import com.hankutech.ax.centralserver.biz.code.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AXResponse {

    /**
     * 字节X1标示视频智能算法控制器状态
     */
    SysRunFlag sysRunFlag;

    /**
     * 摄像头的编号
     * 由X3, X4两个字节组合而成
     */
    int CameraNumber;

    /**
     * 字节X8标示场景应用
     */
    ScenarioFlag scenarioFlag;

    /**
     * 字节X9标示任务类型
     */
    AITaskType taskType;

    /**
     * 字节X10标示检测结果
     */
    AIResult aiResult;

    /**
     * get a empty response as default
     *
     * @return
     */
    public static AXResponse DefaultEmpty() {
        AXResponse resp = new AXResponse();
        resp.setSysRunFlag(SysRunFlag.EMPTY);
        resp.setCameraNumber(0);
        resp.setScenarioFlag(ScenarioFlag.EMPTY);
        resp.setTaskType(AITaskType.EMPTY);
        resp.setAiResult(AIBoxResultType.EMPTY);
        return resp;
    }
}
