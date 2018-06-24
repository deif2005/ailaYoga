package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ISystemBaseDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.po.Base.EmpBusinessRelation;
import com.dod.sport.domain.po.Base.EmployeeInfo;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.domain.po.Business.BusiEmployee;
import com.dod.sport.domain.po.Response.ResponseEmployee;
import com.dod.sport.service.IBusiEmployeeService;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by defi on 2017-09-21.
 */
@Service
public class BusiEmployeeServiceImpl implements IBusiEmployeeService {

    @Autowired
    ISystemBaseDao systemBaseDao;

    /**
     * 添加基础员工信息
     * @param em
     */
    @Override
    public void addBaseEmployeeInfo(EmployeeInfo em) {
        String baseEmployeeSerialId = RedisCommon.getMaxRegisterId();
        if(baseEmployeeSerialId == ""|| baseEmployeeSerialId == null){
            String maxRegisterId = systemBaseDao.getMaxBaseEmployeeSerialId();
            baseEmployeeSerialId = RedisCommon.setAndReturnMaxRegisterId(maxRegisterId);
        }
        em.setBaseEmployeeSerialId(baseEmployeeSerialId);
        systemBaseDao.addBaseEmployeeInfo(em);
    }

    /**
     * 根据员工电话和商家编号获取员工关系表信息
     * @param em
     * @return
     */
    @Override
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em) {
        ResponseEmployee remp = systemBaseDao.queryEmployeeInfoByStoreIdAndPhoneNum(em);
        if (remp==null){
            return null;
        }
        if (remp!=null && remp.getEmpHead() !=null && !"".equals(remp.getEmpHead())){
            remp.setEmpHead(Configuration.getStaticShowPath()+remp.getEmpHead());
        }
        if ( remp.getEmpPicture()!=null && !"".equals(remp.getEmpPicture())){
            String[] picStr =  remp.getEmpPicture().split(";");
            if (picStr !=null && picStr.length>0){
                List<String> picPathList = new ArrayList<>();
                for (String str: picStr){
                    picPathList.add(Configuration.getStaticResourcePath()+str);
                }
                remp.setPicPathList(picPathList);
            }
        }
        return remp;
    }

    /**
     * 添加员工商家关系信息
     * @param em
     */
    @Override
    @Transactional
    public void addEmpBusinessRelation(EmpBusinessRelation em) {
        String empId = RedisCommon.getMaxEmployeeId(em.getBusinessId());
        if(empId == ""||empId==null){
            String maxEmployeeId = systemBaseDao.queryEmployeeIdMax(em.getBusinessId());
            empId = RedisCommon.setAndReturnMaxEmployeeId(em.getBusinessId(),maxEmployeeId);
        }
        em.setEmployeeSerialId(empId);
        systemBaseDao.addEmpBusinessRelation(em);
        List<EntryBill>entryBillList = systemBaseDao.queryentryBillByEmpId(em.getId());
        if (entryBillList ==null ||entryBillList.size()<1 ){
            //生成入职单
            EntryBill entryBill = new EntryBill();
            entryBill.setId(UUID.randomUUID().toString());
            entryBill.setBillId(RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_ENTRY));
            entryBill.setBusinessId(em.getBusinessId());
            entryBill.setStoreId(em.getStoreId());
            entryBill.setBillType(CommonEnum.Billinfo.billType.entryBill.getValue());
            entryBill.setEmployeeId(em.getId());
            entryBill.setStartTime(em.getEntryDate());
            entryBill.setDepId(em.getDepId());
            entryBill.setPositionId(em.getPositionId());
            entryBill.setApprover(em.getCreator());
            systemBaseDao.addEntryBill(entryBill);
        }
    }

    /**
     * 获取门店所有员工信息列表
     * @param storeId
     * @return
     */
    @Override
    public List<ResponseEmployee> listEmpBusinessRelationByStoreId(String storeId, Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<ResponseEmployee> rempList = systemBaseDao.listEmpBusinessRelationByStoreId(storeId,
                batchDataPage.getOffset(),batchDataPage.getPagesize());
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
     * 根据员工关系id获取员工信息
     * @param id
     * @return
     */
    @Override
    public ResponseEmployee getEmpBusinessRelationById(String id) {
        ResponseEmployee remp = systemBaseDao.getEmpBusinessRelationById(id);
        if (remp !=null){
            if (remp.getEmpHead() !=null && !"".equals(remp.getEmpHead())){
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
     * 新增员工信息
     * @param employeeInfo
     * @param empBusinessRelation
     */
    @Override
    @Transactional
    public void addBaseAndBusinessEmployeeInfo(EmployeeInfo employeeInfo,EmpBusinessRelation empBusinessRelation){
        EmployeeInfo emp = systemBaseDao.getEmployeeByPhoneNum(employeeInfo.getPhoneNum(), null);
        //判断员工基础信息表中是否存在该信息,如果存在,则不添加基础信息
        if(emp==null){
            addBaseEmployeeInfo(employeeInfo);
        }
        addEmpBusinessRelation(empBusinessRelation);
    }

    /**
     * 获取商家员工基础信息列表
     * @param employeeName
     * @param page
     * @return
     */
    public List<EmployeeInfo> listBusinessEmployee(String employeeName, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<EmployeeInfo> employeeInfoList = systemBaseDao.listBusinessEmployeeInfo(employeeName,
                batchDataPage.getOffset(),batchDataPage.getPagesize());
        return employeeInfoList;
    }
}
