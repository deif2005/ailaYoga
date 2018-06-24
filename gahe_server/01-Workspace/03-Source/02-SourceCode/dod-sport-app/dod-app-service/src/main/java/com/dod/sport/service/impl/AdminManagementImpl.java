package com.dod.sport.service.impl;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IAdminManagementDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.Position;
import com.dod.sport.domain.po.Bill.*;
import com.dod.sport.service.IAdminManagementService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.DateUtil;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by defi on 2017-08-10.
 * 商家端行政管理服务类
 */

@Service
public class AdminManagementImpl implements IAdminManagementService {

    @Autowired
    IAdminManagementDao adminManagementDao;

    /**
     * 根据单据类型获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单,
     * @param page 当前页数
     * @return
     */
    @Override
    public List<SummaryBill> listSummaryBillInfo(String storeId, String approveStatus, String billType, Integer page ){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<SummaryBill> listVacationBill = adminManagementDao.listSummaryBillInfo(storeId, batchDataPage.getOffset(),
                batchDataPage.getPagesize(), billType ,approveStatus );
        batchDataPage.setRows(listVacationBill);
        return listVacationBill;
    }

    /**
     * 根据用户获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单,
     * @param page 当前页数
     * @param employeeId 员工
     * @return
     */
    @Override
    public List<BaseBill> getUserBillInfoList(String storeId, String billType, Integer page,String employeeId ){

        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<BaseBill> listVacationBill = adminManagementDao.getUserBillInfoList(storeId, batchDataPage.getOffset(),
                batchDataPage.getPagesize(), billType, employeeId );
        batchDataPage.setRows(listVacationBill);
        return listVacationBill;
    }
    /**
     * 获取请假/休假单详细信息
     * @param id
     * @return
     */
    @Override
    public VacationBill getVacationBillDetailById(String id){
        VacationBill vacationBill = adminManagementDao.getVacationBillDetailById(id);
        return vacationBill;
    }

    /**
     * 获取转正单详细信息
     * @param id
     * @return
     */
    @Override
    public RegularBill getRegularBillDetailById(String id){
        RegularBill regularBill = adminManagementDao.getRegularBillDetailById(id);
        return regularBill;
    }

    /**
     * 新增请假/休假单信息
     * @param vacation
     */
    @Override
    public void addVactionBill(VacationBill vacation){
        vacation.setId(UUID.randomUUID().toString());
        String billId = RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_VACATION);
        vacation.setBillId(billId);
        adminManagementDao.addVacationBill(vacation);
    }

    /**
     * 新增转正单信息
     * @param regularBill
     */
    public void addRegularBill(RegularBill regularBill){
        regularBill.setId(UUID.randomUUID().toString());
        String billId = RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_REGULAR);
        regularBill.setBillId(billId);
        adminManagementDao.addRegularBill(regularBill);
    }

    /**
     * 新增离职单信息
     * @param leaveBill
     */
    public void addLeaveBill(LeaveBill leaveBill){
        leaveBill.setId(UUID.randomUUID().toString());
        String billId = RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_LEAVE);
        leaveBill.setBillId(billId);
        adminManagementDao.addLeaveBill(leaveBill);
    }

    /**
     * 新增调岗单
     * @param transferBill
     */
    public void addTransferBill(TransferBill transferBill){
        transferBill.setId(UUID.randomUUID().toString());
        String billId = RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_TRANSFER);
        transferBill.setBillId(billId);
        adminManagementDao.addTransferBill(transferBill);
    }

    /**
     * 新增报销单
     * @param expenseAccountBill
     */
    public void addExpenseAccountBill(ExpenseAccountBill expenseAccountBill){
        expenseAccountBill.setId(UUID.randomUUID().toString());
        String billId = RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_ACCOUNT);
        expenseAccountBill.setBillId(billId);
        adminManagementDao.addExpenseAccountBill(expenseAccountBill);
    }

    /**
     * 获取离职单详细信息
     * @param id
     * @return
     */
    @Override
    public LeaveBill getLeaveBillDetailById(String id){
        LeaveBill leaveBill = adminManagementDao.getLeaveBillDetailById(id);
        return leaveBill;
    }

    /**
     * 获取调岗单详细
     * @param id
     * @return
     */
    @Override
    public TransferBill getTransferBillDetailById(String id){
        TransferBill transferBill = adminManagementDao.getTransferBillInfoById(id);

        if (transferBill == null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_getdataerror.getValue());
        }
        if (transferBill.getEntryDate() == null || "".equals(transferBill.getEntryDate())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_getdataerror.getValue());
        }
        //工龄计算
        Date date = new Date();
        Date beginDate = DateUtil.parse(transferBill.getEntryDate(), SysConfigConstants.DATE_FORMAT_FORDATE);
        Integer month = DateUtil.getMonth(beginDate,date);
        double years = Math.floor(month/12);
        transferBill.setWorkDuration(Double.toString(years));
        return transferBill;
    }

    /**
     * 获取职位信息列表
     * @return
     */
    public List<Position> listPositionInfo(){
        List<Position> positionList = adminManagementDao.listPositionInfo();
        return positionList;
    }

    /**
     * 获取职位信息
     * @return
     */
    @Override
    public Position getPositionInfo(String id){
        Position position = adminManagementDao.getPositionInfo(id);
        return position;
    }

    /**
     * 添加职位信息
     * @param position
     */
    @Override
    public void addPosition(Position position){
        String maxId = RedisCommon.getMaxPositionId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxPositionId(adminManagementDao.getMaxPositionId());
        }
        position.setId(UUID.randomUUID().toString());
        position.setPositionSerialId(maxId);
        adminManagementDao.addPosition(position);
    }

    /**
     * 获取商家职位信息列表
     * @return
     */
    @Override
    public List<Position> listBusiPositionInfo(String businessId){
        List<Position> positionList = adminManagementDao.listBusiPositionInfo(businessId);
        return positionList;
    }

    /**
     * 获取商家职位信息
     * @return
     */
    @Override
    public Position getBusiPositionInfo(String id){
        Position position = adminManagementDao.getBusiPositionInfo(id);
        return position;
    }

    /**
     * 添加商家职位信息
     * @param position
     */
    @Override
    public void addBusiPosition(Position position){
        String maxId = RedisCommon.getMaxPositionId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxPositionId(adminManagementDao.getMaxPositionId());
        }
        position.setId(UUID.randomUUID().toString());
        position.setPositionSerialId(maxId);
        adminManagementDao.addBusiPosition(position);
    }

    /**
     * 获取报销单详细信息
     * @param id
     * @return
     */
    public ExpenseAccountBill getExpenseAccountBillDetailById(String id){
        ExpenseAccountBill expenseAccountBill = adminManagementDao.getExpenseAccountBillInfoById(id);
//        if (expenseAccountBill.getAccountBill() != null && !"".equals(expenseAccountBill.getAccountBill())){
//            List<String> accountBillList = Arrays.asList(expenseAccountBill.getAccountBill());
//        }
        return expenseAccountBill;
    }

    /**
     *新增入职表单
     * @param entryBill
     *
     */
    @Override
    public void addEntryBill(EntryBill entryBill) {
        entryBill.setId(UUID.randomUUID().toString());
        entryBill.setBillId(RedisCommon.getBillIdByType(SysConfigConstants.BILL_FREFIX_ENTRY));
        adminManagementDao.addEntryBill(entryBill);
    }

    /**
     * 查询入职详情
     * @param EntryBillId
     * @return
     */
    @Override
    public EntryBill queryEntryBillInfoById(String EntryBillId) {
        return adminManagementDao.queryEntryBillInfoById(EntryBillId);
    }

    /**
     * 查询门店入职列表
     * @param storeId
     * @return
     */
    @Override
    public List<EntryBill> queryEntryBillListByStoreId(String storeId) {
        return adminManagementDao.queryEntryBillListByStoreId(storeId);
    }

    /**
     * 单据审核
     * @param id
     * @param approveStatus
     * @param approver
     * @param approveDesc
     * @return
     */
    @Override
     public void auditDocument(String id,String approveStatus,String approver,String approveDesc) {
        BaseBill  baseBill= new BaseBill();
        baseBill.setId(id);
        baseBill.setApproveStatus(approveStatus);
        baseBill.setApprover(approver);
        baseBill.setApproveDesc(approveDesc);
         adminManagementDao.auditDocument(baseBill);
    }

    /**
     * 获取员工入职单
     * @param employeeId
     * @return
     */
    @Override
    public List<EntryBill> queryentryBillByEmpId(String employeeId) {
        return adminManagementDao.queryentryBillByEmpId(employeeId);
    }

    /**
     * 删除未审核表单
     * @param id
     */
    @Override
    public void delBillById(String id) {
        adminManagementDao.delBillById(id);
    }

    /**
     * 根据EmpId单据类型获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单,
     * @return
     */
    @Override
    public List<BaseBill> getSummaryBillEmpId(String employeeId,String storeId, String billType ){

        List<BaseBill> listVacationBill = adminManagementDao.getSummaryBillEmpId(employeeId, storeId, billType);

        return listVacationBill;
    }

    /**
     * 根据EmpId请假单据类型获取单据列表
     * @param storeId 门店编号
     * @param employeeId
     * @return
     */
    @Override
    public List<BaseBill> getVacationBillEmpById(String billType,String employeeId,String startTime){

       return adminManagementDao.getVacationBillEmpById(employeeId, billType, startTime);
    }

}
