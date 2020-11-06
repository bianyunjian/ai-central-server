package com.hankutech.ax.centralserver.pojo.query;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class QRCodeEventParams {

    private List<Integer> deviceIdList;
}
