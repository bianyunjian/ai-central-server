package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Device;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author ZhangXi
 */
@Schema(description = "带人员信息的设备数据")
public class DeviceWithPersonVO extends DeviceVO {

    @Schema(description = "人员信息列表")
    private List<PersonVO> personList;


    public DeviceWithPersonVO(Device device, List<PersonVO> persons) {
        super(device);
        this.personList = persons;
    }

    public List<PersonVO> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonVO> personList) {
        this.personList = personList;
    }
}
