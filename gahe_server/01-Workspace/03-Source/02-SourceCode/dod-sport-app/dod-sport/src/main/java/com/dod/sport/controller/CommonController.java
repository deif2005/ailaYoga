package com.dod.sport.controller;


import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.service.ICommonService;
import com.dod.sport.service.IEmployeeService;
import com.dod.sport.service.ISMSExampleService;
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
    @Autowired
    private ICommonService commonService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISMSExampleService exampleService;

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
     * @param phoneNum
     * @param operationId 1:注册;2:忘记密码
     * @param platformId 3:客户端;2:商家端;1:服务端
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getIdentifyingCode", method = RequestMethod.POST)
    public String getIdentifyingCode(@RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "platformId") final String platformId,
                                  @RequestParam(value = "operationId") final String operationId){
        if ("".equals(phoneNum)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        //短信推送
       commonService.getIdentifyingCode(phoneNum, platformId, operationId);
        return "";
    }

    /**
     * 验证码验证
     * @param phoneNum
     * @param identifyingCode
     * @param operationId 1:注册;2:忘记密码
     * @param platformId 1:客户端;2:商家端;3:服务端
     */
    @RequestMapping(value = "/verifyIdentCode",method = RequestMethod.POST )
    @ResponseBody
    public Map verifyIdentCode( @RequestParam(value = "phoneNum") final String phoneNum,
                                @RequestParam(value = "platformId") final String platformId,
                                @RequestParam(value = "operationId") final String operationId,
                                @RequestParam(value = "identifyingCode") final String identifyingCode){
        Map<String,Object> map  =commonService.verifyIdentCode(phoneNum, platformId, operationId,identifyingCode);
        return map;
    }
}

