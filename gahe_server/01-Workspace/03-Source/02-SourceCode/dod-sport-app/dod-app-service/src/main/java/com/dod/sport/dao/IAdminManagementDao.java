package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.Position;
import com.dod.sport.domain.po.Bill.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by defi on 2017-08-10.
 */
@Repository
public interface IAdminManagementDao {

//    /**
//     * 分批查询单据概要信息
//     * @param storeId 门店编号
//     * @param startPage
//     * @param endPage
//     * @param billType 单据类型
//     * @return
//     */
//    public List<BaseBill> listBillInfo(@Param("storeId") String storeId,
//                                       @Param("startPage") Integer startPage,
//                                       @Param("endPage") Integer endPage,
//                                       @Param("billType") String billType,
//                                       @Param("id") String id,
//                                       @Param("approveStatus") String approveStatus
//                                       );

    /**
     * 分批查询单据概要信息
     * @param storeId 门店编号
     * @param startPage
     * @param endPage
     * @param billType 单据类型 0为全部类型
     * @return
     */
    public List<SummaryBill> listSummaryBillInfo(@Param("storeId") String storeId,
                                       @Param("startPage") Integer startPage,
                                       @Param("endPage") Integer endPage,
                                       @Param("billType") String billType,
                                       @Param("approveStatus") String approveStatus);

    /**
     * 查询用户单据信息
     * @param storeId 门店编号
     * @param startPage
     * @param endPage
     * @param billType 单据类型 0为全部类型
     * @return
     */
    public List<BaseBill> getUserBillInfoList(@Param("storeId") String storeId,
                                               @Param("startPage") Integer startPage,
                                               @Param("endPage") Integer endPage,
                                               @Param("billType") String billType,
                                               @Param(value = "employeeId") final String employeeId );

    /**
     * 根据id号获取请假、休假单单据详细
     * @param Id 单据编号
     * @return
     */
    public VacationBill getVacationBillDetailById(String Id);


    /**
     * 根据id号获取转正单单据详细
     * @param Id 单据编号
     * @return
     */
    public RegularBill getRegularBillDetailById(String Id);

    /**
     * 新增请假单
     * @param vacationBill
     */
    public void addVacationBill(VacationBill vacationBill);

    /**
     * 新增转正单
     * @param regularBill
     */
    public void addRegularBill(RegularBill regularBill);

    /**
     * 新增离职单
     * @param leaveBill
     */
    public void addLeaveBill(LeaveBill leaveBill);

    /**
     * 新增调岗单
     * @param transferBill
     */
    public void addTransferBill(TransferBill transferBill);

    /**
     * 新增报销单
     * @param expenseAccountBill
     */
    public void addExpenseAccountBill(ExpenseAccountBill expenseAccountBill);

    /**
     * 获取报销单据详细信息
     * @param id
     * @return
     */
    public ExpenseAccountBill getExpenseAccountBillInfoById(String id);

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
    public TransferBill getTransferBillInfoById(String id);

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
     * 获取商家职位信息列表
     * @return
     */
    public List<Position> listBusiPositionInfo(String businessId);

    /**
     * 获取商家职位信息
     * @return
     */
    public Position getBusiPositionInfo(String id);

    /**
     * 添加商家职位信息
     * @param position
     */
    public void addBusiPosition(Position position);

    /**
     * 获取最大职位信息
     * @return
     */
    public String getMaxPositionId();

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
     * 审核单据
     * @param baseBill
     * @return
     */
    public void auditDocument( BaseBill  baseBill);

    /**
     * 获取员工入职单
     * @param employeeId
     * @return
     */
    public List<EntryBill> queryentryBillByEmpId(String employeeId);

    /**
     * 根据id删除未审核的表单
     * @param id
     */
    public void delBillById(String id);

    /**
     * 查询单据概要信息
     * @param storeId 门店编号
     * @param billType 单据类型 0为全部类型
     * @return
     */
    public List<BaseBill> getSummaryBillEmpId(@Param("employeeId") String employeeId,
                                              @Param("storeId") String storeId,
                                              @Param("billType") String billType);

    /**
     * 根据EmpId请假单据类型获取单据列表
     * @param storeId 门店编号
     * @param employeeId
     * @return
     */
    public List<BaseBill> getVacationBillEmpById(@Param("employeeId") String employeeId,
                                                 @Param("billType") String billType,
                                                 @Param("startTime") String startTime );
}
