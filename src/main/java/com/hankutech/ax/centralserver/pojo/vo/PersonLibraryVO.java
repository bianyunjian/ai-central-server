package com.hankutech.ax.centralserver.pojo.vo;

import com.hankutech.ax.centralserver.dao.model.Person;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PersonLibraryVO {

    String deviceName;
    ArrayList<PersonVO> personList;
}
