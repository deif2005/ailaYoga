package com.dod.sport.dao;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Repository
public interface IEmployeeDao {

    /**
     * 根据员工关系id获取员工详情
     * @param id
     * @return
     */
    public ResponseEmployee getEmpBusinessRelationById(String id);
    /**
     * 获取员工基础信息
     * @param phoneNum
     * @return
     */
    public EmployeeInfo getEmployeeByPhoneNum(String phoneNum);


    /**
     * 根据电话号码获取未注册员工所有信息
     * @param phoneNum
     * @return
     */
    public List<ResponseEmployee> getEmpListByPhoneNumAndEmpStatus(String phoneNum);

    /**
     * 根据名称模糊查询员工信息
     * @param employee
     * @return
     */
    public List<ResponseEmployee> listEmpBusinessRelationByName(ResponseEmployee employee);
    /**
     * 员工注册
     * @param employee
     */
    public void registerEmployee(EmployeeInfo employee);

    /**
     * 获取员工最大的编号
     * @return
     */
    public String queryEmployeeIdMax( String businessId);

    /**
     * 获取员工基础信息最大的注册编号
     * @return
     */
    public String queryRegisterIdMax( );

    /**
     * 根据商家编号与员工的电话号码查询员工信息
     * @param em
     * @return
     */
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em);

    /**
     * 员工登陆查询
     * @param em
     * @return
     */
    public EmployeeInfo doLogin(EmployeeInfo em);

    /**
     * 更换员工密码
     * @param em
     */
    public void updateEmployeePassword(EmployeeInfo em);

    /**
     * 新增员工基础信息
     * @param employeeInfo
     */
    public void addBaseEmployeeInfo(EmployeeInfo employeeInfo);

    /**
     * 新增员工关系表数据
     * @param em
     */
    public void addEmpBusinessRelation(EmpBusinessRelation em);

    /**
     * 根据员工Id获取商家信息
     * @param id
     * @return
     */
    public BusinessInfo queryBusinessInfoByEmployeeId(String id);

    /**
     * 根据商家id获取员工信息
     * @param businessIds
     * @return
     */
    public List<String> getEmployeeIdByBusiId(@Param("businessIds") String[] businessIds);

    /**
     * 根据门店id获取员工信息
     * @param storeIds
     * @return
     */
    public List<String> getEmployeeIdByStoreId(@Param("storeIds") String[] storeIds);

    /**
     * 根据门店id,部门id获取员工信息
     * @param storeIds
     * @param departmentIds
     * @return
     */
    public List<String> getEmployeeIdByStoreId(@Param("storeIds") String[] storeIds,
                                               @Param("departmentIds") String[] departmentIds);


    /**
     * 获取入职员工编号
     * @return
     */
    public List<String> getEmployeeIdByEmpStatus();

    /**
     * 获取门店中所有员工的信息
     * @param storeId
     * @return
     */
    public List<ResponseEmployee>listEmpBusinessRelationByStoreId(String storeId);

    /**
     * 获取门店中所有员工的信息 分页查询
     * @param storeId
     * @return
     */
    public List<ResponseEmployee>getEmpBusinessRelationByStoreId(@Param("storeId")String storeId,
                                                                  @Param("employeeName") String employeeName,
                                                                  @Param("employeeId") String employeeId,
                                                                  @Param("startPage") Integer startPage,
                                                                  @Param("endPage") Integer endPage);
    /**
     * 获取门店教练列表
     * @param storeId
     * @return
     */
    public List<EmployeeEvaluateInfo>queryCoachListByStoreId(@Param("storeId")String storeId,
                                                             @Param("startPage") Integer startPage,
                                                             @Param("endPage") Integer endPage);

    /**
     * 获取该部门下所有的员工
     * @param remp
     * @return
     */
    public List<ResponseEmployee>queryEmpListByDepId(ResponseEmployee remp);

    /**
     * 编辑员工基本信息
     * @param emp
     */
    public void updateEmployeInfo(EmployeeInfo emp);

    /**
     * 编辑员工与商家关系信息
     * @param empr
     */
    public void updateEmpBusinessRelation(EmpBusinessRelation empr);

    /**查询员工表总记录数*/
    public long queryEmpCounts();

    /**
     * 分页查询员工表数据
     * @param startPage
     * @param endPage
     * @return
     */
    public List<ResponseEmployee>queryEmpAll( @Param("startPage") Integer startPage,
                                              @Param("endPage") Integer endPage);


    /**
     * 获取员工角色
     * @param employeeId
     * @return
     */
    public String getEmployeeRole(String employeeId);

    /**
     * 查询教练周平均评价
     * @param id 教练id
     * @return
     */
    public MemberEvaluate queryAvgMemberevaluate(String id);

    /**
     * 查询教练总评价
     * @param id
     * @return
     */
    public EmployeeEvaluateInfo getEmpMemberevaluateById(String id);

    /**
     * 获取员工评语列表
     * @param id
     * @return
     */
    public List<MemberEvaluate> listQueryMemberevaluate(@Param("id") String id,
                                                        @Param("courseId")String courseId,
                                                        @Param("startPage") Integer startPage,
                                                        @Param("endPage") Integer endPage);

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<UserRole> listUserRole(@Param("platform") String platform,
                                       @Param("businessId") String businessId,
                                       @Param("startPage") Integer startPage,
                                       @Param("endPage") Integer endPage);

    /**
     * 根据id查询教练详情
     * @param id
     * @return
     */
    public CoachInfo getCoachInfoById(String id);

    /**
     * 根据员工身份证获取员工信息
     * @param idCard
     * @param storeId
     * @return
     */
    public ResponseEmployee  getEmpBusinessRelationByIdCard( @Param("phoneNum") String phoneNum,
                                                             @Param("idCard") String idCard ,
                                                             @Param("storeId") String storeId);
}
