package com.dod.sport.controller;

import com.dod.sport.domain.common.BusiException;
import com.dod.sport.service.IUserService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/9.
 */
@Controller
@RequestMapping(value = "api/common/v1")
public class CommonController {

    /**
     * 获取api访问token
     * @param id 开发者账号
     * @param password 开发者密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Map getToken(@RequestParam(value = "id") final String id,
                        @RequestParam(value = "password") final String password){
        Map map = new HashMap<String,String>();
        if (!"dodsport".equals(id) || !"dodsport".equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_noauth.getValue());
        }
        String tokenStr = RedisCommon.getApiAccessToken();
        map.put("token",tokenStr);
        return map;
    }

    /**
     * 获取验证码
     * @param phoneNum 用户手机号
     * @param operationId 1,注册；2，忘记密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIdentcode", method = RequestMethod.POST)
    public Map getIdentifyingCode(@RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "operationId") final String operationId){
        String identStr = "";
        Map map = new HashMap<String,String>();
        if ("".equals(phoneNum)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        identStr = RedisCommon.createIdentifyingCode(phoneNum,operationId);
        map.put("identifyingCode",identStr);
        return map;
    }
}
