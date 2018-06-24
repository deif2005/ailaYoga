package com.dod.sport.service;

import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface IEmployeeService {
    /**
     * 获取注册验证码
     * @param phoneNum 用户手机号
     * @return string 验证码字符串
     */
    public String getIdentifyingCode(String phoneNum);

    /**
     * 获取员工所在的商家分店列表
     * @param phoneNum
     * @return
     * getBusinessListByPhoneNum
     */
    public List<BusinessInfo> getBusinessListByPhoneNum(String phoneNum);

    /**
     * 根据名称模糊查询员工关系信息
     * @param employee
     * @return
     */
    public List<ResponseEmployee> listEmpBusinessRelationByName(ResponseEmployee employee);

    /**
     * 根据员工电话获取员工信息列表
     * @param phoneNum
     * @return
     */
    public EmployeeInfo LoginResult(String phoneNum);

    /**
     * 根据电话号码获取未注册员工所有信息
     * @param phoneNum
     * @return
     */
    public ResponseEmployee getEmpBusinessInfoByPhoneNum(String phoneNum);
    /**
     * 员工注册
     * @param em
     */
    public void registerEmployee(EmployeeInfo em);

    /**
     *根据商家编号与员工的电话号码查询员工信息
     * @param em
     * @return Employee
     */
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em);

    /**
     * 员工密码找回
     * @param password
     * @param phoneNum
     */
    public void getpassword(String password,String phoneNum);
    /**
     * 新增员工基础信息
     * @param employeeInfo
     */
    public void addBaseEmployeeInfo(EmployeeInfo employeeInfo);

    /**
     * 新增员工关系表数据
     * @param em
     */
    public void addEmpBusinessRelation(EmpBusinessRelation em,String phoneNum,String idCard);

    /**
     * 员工签到
     * @param id 员工id
     * @param storeId 门店id
     * @param signType 签到类型:1签到;2签退
     * @param signAddr 签到地址
     */
    public void requestSign(String id,String storeId, int signType,String signAddr,String lng,String lat) throws ParseException;

    /**
     * 选择WiFi签到
     * @param empId
     * @param stroeId
     * @param wifiId
     * @param signType
     * @throws ParseException
     */
    public  void wifiSign(String empId,String stroeId,String wifiId,Integer signType) throws ParseException;
    /**
     * 根据员工关系id获取员工信息
     * @param id
     * @return
     */
    public ResponseEmployee getEmpBusinessRelationById(String id);

    /**
     * 获取门店中所有员工的信息
     * @param storeId
     * @return
     */
    public List<ResponseEmployee> listEmpBusinessRelationByStoreId(String storeId);

    /**
     * 获取门店教练列表
     * @param storeId
     * @return
     */
    public List<EmployeeEvaluateInfo>queryCoachListByStoreId(String storeId,Integer page, Integer pageSize);

    /**
     * 获取该门店下所有教练的周评价
     * @param storeId
     * @return
     */
    public List<EmployeeEvaluateInfo> listEmpMemberevaluate(String storeId,Integer page);

    /**
     * 获取该教练的评价详情
     * @param id
     * @return
     */
    public EmployeeEvaluateInfo getEmpMemberevaluateById(String id,String courseId,Integer page);
    /**
     * 获取门店所有部门所有员工
     * @param businessId
     * @param storeId
     * @return
     */
    public List<Department>queryAllEmp(String businessId,String storeId, String employeeName);

    /**
     * 编辑员工
     * @param emp
     */
    public void updateEmployeInfo(EmployeeInfo emp);

    /**
     * 编辑员工与商家关系信息
     * @param empr
     */
    public void updateEmpBusinessRelation(EmpBusinessRelation empr);

    public BatchDataPage queryEmpAll( Integer page);

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @return
     */
    public List<UserRole> listUserRole(String platform, String businessId);


}
