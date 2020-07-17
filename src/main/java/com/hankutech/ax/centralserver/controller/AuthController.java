package com.hankutech.ax.centralserver.controller;


import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.vo.auth.*;
import com.hankutech.ax.centralserver.support.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @PostMapping(value = "/login")
    public BaseResponse<LoginResp> login(@RequestBody @Validated LoginReq param) {

        BaseResponse<LoginResp> obj = new BaseResponse<LoginResp>();
        LoginResp resp = new LoginResp();

        String passwordBase64 = "MTIzNDU2"; //"123456";
        String userName = param.getUserName();
        String userId = userName;

        boolean checkUserName = userName.equals("admin@ax.com");
        boolean checkPassword = TokenUtil.getInstance().verifyPassword(param.getPassword(), passwordBase64) == false;
        if (checkUserName && checkPassword) {
            obj.fail("username or password incorrect");
            return obj;
        }
        resp.setUserId(userId);
        resp.setUserName(userName);
        resp.setLoginName(param.getUserName());

        Date issueAt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TokenUtil.getInstance().TOKEN_EXPIRE_MINUTES);
        Date expireAt = calendar.getTime();
        resp.setIssuedAt(issueAt);
        resp.setExpiresAt(expireAt);

        String[] audienceArray = {userId, userName};
        String accessToken = TokenUtil.getInstance().generateAccessToken(issueAt, expireAt, audienceArray);
        resp.setAccessToken(accessToken);
        resp.setRefreshToken(accessToken);
        resp.setValid(true);
        obj.success("success", resp);

        //TokenManager加入到缓存中
//        TokenManager.updateTokenCache(resp);
        return obj;
    }


    @PostMapping(value = "/loginOut")
    public BaseResponse<LoginOutResp> loginOut(@RequestBody @Validated LoginOutReq param) {

        BaseResponse<LoginOutResp> obj = new BaseResponse<>();
        LoginOutResp resp = new LoginOutResp();

        if (param != null && StringUtils.isEmpty(param.getAccessToken()) == false) {
            UserTokenInfo userTokenInfo = TokenUtil.getInstance().decodeToken(param.getAccessToken());
            if (userTokenInfo != null
                    && StringUtils.isEmpty(userTokenInfo.getUserId()) == false) {
                //从缓存中移除
//                TokenManager.removeTokenCache(userTokenInfo.getUserId());
                obj.success("success login out");
                return obj;
            }
        }
        obj.fail("fail to login out");
        return obj;
    }

}