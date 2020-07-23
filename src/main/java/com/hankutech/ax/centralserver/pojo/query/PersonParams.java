package com.hankutech.ax.centralserver.pojo.query;

import com.hankutech.ax.centralserver.pojo.request.QueryParams;
import lombok.Data;

@Data
public class PersonParams extends QueryParams {
    String personName;
}
