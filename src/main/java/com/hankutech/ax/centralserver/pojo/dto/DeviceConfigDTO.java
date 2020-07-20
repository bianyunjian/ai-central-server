package com.hankutech.ax.centralserver.pojo.dto;

import com.hankutech.ax.centralserver.pojo.vo.AiTypeConfigVO;
import lombok.Data;

import java.util.List;

/**
 * 设备配置参数DTO
 *
 * @author ZhangXi
 */
@Data
public class DeviceConfigDTO {

    private Integer deviceId;

    private List<AiTypeConfigVO> aiTypeConfig;

}
