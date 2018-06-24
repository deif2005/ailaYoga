package com.dod.sport.service.impl;

import com.dod.sport.constant.MessageConstants;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Base.EmployeeInfo;
import com.dod.sport.domain.po.ResponseEmployee;
import com.dod.sport.service.*;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/18.
 */
@Service
public class CommonServiceImpl implements ICommonService{
    @Autowired
    private IEmployeeDao employeeDao;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IClientUserService clientUserService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private ISMSExampleService smsExampleService;
    /**
     * 获取验证码
     * @param phoneNum 电话
     * @param platformId 1:后台管理端，2商家端，3客户端
     * @param operationId 1:注册;2:忘记密码
     * @return
     */
    @Override
    public void getIdentifyingCode(String phoneNum, String platformId, String operationId) {
        //1.生成验证码
        String keyStr = RedisCommon.createIdentifyingCode(phoneNum, platformId,operationId);
        System.out.println("获取注册验证码:"+keyStr);
        String tempId ="";
        if (CommonEnum.Common.platformId.client_side.getValue().equals(platformId)){//客户端
            if (CommonEnum.Common.operationId.register.getValue().equals(operationId)){//注册
                tempId = MessageConstants.REGISTER_EMPLOYEE_MODE_ID;
            }else if (CommonEnum.Common.operationId.lost_password.getValue().equals(operationId)){//忘记密码
                tempId = MessageConstants.UPDATE_EMPLOYEE_PASSWORD_MODE_ID;
            }
        }else if (CommonEnum.Common.platformId.business_side.getValue().equals(platformId)){//商家端
            if (CommonEnum.Common.operationId.register.getValue().equals(operationId)){//注册
                tempId = MessageConstants.REGISTER_EMPLOYEE_MODE_ID;
            }else if (CommonEnum.Common.operationId.lost_password.getValue().equals(operationId)){//忘记密码
                tempId = MessageConstants.UPDATE_EMPLOYEE_PASSWORD_MODE_ID;
            }else if (CommonEnum.Common.operationId.add_user.getValue().equals(operationId)){//新增平台用户
                tempId = MessageConstants.ADD_USER_MODE_ID;
            }
        }else if (CommonEnum.Common.platformId.service_sid.getValue().equals(platformId)){//服务端
            tempId = "";
        }
        smsExampleService.sendSMSCode(phoneNum,keyStr,tempId);
    }

    /**
     * 验证验证码
     * @param phoneNum 电话
     * @param platformId 1后台管理端，2商家端，3客户端
     * @param operationId 1:注册;2:忘记密码
     * @param identifyingCode 验证码
     * @return
     */
    @Override
    public Map<String ,Object> verifyIdentCode(String phoneNum, String platformId, String operationId, String identifyingCode ) {
        Map<String ,Object> map =new  HashMap();
        //验证码校验
        if (RedisCommon.verifyIdentCode(phoneNum,platformId,operationId, identifyingCode)==false){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_identifyingcode.getValue());
        }
        if (CommonEnum.Common.platformId.client_side.getValue().equals(platformId)){//客户端
            ClientUser clientUser = clientUserService.getClientUserByPhone(phoneNum);
            if (CommonEnum.Common.operationId.register.getValue().equals(operationId)){//注册
                if (clientUser!=null){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_clientuserxists.getValue());//用户已存在
                }
            }else if (CommonEnum.Common.operationId.lost_password.getValue().equals(operationId)){//忘记密码
                if (clientUser==null){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_clientusernotxists.getValue());//用户不存在
                }
            }
        }else if (CommonEnum.Common.platformId.business_side.getValue().equals(platformId)){//商家端

           EmployeeInfo emp = employeeDao.getEmployeeByPhoneNum(phoneNum);
            if (CommonEnum.Common.operationId.register.getValue().equals(operationId)){//注册
                if (emp == null ) {
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotexists.getValue());//该员工不存在
                }
                if(emp.getRegisterType().equals(CommonEnum.Employee.registerType.registered.getValue())){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_registered.getValue());//该员工已注册
                }
                ResponseEmployee remp = employeeService.getEmpBusinessInfoByPhoneNum(phoneNum);
                map.put("remp",remp);
            }else if (CommonEnum.Common.operationId.lost_password.getValue().equals(operationId)){//忘记密码
                if(emp==null){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotexists.getValue());//该员工不存在
                }
            }
        }else if (CommonEnum.Common.platformId.service_sid.getValue().equals(platformId)){//服务端
            if (CommonEnum.Common.operationId.register.getValue().equals(operationId)){//注册
                //后续完成
            }else if (CommonEnum.Common.operationId.lost_password.getValue().equals(operationId)){//忘记密码
                //后续完成
            }
        }
        return map;
    }
}
