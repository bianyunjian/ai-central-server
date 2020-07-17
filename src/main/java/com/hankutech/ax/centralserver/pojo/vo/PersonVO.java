package com.hankutech.ax.centralserver.pojo.vo;

import lombok.Data;

@Data
public class PersonVO {

    String name;
    int id;
    public static final int FTR_LENGTH = 512;
    Float[] faceFtrArray = new Float[0];
}
