package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.constant.WebConstants;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.redis.RedisData;
import com.dod.sport.service.IAdminManagementService;
import com.dod.sport.service.ICommonService;
import com.dod.sport.service.IEmployeeService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 员工管理接口
 * Created by Administrator on 2017/8/11.
 */
@Controller
@RequestMapping("api/userManager/v1")
public class EmployeeController extends BaseController {
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    private IAdminManagementService adminManagementService;
    @Autowired
    private IEmployeeDao employeeDao;
    @Autowired
    private ICommonService commonService;

    /**
     * 注册时验证验验证以及获取员工信息
     * @param phoneNum 电话
     * @param platformId 1:客户端;2:商家端;3:服务端
     * @param operationId 1:注册;2:忘记密码
     * @param identifyingCode 验证码
     * @return
     */
    @RequestMapping(value = "/registerVerifyIdentCodeAndGetBusiEmp" ,method = RequestMethod.POST)
    @ResponseBody
    public Map registerVerifyIdentCodeAndGetBusiEmp(@RequestParam(value = "phoneNum") final String phoneNum,
                                                     @RequestParam(value = "platformId") final String platformId,
                                                     @RequestParam(value = "operationId") final String operationId,
                                                     @RequestParam(value = "identifyingCode") final String identifyingCode){
      //  commonService.verifyIdentCode(phoneNum,platformId,operationId,identifyingCode);//验证验证码
        Map map = new HashMap<String,Object>();
        ResponseEmployee remp = employeeService.getEmpBusinessInfoByPhoneNum(phoneNum);
        map.put("responseEmployee",remp);
        return map;
    }

    /**
     * 获取员工商家列表
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/getBusinessListByPhoneNum" ,method = RequestMethod.POST)
    @ResponseBody
    public Map getBusinessListByPhoneNum( @RequestParam(value = "phoneNum") final String phoneNum){

        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }
        Map map = new HashMap<String,Object>();
        //根据号码查询员工商家列表
        List<BusinessInfo> businessInfoList = employeeService.getBusinessListByPhoneNum(phoneNum);
        map.put("businessInfoList",businessInfoList);
        return map;
    }

    /**
     *员工注册
     * @param password
     * @param repassword
     * @param phoneNum
     * @return 员工信息
     */
    @RequestMapping(value = "/register/registerEmployee",method = RequestMethod.POST )
    @ResponseBody
    public String registerEmployee(@RequestParam(value = "password") final String password,
                                   @RequestParam(value = "repassword") String repassword,
                                   @RequestParam(value = "phoneNum") String phoneNum){
        if("".equals(password)||"".equals(repassword)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        if(!repassword.equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passwordnotmatch.getValue());
        }
        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }

        EmployeeInfo queryemp = employeeDao.getEmployeeByPhoneNum(phoneNum);
        if(queryemp==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        if(queryemp.getRegisterType().equals(CommonEnum.Employee.registerType.registered.getValue())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_registered.getValue());
        }
        EmployeeInfo emp = new EmployeeInfo();
        emp.setPhoneNum(phoneNum);
        emp.setPassword(password);
        employeeService.registerEmployee(emp);
        return "";
    }

    /**
     * 员工登陆
     * @param password
     * @param phoneNum
     * @return
     */

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST )
    @ResponseBody
    public Map doLogin( @RequestParam(value = "password") final String password,
                        @RequestParam(value = "phoneNum") final String phoneNum){

        if("".equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passwordnotmatch.getValue());
        }
        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }
        Map map = new HashMap<String,Object>();
        EmployeeInfo em = new EmployeeInfo();
        em.setPassword(password);
        em.setPhoneNum(phoneNum);
        EmployeeInfo emp = employeeDao.getEmployeeByPhoneNum(phoneNum);
        if(emp==null){//该账号不存在
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotexists.getValue());
        }else if (emp.getRegisterType().equals("1")){//该账号未注册
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotregister.getValue());
        }

        EmployeeInfo employeeInfo = employeeDao.doLogin(em);
        if (employeeInfo==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        ResponseEmployee responseEmployee = employeeService.getEmpBusinessInfoByPhoneNum(phoneNum);
        map.put("responseEmployee",responseEmployee);
        if (responseEmployee !=null && responseEmployee.getRoleId()!=null && !"".equals(responseEmployee.getRoleId())){
            UserRole userRole = RedisData.getRedisRoleInfo(responseEmployee.getRoleId());
            request.getSession().setAttribute(WebConstants.USER_ROLE, userRole);
        }
        return map;
    }

    /**
     * 更新员工密码
     * @param password
     * @param repassword
     * @param phoneNum
     */
    @RequestMapping(value = "/getEmployeePassword",method = RequestMethod.POST )
    @ResponseBody
    public String updateEmployeePassword( @RequestParam(value = "password") final String password,
                                       @RequestParam(value = "repassword") final String repassword,
                                       @RequestParam(value = "phoneNum") final String phoneNum  ){

        if("".equals(password)||"".equals(repassword)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        if(!repassword.equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passwordnotmatch.getValue());
        }
        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }
        EmployeeInfo emp = employeeDao.getEmployeeByPhoneNum(phoneNum);
        if(emp==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        EmployeeInfo updateEmp = new EmployeeInfo();
        updateEmp.setPhoneNum(phoneNum);
        updateEmp.setPassword(password);
        employeeDao.updateEmployeePassword(updateEmp);
        return "";
    }

    /**
     * 根据电话和门店获取员工信息
     * @param phoneNum
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/queryEmployeeInfoByStoreIdAndPhoneNum",method = RequestMethod.POST )
    @ResponseBody
    public Map<String,Object> queryEmployeeInfoByStoreIdAndPhoneNum( @RequestParam(value = "phoneNum") final String phoneNum ,
                                                                     @RequestParam(value = "storeId") final String storeId ){
        ResponseEmployee remp = new ResponseEmployee();
        remp.setPhoneNum(phoneNum);
        remp.setStoreId(storeId);
        Map<String,Object> map = new HashMap<>();
        ResponseEmployee responseEmployee = employeeService.queryEmployeeInfoByStoreIdAndPhoneNum(remp);
        map.put("responseEmployee",responseEmployee);
        return map;
    }

    /**
     *员工入职
     * @param employeeName 员工姓名
     * @param phoneNum 员工电话
     * @param idCard 员工身份证
     * @param depId 部门id
     * @param positionId 职位id
     * @param empRela  聘用关系：1全职，2兼职
     * @param businessId 商家id
     * @param storeId 门店id
     * @param sex 性别:1:男;2:女;3:未知
     * @param isCoach :是否是教练:1.否;2.是
     * @param jobTitle  教练职称;1:初级教练;2:中级教练;3:高级教练
     * @param selfIntroduction 自我介绍
     * @param roleId 角色id
     * @param creator 创建人id
     * @return
     */
    @RequestMapping(value = "/addEmployeeInfo",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String addEmployeeInfo(  @RequestParam(value = "employeeName") final String employeeName,
                                    @RequestParam(value = "phoneNum") final String phoneNum,
                                    @RequestParam(value = "idCard") final String idCard,
                                    @RequestParam(value = "depId") final String depId,
                                    @RequestParam(value = "positionId") final String positionId,
                                    @RequestParam(value = "entryDate") final String entryDate,
                                    @RequestParam(value = "empRela") final String empRela,
                                    @RequestParam(value = "businessId") final String businessId,
                                    @RequestParam(value = "storeId") final String storeId,
                                    @RequestParam(value = "sex") final String sex,
                                    @RequestParam(value = "isCoach") final String isCoach,
                                    @RequestParam(value = "jobTitle", required = false) final String jobTitle,
                                    @RequestParam(value = "selfIntroduction", required = false) final String selfIntroduction,
                                    @RequestParam(value = "roleId") final String roleId,
                                    @RequestParam(value = "creator") final String creator){
        if ("".equals(employeeName)||"".equals(roleId)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }
        EmpBusinessRelation empRelation = new EmpBusinessRelation();
        EmployeeInfo emp = employeeDao.getEmployeeByPhoneNum(phoneNum);
        String empId = "";
        if(emp==null){ //2.判断员工基础信息表中是否存在该信息,如果存在,则不添加基础信息,否则则添加员工基础信心
            EmployeeInfo addemp = new EmployeeInfo();
            empId= UUID.randomUUID().toString();
            addemp.setId(empId);
            addemp.setIdCard(idCard);
            addemp.setPhoneNum(phoneNum);
            addemp.setEmployeeName(employeeName);
            addemp.setSex(sex);
            employeeService.addBaseEmployeeInfo(addemp);
        }else {
            empId = emp.getId();
        }
        //新增员工关系表信息
        String empRelationId =  UUID.randomUUID().toString();
        empRelation.setIsCoach(isCoach);
        if(isCoach.equals("2")){//教练
            empRelation.setJobTitle(jobTitle);
        }
        empRelation.setId(empRelationId);
        empRelation.setEmpId(empId);
        empRelation.setSelfIntroduction(selfIntroduction);
        empRelation.setEmpType(CommonEnum.Employee.empType.informal.getValue());
        empRelation.setBusinessId(businessId);
        empRelation.setDepId(depId);
        empRelation.setStoreId(storeId);
        empRelation.setPositionId(positionId);
        empRelation.setEmpRela(empRela);
        empRelation.setCreator(creator);
        empRelation.setEntryDate(entryDate);
        empRelation.setIsCoach(isCoach);
        empRelation.setRoleId(roleId);
        employeeService.addEmpBusinessRelation(empRelation,phoneNum,idCard);
        //生成入职单
        EntryBill entryBill = new EntryBill();
        entryBill.setBusinessId(businessId);
        entryBill.setStoreId(storeId);
        entryBill.setBillType(CommonEnum.Billinfo.billType.entryBill.getValue());
        entryBill.setEmployeeId(empRelationId);
        entryBill.setStartTime(entryDate);
        entryBill.setDepId(depId);
        entryBill.setPositionId(positionId);
        entryBill.setApprover(creator);
        entryBill.setApproveStatus("2");
        adminManagementService.addEntryBill(entryBill);
        return "";
    }

    /**
     * 员工请求签到
     * @param id 员工id
     * @param signType 签到类型:1.签到,2:签退
     * @param storeId 门店id
     * @param  signAddr 签到地址
     * @param lng 经度
     * @param  lat 纬度
     */
    @RequestMapping(value = "/sign/business_user",method = RequestMethod.POST)
    @ResponseBody
    public String requestSign(@RequestParam(value = "id") final String id,
                              @RequestParam(value = "signType") final int signType,
                              @RequestParam(value = "storeId") final String storeId,
                              @RequestParam(value = "signAddr") final String signAddr,
                              @RequestParam(value = "lng") final String lng,
                              @RequestParam(value = "lat") final String lat) {
        if ("".equals(signAddr)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue());
        }
        try {
            employeeService.requestSign(id,storeId ,signType ,signAddr,lng,lat);
        }catch (ParseException p){
            p.printStackTrace();
        }
        return "";
    }

    /**
     * 选择wifi签到
     * @param id   员工id
     * @param wifiId   wifi id
     * @param signType 签到类型:1签到;2签退
     * @return
     */
    @RequestMapping(value = "sign/wifiSign",method = RequestMethod.POST)
    @ResponseBody
    public String wifiSign( @RequestParam(value = "id") final String id,
                            @RequestParam(value = "storeId") final String storeId,
                            @RequestParam(value = "wifiId") final String wifiId,
                            @RequestParam(value = "signType") final Integer signType) {
        try {
            employeeService.wifiSign(id,storeId, wifiId, signType);
        }catch (ParseException p){
            p.printStackTrace();
        }
        return "";
    }
    /**
     * 根据员工名称模糊查询员工关系表信息
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/listEmpBusinessRelationByName",method = RequestMethod.POST)
    @ResponseBody
    public Map listEmpBusinessRelationByName(@RequestParam(value = "storeId") final String storeId,
                                             @RequestParam(value = "employeeName",required = false) final String employeeName){
        Map<String,Object>map = new HashMap<>();
        ResponseEmployee employee = new ResponseEmployee();
        employee.setStoreId(storeId);
        employee.setEmployeeName(employeeName);
        List<ResponseEmployee> remps = employeeService.listEmpBusinessRelationByName(employee);
        map.put("remps", remps);
        return map;
    }

    /**
     * 获取门店所有员工信息
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/queryEmpListByStoreId",method = RequestMethod.POST)
    @ResponseBody
    public Map queryEmpListByStoreId(@RequestParam(value = "storeId") final String storeId){
        Map<String,Object>map = new HashMap<>();
        List<ResponseEmployee>rempList = employeeService.listEmpBusinessRelationByStoreId(storeId);
        map.put("rempList", rempList);
        return map;
    }

    /**
     * 获取门店教练列表
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/queryCoachListByStoreId",method = RequestMethod.POST)
    @ResponseBody
    public Map queryCoachListByStoreId(@RequestParam(value = "storeId") final String storeId,
                                       @RequestParam(value = "page") final Integer page,
                                       @RequestParam(value = "pageSize",required = false) final Integer pageSize){
        Map<String,Object>map = new HashMap<>();
        List<EmployeeEvaluateInfo>evalList = employeeService.queryCoachListByStoreId(storeId, page, pageSize);
        map.put("evalList",evalList);
        return map;
    }

    /**
     * 获取门店所有部门所有员工
     * @param storeId
     * @param businessId
     * @return
     */
    @RequestMapping(value = "/queryAllEmp",method = RequestMethod.POST)
    @ResponseBody
    public Map queryAllEmp(@RequestParam(value = "storeId") final String storeId,
                           @RequestParam(value = "businessId") final String businessId,
                           @RequestParam(value = "employeeName",required = false) final String employeeName){
        Map<String,Object>map = new HashMap<>();
        List<Department> departmentList = employeeService.queryAllEmp(businessId, storeId,employeeName);
        map.put("departmentList",departmentList);
        return map;
    }

    /**
     * 编辑员工信息
     * @param employeeName 姓名
     * @param phoneNum 电话
     * @param birthday
     * @param depId 部门id
     * @param positionId 职位id
     * @param empRela 就职状态:1:全职;2:兼职
     * @param entryDate 入职日期
     * @param sex 性别:1男;2:女
     * @param isCoach 是否是教练:1:否;2:是
     * @param jobTitle 教练职称;1:初级教练;2:中级教练;3:高级教练;
     * @param creator
     * @param selfIntroduction 自我介绍
     * @param roleId 角色id
     * @param empId 员工基础id
     * @param id 员工关系id
     * @return
     */
    @RequestMapping(value = "/updateEmployeeInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployeeInfo(@RequestParam(value = "employeeName" ,required = false) final String employeeName,
                                     @RequestParam(value = "phoneNum",required = false) final String phoneNum,
                                     @RequestParam(value = "birthday",required = false) final String birthday,
                                     @RequestParam(value = "depId",required = false) final String depId,
                                     @RequestParam(value = "positionId",required = false) final String positionId,
                                     @RequestParam(value = "empRela",required = false) final String empRela,
                                     @RequestParam(value = "entryDate",required = false) final String entryDate,
                                     @RequestParam(value = "sex",required = false) final String sex,
                                     @RequestParam(value = "isCoach",required = false) final String isCoach,
                                     @RequestParam(value = "jobTitle",required = false) final String jobTitle,
                                     @RequestParam(value = "creator",required = false) final String creator,
                                     @RequestParam(value = "selfIntroduction" ,required = false) final String selfIntroduction,
                                     @RequestParam(value = "roleId",required = false) final String roleId,
                                     @RequestParam(value = "empId",required = false) final String empId,
                                     @RequestParam(value = "id") final String id){
        ResponseEmployee remp =  employeeService.getEmpBusinessRelationById(id);
        if (remp ==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        //编辑员工基础信息
        EmployeeInfo emp = new EmployeeInfo();
        emp.setEmployeeName(employeeName);
        emp.setPhoneNum(phoneNum);
        emp.setSex(sex);
        emp.setBirthday(birthday);
        emp.setId(empId);
        employeeService.updateEmployeInfo(emp);
        //编辑员工关系id
        EmpBusinessRelation empr = new EmpBusinessRelation();
        empr.setDepId(depId);
        empr.setPositionId(positionId);
        empr.setEmpRela(empRela);
        empr.setIsCoach(isCoach);
        empr.setJobTitle(jobTitle);
        empr.setId(id);
        empr.setEntryDate(entryDate);
        empr.setCreator(creator);
        empr.setRoleId(roleId);
        empr.setSelfIntroduction(selfIntroduction);
        employeeService.updateEmpBusinessRelation(empr);
        return "";
    }

    /**
     * 员工图片上传
     * @param request
     * @param flag 1上传的是头像;2上传的是教学照片
     * @param empRelationId
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/empUploadFile",method = RequestMethod.POST)
    @ResponseBody
    public  Map empUploadFile(HttpServletRequest request,
                                              @RequestParam(value = "flag") final Integer flag,
                                              @RequestParam(value = "empRelationId") final String empRelationId,
                                              @RequestParam(value = "employeeId") final String employeeId){
        //文件上传相对目录
        String relaPath ="";
        String empHead = "";
        String empPic="";
        if (flag==1){//头像
             relaPath = Configuration.getUploadEmployeePicturePath(employeeId);
        }else if(flag ==2){//教学照片
            relaPath = Configuration.getUploadEmployeePicturePath(empRelationId);
        }
        Map<String,List> map = new HashMap<>();
        List<String>list = new ArrayList<>();
        try {
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                }
                if (flag==1){//头像
                    empHead = stringBuffer.toString().substring(1);
                    list.add(Configuration.getStaticShowPath()+empHead);
                }else if(flag ==2) {//教学照片
                    empPic = stringBuffer.toString().substring(1);
                    String[] picStr =  empPic.split(";");
                    if (picStr !=null && picStr.length>0){
                        for (String str: picStr){
                            list.add(Configuration.getStaticShowPath()+str);
                        }
                    }
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("EmployeeController-addEmployeeInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        if (empHead!=null &&!"".equals(empHead)){
            EmployeeInfo emp = new EmployeeInfo();
            emp.setEmpHead(empHead);
            emp.setId(employeeId);
            employeeService.updateEmployeInfo(emp);
        }
        if (empPic!=null &&!"".equals(empPic)){
            EmpBusinessRelation empr = new EmpBusinessRelation();
            empr.setEmpPicture(empPic);
            empr.setId(empRelationId);
            employeeService.updateEmpBusinessRelation(empr);
        }
        map.put("list",list);
        return map;
    }

    /**
     * 获取所有员工
     * @param page
     * @return
     */
    @RequestMapping(value = "/queryEmpAll",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryEmpAll( @RequestParam(value = "page") final Integer page){
        Map<String,Object>map = new HashMap<>();
        BatchDataPage batchDataPage =  employeeService.queryEmpAll(page);
        map.put("batchDataPage", batchDataPage);
        return map;
    }

    /**
     *查询门店中所有教练周平均评价
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/listEmpMemberevaluate",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listEmpMemberevaluate( @RequestParam(value = "storeId") final String storeId,
                                                     @RequestParam(value = "page") final Integer page){
        Map<String,Object>map = new HashMap<>();
        List<EmployeeEvaluateInfo> list = employeeService.listEmpMemberevaluate(storeId, page);
        map.put("list",list);
        return map;
    }

    /**
     * 获取该教练的评价详情
     * @param id
     * @param courseId
     * @param page
     * @return
     */
    @RequestMapping(value = "/getEmpMemberevaluateByEmpId",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getEmpMemberevaluateById( @RequestParam(value = "id") final String id,
                                                        @RequestParam(value = "courseId") final String courseId,
                                                        @RequestParam(value = "page") final Integer page){
        Map<String,Object>map = new HashMap<>();
        EmployeeEvaluateInfo eval = employeeService.getEmpMemberevaluateById(id, courseId, page);
        map.put("eval",eval);
        return map;
    }

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserRole", method = RequestMethod.POST)
    public Map<String,Object> listUserRole(@RequestParam("platform") String platform,
                                           @RequestParam("businessId") String businessId){
        Map<String,Object> map = new HashMap<>();
        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList = employeeService.listUserRole(platform,businessId);
        map.put("userRoleList",userRoleList);
        return map;
    }


    /**
     * 根据id查询教练详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCoachInfoById", method = RequestMethod.POST)
    public  Map<String,Object> getCoachInfoById(  @RequestParam("id") String  id){
       CoachInfo coachInfo =  employeeDao.getCoachInfoById(id);
        Map<String,Object>map = new HashMap<>();
        if (coachInfo ==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("coachInfo",coachInfo);
        return map;
    }
}
