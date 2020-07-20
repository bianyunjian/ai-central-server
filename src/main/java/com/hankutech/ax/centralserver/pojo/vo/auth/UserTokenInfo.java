package com.hankutech.ax.centralserver.pojo.vo.auth;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserTokenInfo {

    String userId;
    String userName;
    String displayName;
    String accessToken;
    String refreshToken;
    Date issuedAt;
    Date expiresAt;

    boolean isValid;


}
