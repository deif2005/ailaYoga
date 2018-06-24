package com.dod.sport.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/18.
 */

public interface ICommonService {

    /**
     * 获取验证码,并进行短信推送
     * @param phoneNum 电话
     * @param platformId 1:客户端;2:商家端;3:服务端
     * @param operationId 1:注册;2:忘记密码
     * @return
     */
    public void getIdentifyingCode(String phoneNum,String platformId,String operationId);

    /**
     * 验证验证码
     * @param phoneNum 电话
     * @param platformId 1:客户端;2:商家端;3:服务端
     * @param operationId 1:注册;2:忘记密码
     * @param identifyingCode 验证码
     * @return
     */
    public Map<String ,Object> verifyIdentCode(String phoneNum,String platformId,String operationId, String identifyingCode );
}
