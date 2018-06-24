package com.dod.sport.service;

import com.dod.sport.domain.po.Base.Position;
import com.dod.sport.domain.po.Bill.*;

import java.util.List;

/**
 * Created by defi on 2017-08-10.
 * 商家端行政管理服务接口
 */
public interface IAdminManagementService {

    /**
     * 根据单据类型获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单
     * @param page 当前页数
     * @return
     */
    public List<SummaryBill> listSummaryBillInfo(String storeId, String approveStatus,  String billType, Integer page);

    /**
     * 根据用户类型获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单
     * @param page 当前页数
     * @param  employeeId  用户ID
     * @return
     */
    public List<BaseBill> getUserBillInfoList(String storeId, String billType, Integer page,String employeeId );

    /**
     * 获取请假/休假单信息
     * @param id
     * @return
     */
    public VacationBill getVacationBillDetailById(String id);

    /**
     * 获取转正单详细信息
     * @param id
     * @return
     */
    public RegularBill getRegularBillDetailById(String id);

    /**
     * 新增请假/休假单信息
     * @param vacation
     */
    public void addVactionBill(VacationBill vacation);

    /**
     * 新增转正单信息
     * @param regularBill
     */
    public void addRegularBill(RegularBill regularBill);

    /**
     * 新增离职单信息
     * @param leaveBill
     */
    public void addLeaveBill(LeaveBill leaveBill);

    /**
     * 新增调岗单信息
     * @param transferBill
     */
    public void addTransferBill(TransferBill transferBill);

    /**
     * 获取离职单详细信息
     * @param id
     * @return
     */
    public LeaveBill getLeaveBillDetailById(String id);

    /**
     * 获取调岗单详细信息
     * @param id
     * @return
     */
    public TransferBill getTransferBillDetailById(String id);

    /**
     * 获取职位信息列表
     * @return
     */
    public List<Position> listPositionInfo();

    /**
     * 获取职位信息
     * @return
     */
    public Position getPositionInfo(String id);

    /**
     * 添加职位信息
     * @param position
     */
    public void addPosition(Position position);

    /**
     * 获取职位信息列表
     * @return
     */
    public List<Position> listBusiPositionInfo(String businessId);

    /**
     * 获取职位信息
     * @return
     */
    public Position getBusiPositionInfo(String id);

    /**
     * 添加职位信息
     * @param position
     */
    public void addBusiPosition(Position position);

    /**
     * 新增报销单据信息
     * @param expenseAccountBill
     */
    public void addExpenseAccountBill(ExpenseAccountBill expenseAccountBill);

    /**
     * 获取报销单详细信息
     * @param id
     * @return
     */
    public ExpenseAccountBill getExpenseAccountBillDetailById(String id);

    /**
     * 新增入职单
     * @param entryBill
     */
    public void addEntryBill(EntryBill entryBill);

    /**
     * 查询离职详情
     * @param EntryBillId
     * @return
     */
    public EntryBill queryEntryBillInfoById(String EntryBillId);

    /**
     * 查询门店离职列表
     * @param storeId
     * @return
     */
    public List<EntryBill> queryEntryBillListByStoreId(String storeId);

    /**
     * 审核单据信息
     * @param id
     * @param approveStatus
     * @return
     */
    public void auditDocument(String id,String approveStatus,String approver, String approveDesc);

    /**
     * 获取员工的入职单
     * @param employeeId
     * @return
     */
    public List<EntryBill>queryentryBillByEmpId(String employeeId);

    /**
     * 根据id删除未审核的表单
     * @param id
     */
    public void delBillById(String id);

    /**
     * 根据EmpId单据类型获取单据列表
     * @param storeId 门店编号
     * @param billType 单据类型:0全部, 1请假单，2,入职单，3,离职单，4调岗单，5转正单,
     * @return
     */
    public List<BaseBill> getSummaryBillEmpId(String employeeId,String storeId,  String billType );

     /**
     * 根据EmpId请假单据类型获取单据列表
     * @param storeId 门店编号
     * @param employeeId
     * @return
     */
    public List<BaseBill> getVacationBillEmpById(String billType,String employeeId,String startTime);
}
