package com.dod.sport.service;

import com.dod.sport.domain.po.Base.EmpBusinessRelation;
import com.dod.sport.domain.po.Base.EmployeeInfo;
import com.dod.sport.domain.po.Response.ResponseEmployee;
import java.util.List;

/**
 * Created by defi on 2017-09-21.
 */
public interface IBusiEmployeeService {

    /**
     * 添加员工
     * @param em
     */
    public void addBaseEmployeeInfo(EmployeeInfo em);

    /**
     * 根据员工电话和商家编号获取员工关系表信息
     * @param em
     * @return
     */
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em);

    /**
     * 添加员工商家关系信息
     * @param em
     */
    public void addEmpBusinessRelation(EmpBusinessRelation em);

    /**
     * 获取门店所有员工信息
     * @param storeId
     * @return
     */
    public List<ResponseEmployee> listEmpBusinessRelationByStoreId(String storeId, Integer page);

    /**
     * 根据员工关系id获取员工信息
     * @param id
     * @return
     */
    public ResponseEmployee getEmpBusinessRelationById(String id);

    /**
     * 新增员工信息
     * @param employeeInfo
     * @param empBusinessRelation
     */
    public void addBaseAndBusinessEmployeeInfo(EmployeeInfo employeeInfo,EmpBusinessRelation empBusinessRelation);

    /**
     * 获取商家员工基础信息列表
     * @param employeeName
     * @param page
     * @return
     */
    public List<EmployeeInfo> listBusinessEmployee(String employeeName, Integer page);
}
