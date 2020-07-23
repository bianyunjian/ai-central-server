package com.hankutech.ax.centralserver.pojo.vo;


import com.hankutech.ax.centralserver.dao.model.Person;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class PersonVO {
    private Integer id;

    private String name;

    private Float[] faceFtrArray;

    private String image;
    private String phoneNum;

    public PersonVO(Person person) {
        if (null != person) {
            this.id = person.getPersonId();
            this.name = person.getPersonName();

            String faceFTRStr = (StringUtils.isEmpty(person.getFaceFtrArray()) ? "" : person.getFaceFtrArray()).replace('[', ' ').replace(']', ' ').trim();

            if (StringUtils.isEmpty(faceFTRStr) == false) {

                Float[] ftrArray = (Arrays.stream(faceFTRStr
                        .split(","))
                        .map(s -> Float.parseFloat(s))
                        .collect(Collectors.toList())).toArray(new Float[0]);
                this.setFaceFtrArray(ftrArray);
            }
            this.setImage(person.getImage());
            this.setPhoneNum(person.getPhoneNum());
        }
    }
}
