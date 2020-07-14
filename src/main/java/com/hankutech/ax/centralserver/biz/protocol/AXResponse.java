package com.hankutech.ax.centralserver.biz.protocol;

import com.hankutech.ax.centralserver.biz.data.AIResultWrapper;
import com.hankutech.ax.centralserver.biz.code.AITaskType;
import com.hankutech.ax.centralserver.biz.code.ScenarioFlag;
import com.hankutech.ax.centralserver.biz.code.SysRunFlag;
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
    AIResultWrapper aiResult;

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
        resp.setAiResult(new AIResultWrapper());
        return resp;
    }

    /**
     * 验证是否有效的数据结构
     *
     * @return
     */
    public boolean isValid() {
        if (this.getSysRunFlag().equals(SysRunFlag.EMPTY)
                || this.getCameraNumber() <= 0
                || this.getScenarioFlag().equals(ScenarioFlag.EMPTY)
                || this.getTaskType().equals(AITaskType.EMPTY)
        ) {
            return false;
        }

        return true;
    }
}
