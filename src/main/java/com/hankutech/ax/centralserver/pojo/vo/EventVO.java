package com.hankutech.ax.centralserver.pojo.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
public class EventVO {
    String type;
    Integer value;

    @ToStringExclude
    String imageBase64;
}
