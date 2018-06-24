package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.dao.IBusinessDao;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.dao.IEmployeeSignSetDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import com.dod.sport.redis.RedisData;
import com.dod.sport.service.IEmployeeService;
import com.dod.sport.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/6.
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    IEmployeeDao employeeDao;
    @Autowired
    IEmployeeSignSetDao employeeSignSetDao;
    @Autowired
    IBusinessDao businessDao;

    /**
     * 获取注册验证码
     * @param phoneNum 用户手机号
     * @return string 验证码字符串
     */
    public String getIdentifyingCode(String phoneNum){
       // String keyStr = RedisCommon.createIdentifyingCode(phoneNum);
      //  System.out.println("获取注册验证码:"+keyStr);
        return "";
    }

    /**
     * 获取员工商家列表
     * @param phoneNum 员工电话
     * @return
     */
    @Override
    public  List<BusinessInfo> getBusinessListByPhoneNum(String phoneNum) {
        List<BusinessInfo> businessInfoList= businessDao.getBusinessListByPhoneNum(phoneNum);
        if (businessInfoList !=null && businessInfoList.size()>0){
            for(BaseBusinessInfo baseBusinessInfo :businessInfoList){
                List<StoreInfo>list = businessDao.listStoreByBusinessId(baseBusinessInfo.getId());
                baseBusinessInfo.setStoreInfoList(list);
            }
        }
        return businessInfoList;
    }

    /**
     * 根据名称模糊查询员工关系信息
     * @param employee
     * @return
     */
    @Override
    public  List<ResponseEmployee> listEmpBusinessRelationByName(ResponseEmployee employee) {
        List<ResponseEmployee> remps = employeeDao.listEmpBusinessRelationByName(employee);
        if (remps !=null && remps.size()>0){
            for (ResponseEmployee remp :remps){
                if ( remp.getEmpPicture()!=null&& !"".equals(remp.getEmpPicture())){
                    String[] picStr =  remp.getEmpPicture().split(";");
                    if (picStr !=null && picStr.length>0){
                        List <String> picPathList = new ArrayList<>();
                        for (String str: picStr){
                            picPathList.add(Configuration.getStaticShowPath()+str);
                        }
                        remp.setPicPathList(picPathList);
                    }
                }
                UserRole userRole = RedisData.getRedisRoleInfo(remp.getRoleId());
                remp.setUserRole(userRole);
                if (remp.getEntryDate()!=null &&! "".equals(remp.getEntryDate())){
                    String years = BusiUtils.getEmpWorkAge(remp.getEntryDate());
                    remp.setWorkDuration(years);
                }
            }
        }

        return remps;
    }

    /**
     * 登陆是获取员工基础信息
     * @param phoneNum
     * @return
     */
    @Override
    public EmployeeInfo LoginResult(String phoneNum) {
        EmployeeInfo emp = employeeDao.getEmployeeByPhoneNum(phoneNum);
        if (emp!=null){
            if (emp.getEmpHead() !=null &&!"".equals(emp.getEmpHead())){
                emp.setEmpHead(Configuration.getStaticShowPath()+emp.getEmpHead());
            }
        }
        return emp;
    }

    /**
     * 获取员工关系列表
     * @param phoneNum
     * @return
     */
    @Override
    public ResponseEmployee getEmpBusinessInfoByPhoneNum(String phoneNum) {
        List<ResponseEmployee> list = employeeDao.getEmpListByPhoneNumAndEmpStatus(phoneNum);
        if (list!=null&&list.size()>0){
            ResponseEmployee remp = list.get(0);
            if (remp !=null && remp.getEntryDate()!=null && !"".equals(remp.getEntryDate()) ) {
                String  years = BusiUtils.getEmpWorkAge(remp.getEntryDate());
                remp.setWorkDuration(years);
            }
            if (remp.getEmpHead() !=null &&!"".equals(remp.getEmpHead())){
                remp.setEmpHead(Configuration.getStaticShowPath()+remp.getEmpHead());
            }
            if ( remp.getEmpPicture()!=null&& !"".equals(remp.getEmpPicture())){
                String[] picStr =  remp.getEmpPicture().split(";");
                if (picStr !=null && picStr.length>0){
                    List <String> picPathList = new ArrayList<>();
                    for (String str: picStr){
                        picPathList.add(Configuration.getStaticShowPath()+str);
                    }
                    remp.setPicPathList(picPathList);
                }
            }
            return remp;
        }
        return null;
    }

    /**
     * 员工注册
     * @param
     * @return
     */
    @Override
    public void registerEmployee(EmployeeInfo em) {
        em.setRegisterType(CommonEnum.Employee.registerType.registered.getValue());
        employeeDao.registerEmployee(em);
    }
    /**
     * 根据员工电话和门店id获取员工关系表信息
     * @param em
     * @return
     */
    @Override
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em) {
        ResponseEmployee remp=employeeDao.queryEmployeeInfoByStoreIdAndPhoneNum(em);
        if (remp==null){
            return null;
        }
        if (remp!=null && remp.getEmpHead() !=null && !"".equals(remp.getEmpHead())){
            remp.setEmpHead(Configuration.getStaticShowPath()+remp.getEmpHead());
        }
        if ( remp.getEmpPicture()!=null&& !"".equals(remp.getEmpPicture())){
            String[] picStr =  remp.getEmpPicture().split(";");
            if (picStr !=null && picStr.length>0){
                List <String> picPathList = new ArrayList<>();
                for (String str: picStr){
                    picPathList.add(Configuration.getStaticShowPath()+str);
                }
                remp.setPicPathList(picPathList);
            }
        }
        return remp;
    }
    /**
     * 员工密码找回
     * @param password
     * @param phoneNum
     */
    @Override
    public void getpassword(String password,String phoneNum) {
        EmployeeInfo em = new EmployeeInfo();
        em.setPassword(password);
        em.setPhoneNum(phoneNum);
        employeeDao.updateEmployeePassword(em);
    }

    /**
     * 新增员工的基础信息
     * @param employeeInfo
     */
    @Override
    public void addBaseEmployeeInfo(EmployeeInfo employeeInfo) {
        String empId = RedisCommon.getMaxBaseEmployeeRegisterId();
        if("".equals(empId)||empId==null){
            String maxEmployeeId = employeeDao.queryRegisterIdMax();
            empId = RedisCommon.setAndReturnMaxBaseEmployeeRegisterId(maxEmployeeId);
        }
        employeeInfo.setBaseEmployeeSerialId(empId);
        if (IdCardUtil.validate(employeeInfo.getIdCard())){//判断身份证得有效性
            String idCard = employeeInfo.getIdCard();
            employeeInfo.setBirthday(IdCardUtil.getYearByIdCard(idCard)+"-"+IdCardUtil.getMonthByIdCard(idCard)+"-"+IdCardUtil.getDateByIdCard(idCard));
        }
        employeeDao.addBaseEmployeeInfo(employeeInfo);
    }

    /**
     * 添加员工商家关系信息
     * @param em
     */
    @Override
    @Transactional
    public void addEmpBusinessRelation(EmpBusinessRelation em,String phoneNum,String idCard) {

        ResponseEmployee queryremp =new ResponseEmployee();
        queryremp.setPhoneNum(phoneNum);
        queryremp.setStoreId(em.getStoreId());
        ResponseEmployee remp = employeeDao.getEmpBusinessRelationByIdCard(phoneNum,idCard,em.getStoreId());
        if(remp!=null){ //1.判断员工关系表中对应的商家中是否存在该员工信息,如果存在则提示该员工信息已存在
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeeexists.getValue());
        }
        List<ResponseEmployee> list = employeeDao.getEmpListByPhoneNumAndEmpStatus(phoneNum);
        if (list!=null && list.size()>0){
            for (ResponseEmployee responseEmployee :list){
               if ((!responseEmployee.getEmpType().equals(3))&& responseEmployee.getEmpRela().equals("1") && em.getEmpRela().equals("1")){//员工不能在不同的门店中都是全职
                   throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeeqzexists.getValue());
               }
            }
        }
        String empId = RedisCommon.getMaxEmployeeId(em.getBusinessId());
        if(empId == ""||empId==null){
            String maxEmployeeId = employeeDao.queryEmployeeIdMax(em.getBusinessId());
            empId = RedisCommon.setAndReturnMaxEmployeeId(em.getBusinessId(), maxEmployeeId);
        }
        em.setEmployeeSerialId(empId);
        employeeDao.addEmpBusinessRelation(em);

    }

    /**
     *员工签到
     * @param id 员工id
     * @param signType 签到类型:1签到;2签退
     * @param signAddr 签到地址
     * @return
     */
    @Override
    @Transactional
    public void requestSign(String id,String storeId ,int signType, String signAddr,String lng,String lat) throws ParseException {
        SignScope signScope = employeeSignSetDao.querySignScopeByStoreId(storeId);
        if (signScope==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        ResponseEmployee remp = employeeDao.getEmpBusinessRelationById(id);
        double distance =  GeoUtils.getDistanceOfMeter(Double.parseDouble(lng), Double.parseDouble(lat), Double.parseDouble(signScope.getLng()),Double.parseDouble(signScope.getLat()));
        List<SignTime> signTimeList = employeeSignSetDao.querySignTimeByStoreId(storeId);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = sd.format(new Date());
        String newdateStr = sdf.format(new Date());
        Date newdate = sdf.parse(newdateStr); //当前签到时间
        EmployeeSign emeployeeSign = new EmployeeSign();
        if (distance > Integer.parseInt(signScope.getRadius())) {//判断当前打卡位置是否在打卡范围
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notinsignscope.getValue());
        }
        emeployeeSign.setId(UUID.randomUUID().toString());
        emeployeeSign.setStoreId(remp.getStoreId());
        emeployeeSign.setSignType(signType);
        emeployeeSign.setSignTime(DateUtil.getDateTime());
        emeployeeSign.setDepId(remp.getDepId());
        emeployeeSign.setSignedAddr(signAddr);
        emeployeeSign.setEmployeeId(id);
        if (signType == 1) {//签到
            EmployeeSign esign = employeeSignSetDao.getempSignByTime(id, newDate, signType);
            if (esign!=null){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeesigned.getValue());
            }
            for (SignTime signTime : signTimeList) {
                String firstTime = signTime.getFirstTime();
                Date firstdate = sdf.parse(firstTime);//签到时间
                //获取打卡时间范围
                Date beforefirst = BusiUtils.getDateTobefore(firstdate, Integer.parseInt(signTime.getTimeScope())); //最早签到时间
                Date afterefirst = BusiUtils.getDateToafter(firstdate, Integer.parseInt(signTime.getTimeScope()));  //最晚签到时间
                if (DateUtil.isAfter(newdate,beforefirst) && DateUtil.isBefore(newdate,afterefirst)) { //签到时间在打卡时间范围内
                    if (DateUtil.isAfter(newdate,firstdate )) {//迟到
                        emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_last.getIntegerVal());
                    }else{//正常
                        emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_ontime.getIntegerVal());
                    }
                    employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
                    return;
                }
            }
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notimetosign.getValue());
        } else {//签退
            EmployeeSign esign = employeeSignSetDao.getempSignByTime(id, newDate, signType);
            if (esign!=null){
                throw new BusiException( CommonEnum.ReturnCode.BusinessCode.busi_err_employeesignedquit.getValue());
            }
            for (SignTime signTime : signTimeList) {
                String lastTime = signTime.getLastTime();
                Date lastdate = sdf.parse(lastTime);  //签退时间
                Date beforefirst = BusiUtils.getDateTobefore(lastdate, Integer.parseInt(signTime.getTimeScope())); //最早签退到时间
                Date afterefirst = BusiUtils.getDateToafter(lastdate, Integer.parseInt(signTime.getTimeScope()));  //最晚签退时间
                if (DateUtil.isAfter(newdate ,beforefirst) && DateUtil.isBefore(newdate,afterefirst)) { //签到时间在打卡时间范围内
                    if (DateUtil.isAfter(newdate,lastdate)) {//正常
                        emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_last.getIntegerVal());
                    }else{//早退
                        emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_leave.getIntegerVal());
                    }
                    employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
                    return;
                }
            }
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notimetosign.getValue());
        }
    }

    /**
     * 选择WiFi签到
     * @param empId
     * @param storeId
     * @param wifiId
     * @param signType
     * @throws ParseException
     */
    @Override
    @Transactional
    public void wifiSign(String empId ,String storeId,String wifiId,Integer signType) throws ParseException {
        if(wifiId.length()<17){
            wifiId = "0"+wifiId;
        }
        StoreWifi wifi =  employeeSignSetDao.querywifiInfo(storeId,wifiId);
        ResponseEmployee remp = employeeDao.getEmpBusinessRelationById(empId);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = sd.format(new Date());
        String newdateStr = sdf.format(new Date());
        Date newdate = sdf.parse(newdateStr); //当前签到时间
        if (wifi!=null){
            List<SignTime> signTimeList = employeeSignSetDao.querySignTimeByStoreId(wifi.getStoreId());
            EmployeeSign emeployeeSign = new EmployeeSign();
            emeployeeSign.setEmployeeId(empId);
            emeployeeSign.setId(UUID.randomUUID().toString());
            emeployeeSign.setStoreId(wifi.getStoreId());
            emeployeeSign.setSignType(signType);
            emeployeeSign.setSignTime(DateUtil.getDateTime());
            emeployeeSign.setDepId(remp.getDepId());
            if (signType==1){
                EmployeeSign esign = employeeSignSetDao.getempSignByTime(empId, newDate, signType);
                if (esign!=null){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeesigned.getValue());
                }
                for (SignTime signTime : signTimeList) {
                    String firstTime = signTime.getFirstTime();
                   // Date firstdate = sdf.parse(firstTime.substring(11));//签到时间
                    Date firstdate = sdf.parse(firstTime);
                    //获取打卡时间范围
                    Date beforefirst = BusiUtils.getDateTobefore(firstdate, Integer.parseInt(signTime.getTimeScope())); //最早签到时间
                    Date afterefirst = BusiUtils.getDateToafter(firstdate, Integer.parseInt(signTime.getTimeScope()));  //最晚签到时间
                    if (DateUtil.isAfter(newdate,beforefirst) && DateUtil.isBefore(newdate,afterefirst )) { //签到时间在打卡时间范围内
                        if (DateUtil.isAfter(newdate,firstdate)) {//迟到
                            emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_last.getIntegerVal());
                        }else{//正常
                            emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_ontime.getIntegerVal());
                        }
                        employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
                        return;
                    }
                }
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notimetosign.getValue());
            } else {//签退
                EmployeeSign esign = employeeSignSetDao.getempSignByTime(empId, newDate, signType);
                if (esign!=null){
                    throw new BusiException( CommonEnum.ReturnCode.BusinessCode.busi_err_employeesignedquit.getValue());
                }
                for (SignTime signTime : signTimeList) {
                    String lastTime = signTime.getLastTime();
                    Date lastdate = sdf.parse(lastTime);  //签退时间
                    Date beforefirst = BusiUtils.getDateTobefore(lastdate, Integer.parseInt(signTime.getTimeScope())); //最早签退到时间
                    Date afterefirst = BusiUtils.getDateToafter(lastdate, Integer.parseInt(signTime.getTimeScope()));  //最晚签退时间
                    if (DateUtil.isAfter(newdate,beforefirst ) && DateUtil.isBefore(newdate,afterefirst)) { //签到时间在打卡时间范围内
                        if (DateUtil.isAfter(newdate,lastdate)) {//正常
                            emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_last.getIntegerVal());
                        }else{//早退
                            emeployeeSign.setSignStatus(CommonEnum.Administeration.Sign_status.work_leave.getIntegerVal());
                        }
                        employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
                        return;
                    }
                }
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notimetosign.getValue());
            }
        }
        throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_storewifinotexists.getValue());
    }

    /**
     * 根据员工关系id获取员工信息
     * @param id
     * @return
     */
    @Override
    public ResponseEmployee getEmpBusinessRelationById(String id) {
        ResponseEmployee remp =employeeDao.getEmpBusinessRelationById(id);
        if (remp !=null){
            if (remp.getEmpHead() !=null && !"".equals(remp.getEmpHead()) ){
                String heanPath =  Configuration.getStaticShowPath()+remp.getEmpHead();
                remp.setEmpHead(heanPath);
            }
           if (remp.getEmpPicture() !=null && !"".equals(remp.getEmpPicture())){
               String[] picStr =  remp.getEmpPicture().split(";");
               if (picStr !=null && picStr.length>0){
                   List <String> picPathList = new ArrayList<>();
                   for (String str: picStr){
                       picPathList.add(Configuration.getStaticShowPath()+str);
                   }
                   remp.setPicPathList(picPathList);
               }
           }
        }
        return remp;
    }

    /**
     * 获取门店所有员工信息
     * @param storeId
     * @return
     */
    @Override
    public List<ResponseEmployee> listEmpBusinessRelationByStoreId(String storeId) {
        List<ResponseEmployee> rempList = employeeDao.listEmpBusinessRelationByStoreId(storeId);
        if (rempList !=null||rempList.size()>0){
            for (ResponseEmployee responseEmployee :rempList){
                String  years = BusiUtils.getEmpWorkAge(responseEmployee.getEntryDate());
                responseEmployee.setWorkDuration(years);
                if (responseEmployee.getEmpHead() !=null && !"".equals(responseEmployee.getEmpHead())){
                    String heanPath = Configuration.getStaticShowPath()+responseEmployee.getEmpHead();
                    responseEmployee.setEmpHead(heanPath);
                }
                if (responseEmployee.getEmpPicture() !=null && !"".equals(responseEmployee.getEmpPicture())){
                    String[] picStr =  responseEmployee.getEmpPicture().split(";");
                    if (picStr !=null && picStr.length>0){
                        List <String> picPathList = new ArrayList<>();
                        for (String str: picStr){
                            picPathList.add(Configuration.getStaticShowPath()+str);
                        }
                        responseEmployee.setPicPathList(picPathList);
                    }
                }
            }
        }
        return rempList;
    }

    /**
     * 获取门店教练列表
     * @param storeId
     * @return
     */
    @Override
    public List<EmployeeEvaluateInfo> queryCoachListByStoreId(String storeId,Integer page, Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        if (pageSize !=null && pageSize !=0){
            batchDataPage.setPagesize(pageSize);
        }
        List<EmployeeEvaluateInfo> list = employeeDao.queryCoachListByStoreId(storeId,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        if (list !=null && list.size()>0 ){
            for (ResponseEmployee remp :list){
                if (remp.getEmpHead() !=null && !"".equals(remp.getEmpHead())){
                    String heanPath = Configuration.getStaticShowPath()+remp.getEmpHead();
                    remp.setEmpHead(heanPath);
                }
                if (remp.getEmpPicture() !=null && !"".equals(remp.getEmpPicture())){
                    String[] picStr =  remp.getEmpPicture().split(";");
                    if (picStr !=null && picStr.length>0){
                        List <String> picPathList = new ArrayList<>();
                        for (String str: picStr){
                            picPathList.add(Configuration.getStaticShowPath()+str);
                        }
                        remp.setPicPathList(picPathList);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取门店中教练周平均评价
     * @param storeId
     * @return
     */
    @Override
    public List<EmployeeEvaluateInfo> listEmpMemberevaluate(String storeId,Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
       //获取教练列表
        List<EmployeeEvaluateInfo> list = employeeDao.queryCoachListByStoreId(storeId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        if (list!=null &&list.size()>0){
            MemberEvaluate memberEvaluate =null;
            for (EmployeeEvaluateInfo eval:list){//获取每个教练的周平均评价
                 memberEvaluate = employeeDao.queryAvgMemberevaluate(eval.getId());
                eval.setAvgEnvironmentlevel(memberEvaluate.getAvgEnvironmentlevel());
                eval.setAvgServiceLevel(memberEvaluate.getAvgServiceLevel());
                eval.setAvgTeachLevel(memberEvaluate.getAvgTeachLevel());
                if (eval.getEmpHead() !=null && !"".equals(eval.getEmpHead())){
                    String heanPath = Configuration.getStaticShowPath()+eval.getEmpHead();
                    eval.setEmpHead(heanPath);
                }
                if (eval.getEmpPicture() !=null && !"".equals(eval.getEmpPicture())){
                    String[] picStr =  eval.getEmpPicture().split(";");
                    if (picStr !=null && picStr.length>0){
                        List <String> picPathList = new ArrayList<>();
                        for (String str: picStr){
                            picPathList.add(Configuration.getStaticShowPath()+str);
                        }
                        eval.setPicPathList(picPathList);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取该教练的评价详情
     * @param id
     * @return
     */
    @Override
    public EmployeeEvaluateInfo getEmpMemberevaluateById(String id,String courseId,Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        EmployeeEvaluateInfo eval = employeeDao.getEmpMemberevaluateById( id);
        if (courseId!=null&&!"".equals(courseId)){
            List<MemberEvaluate> list =  employeeDao.listQueryMemberevaluate(id, courseId, batchDataPage.getOffset(),
                    batchDataPage.getPagesize());
            if (list!=null &&list.size()>0&& eval!=null){
                eval.setMemberEvaluateList(list);
            }
        }
        return eval;
    }

    /**
     * 获取门店所有部门所有员工
     * @param businessId
     * @param storeId
     * @return
     */
    @Override
    public List<Department> queryAllEmp(String businessId, String storeId, String employeeName) {
        List<Department> departmentList = businessDao.getBusiDepartmentListByBusinessId(businessId);
        ResponseEmployee remp =new ResponseEmployee();
        if (employeeName!=null && !"".equals(employeeName)){
            remp.setEmployeeName(employeeName);
        }
        for (Department department :departmentList){
            remp.setDepId(department.getId());
            remp.setStoreId(storeId);
            List<ResponseEmployee> rempList = employeeDao.queryEmpListByDepId(remp);
            department.setList(rempList);
        }
        return departmentList;
    }

    /**
     * 编辑员工
     * @param emp
     */
    @Override
    public void updateEmployeInfo(EmployeeInfo emp) {
        employeeDao.updateEmployeInfo(emp);
    }

    /**
     * 编辑员工关系信息
     * @param empr
     */
    @Override
    public void updateEmpBusinessRelation(EmpBusinessRelation empr) {
        employeeDao.updateEmpBusinessRelation(empr);
    }

    /**
     * 查询所有员工
     * @param page 当前页
     * @return
     */
    @Override
    public BatchDataPage queryEmpAll(Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<ResponseEmployee>list = employeeDao.queryEmpAll(batchDataPage.getOffset(),30);
        batchDataPage.setRows(list);
        long tatol = employeeDao.queryEmpCounts();
        batchDataPage.setTotalRecords(tatol);
        return batchDataPage;
    }

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @return
     */
    @Override
    public List<UserRole> listUserRole(String platform, String businessId){
        List<UserRole> userRoleList = employeeDao.listUserRole(platform,businessId,0,0);
        return userRoleList;
    }


}
