package com.hankutech.ax.centralserver.pojo.request;

import lombok.Data;

@Data
public class PersonAddRequest {
    String personName;
    String image;
    String phoneNum;
}
