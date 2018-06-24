package com.dod.sport.controller;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IUserDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.ManagerUser;
import com.dod.sport.service.IUserService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


/**
 * 用户信息管理
 * Created by defi on 2017/7/22.
 */

@Controller
@RequestMapping(value = "api/userManager/v1")
public class UserManagerController {
    //private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    @Autowired
    IUserService userService;

    @Autowired
    IUserDao userDao;

    /**
     * 获取管理端员工列表
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listManagerUser", method = RequestMethod.POST)
    public HashMap<String,Object> listManagerUser(@RequestParam("page") String page){
        HashMap<String,Object> hashMap = new HashMap<>();
        List<ManagerUser> managerUserList = userService.listManagerUser(Integer.valueOf(page));
        String count = userDao.getManagerUserCount();
        hashMap.put("managerUserList",managerUserList);
        hashMap.put("count",count);
        return hashMap;
    }

    /**
     * 新增管理端用户
     * @param employeeName
     * @param password
     * @param sex
     * @param phoneNum
     * @param idCard
     * @param positionId
     * @param depId
     * @param entryDate
     * @param roleId
     * @param creator
     */
    @ResponseBody
    @RequestMapping(value = "/addManagerUser", method = RequestMethod.POST)
    public String addManagerUser(@RequestParam("employeeName") String employeeName,
                               @RequestParam("password") String password,
                               @RequestParam("sex") String sex,
                               @RequestParam("phoneNum") String phoneNum,
                               @RequestParam("idCard") String idCard,
                               @RequestParam("positionId") String positionId,
                               @RequestParam("depId") String depId,
                               @RequestParam("entryDate") String entryDate,
                               @RequestParam("roleId") String roleId,
                               @RequestParam("creator") String creator){

        ManagerUser managerUser = new ManagerUser();
        managerUser.setEmployeeName(employeeName);
        managerUser.setPassword(password);
        managerUser.setSex(sex);
        managerUser.setPhoneNum(phoneNum);
        managerUser.setIdCard(idCard);
        managerUser.setPositionId(positionId);
        managerUser.setDepId(depId);
        managerUser.setEntryDate(entryDate);
        managerUser.setRoleId(roleId);
        managerUser.setCreator(creator);
        userService.addManagerUser(managerUser);
        return "";
    }

    /**
     * 修改管理端员工信息
     * @param id
     * @param employeeName
     * @param password
     * @param sex
     * @param phoneNum
     * @param idCard
     * @param positionId
     * @param depId
     * @param entryDate
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editManagerUser", method = RequestMethod.POST)
    public String editManagerUser(@RequestParam("id") String id,
                                  @RequestParam("employeeName") String employeeName,
                                  @RequestParam("password") String password,
                                  @RequestParam("sex") String sex,
                                  @RequestParam("phoneNum") String phoneNum,
                                  @RequestParam("idCard") String idCard,
                                  @RequestParam("positionId") String positionId,
                                  @RequestParam("depId") String depId,
                                  @RequestParam("entryDate") String entryDate,
                                  @RequestParam("roleId") String roleId){

        ManagerUser managerUser = new ManagerUser();
        managerUser.setId(id);
        managerUser.setEmployeeName(employeeName);
        managerUser.setPassword(password);
        managerUser.setSex(sex);
        managerUser.setPhoneNum(phoneNum);
        managerUser.setIdCard(idCard);
        managerUser.setPositionId(positionId);
        managerUser.setDepId(depId);
        managerUser.setEntryDate(entryDate);
        managerUser.setRoleId(roleId);
        String isExists = userDao.isExistsUserPhoneNum(phoneNum, id);
        if (!isExists.equals("0")){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_manageruserphoneexits.getValue());
        }
        userDao.updateManagerUser(managerUser);
        return "";
    }

    /**
     * 删除管理端用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteManagerUser", method = RequestMethod.POST)
    public String deleteManagerUser(@RequestParam("id") String id){
        ManagerUser managerUser = new ManagerUser();
        managerUser.setId(id);
        managerUser.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        userDao.updateManagerUser(managerUser);
        return "";
    }

    /**
     * 修改管理端用户密码
     * @param password
     * @param repassword
     * @param phoneNum
     */
    @RequestMapping(value = "/editManagerUserPassword",method = RequestMethod.POST )
    @ResponseBody
    public String editManagerUserPassword(@RequestParam(value = "password") final String password,
                                          @RequestParam(value = "repassword") final String repassword,
                                          @RequestParam(value = "phoneNum") final String phoneNum){
        if(password=="" || repassword==""){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_passworderror.getValue());
        }
        if(!repassword.equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_passwordnotmatch.getValue());
        }
        if(phoneNum=="" || phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_phonenumerror.getValue());
        }
        ManagerUser managerUser = new ManagerUser();
        managerUser.setPhoneNum(phoneNum);
        managerUser = userDao.getUserInfoByPhoneNum(managerUser);
        if(managerUser==null){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_nodata.getValue());
        }
        managerUser.setPassword(password);
        userDao.updateManagerUser(managerUser);
        return "";
    }

    /**
     * 验证码校验
     * @param phoneNum
     * @param identifyingCode
     * @param operationId 1,注册；2，忘记密码
     */
    @RequestMapping(value = "/verifyIdentCode",method = RequestMethod.POST)
    @ResponseBody
    public String verifyIdentCode(@RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "operationId") final String operationId,
                                  @RequestParam(value = "identifyingCode") final String identifyingCode){
        ManagerUser managerUser = new ManagerUser();
        managerUser.setPhoneNum(phoneNum);
        managerUser = userDao.getUserInfoByPhoneNum(managerUser);
        if (!RedisCommon.verifyIdentCode(phoneNum, identifyingCode, operationId)){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_identifyingcode.getValue());
        }
        if ("1".equals(operationId)){
            //注册
            if (managerUser!=null){
                throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_employeeexists.getValue());
            }
        }else if ("2".equals(operationId)){
            //找回密码
            if (managerUser==null){
                throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_nodata.getValue());
            }
        }else{
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        return "";
    }
}
