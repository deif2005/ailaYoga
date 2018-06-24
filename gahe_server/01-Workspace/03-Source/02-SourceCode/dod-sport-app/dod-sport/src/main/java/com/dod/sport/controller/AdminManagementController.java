package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.Base.Position;
import com.dod.sport.domain.po.Bill.*;
import com.dod.sport.service.IAdminManagementService;
import com.dod.sport.service.IMessageService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by defi on 2017-08-10.
 * 行政管理接口
 */

@Controller
@RequestMapping(value = "api/AdminManage/v1")
public class AdminManagementController extends BaseController {
//    Logger logger = new

    @Autowired
    private IAdminManagementService adminManagementService;

    @Autowired
    IMessageService messagePushService;

    /**
     * 获取单据数据列表
     * @param storeId
     * @param billType 单据类型 单据类型:0，全部，1请假单，2,入职单，3,离职单，4调岗单，5转正单，6报销单
     * @param page 刷新页数
     * @param approveStatus 审核状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBillInfo", method = RequestMethod.POST)
    public Map<String,Object> listBillInfo(@RequestParam(value = "storeId") final String storeId,
                                               @RequestParam(value = "billType") final String billType,
                                               @RequestParam(value = "approveStatus", required = false) final String approveStatus,
                                               @RequestParam(value = "page") final Integer page)
    {
        Map<String,Object> map = new HashMap<>();
        List<SummaryBill> bills = adminManagementService.listSummaryBillInfo(storeId, approveStatus, billType, page);
        map.put("bills",bills);
        return map;
    }

    /**
     * 获取用户单据历史记录详情
     * @param storeId
     * @param billType 单据类型 单据类型:0，全部，1请假单，2,入职单，3,离职单，4调岗单，5转正单，6报销单
     * @param page 刷新页数
     * @param employeeId 员工id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserBillInfoList", method = RequestMethod.POST)
    public Map<String,Object> getUserBillInfoList(@RequestParam(value = "storeId") final String storeId,
                                           @RequestParam(value = "billType") final String billType,
                                           @RequestParam(value = "page") final Integer page,
                                           @RequestParam(value = "employeeId") final String employeeId )
    {
        Map<String,Object> map = new HashMap<>();
        List<BaseBill> bills = adminManagementService.getUserBillInfoList(storeId, billType, page, employeeId);
        map.put("bills",bills);
        return map;
    }


    /**
     * 新增假期单据
     * @param businessId
     * @param storeId
     * @param vacationType
     * @param employeeId
     * @param startTime
     * @param endTime
     * @param duration
     * @param description
     * @param approver
     */
    @ResponseBody
    @RequestMapping(value = "/addVacationBill", method = RequestMethod.POST)
    public String addVacationBill(@RequestParam(value = "businessId") final String businessId,
                                @RequestParam(value = "storeId") final String storeId,
                                @RequestParam(value = "vacationType") final Integer vacationType,
                                @RequestParam(value = "employeeId") final String employeeId,
                                @RequestParam(value = "startTime") final String startTime,
                                @RequestParam(value = "startDay", required = false) final  String startDay,
                                @RequestParam(value = "endTime") final String endTime,
                                @RequestParam(value = "endDay", required = false) final  String endDay,
                                @RequestParam(value = "duration") final Float duration,
                                @RequestParam(value = "description") final String description,
                                @RequestParam(value = "approver") final String approver){
        List<BaseBill>  bills= adminManagementService.getVacationBillEmpById(CommonEnum.Billinfo.billType.vacationBill.getValue(),employeeId,startTime);
        if (bills.size() > 1  ){
            throw new BusiException( CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_bill.getValue());
        }
        VacationBill vacationBill = new VacationBill();
        vacationBill.setBillType(CommonEnum.Billinfo.billType.vacationBill.getValue());
        vacationBill.setBusinessId(businessId);
        vacationBill.setStoreId(storeId);
        vacationBill.setVacationType(String.valueOf(vacationType));
        vacationBill.setEmployeeId(employeeId);
       /* vacationBill.setStartTime(sdf.format(startTime));
        vacationBill.setEndTime(sdf.format(endTime));*/
        vacationBill.setStartTime(startTime);
        vacationBill.setStartDay(startDay);
        vacationBill.setEndTime(endTime);
        vacationBill.setEndDay(endDay);
        vacationBill.setDuration(String.valueOf(duration));
        vacationBill.setDescription(description);
        vacationBill.setApprover(approver);
        adminManagementService.addVactionBill(vacationBill);
        return "";
    }

    /**
     * 获取假期单据明细内容
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getVacationBillDetail", method = RequestMethod.POST)
    public Map<String,Object> getVacationBill(@RequestParam(value = "id") final String id){
        Map<String,Object> map = new HashMap<>();
        BaseBill vacationBill = adminManagementService.getVacationBillDetailById(id);
        map.put("VacationBills",vacationBill);
        return map;
    }

    /**
     * 获取转正单单据明细内容
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRegularBillDetail", method = RequestMethod.POST)
    public Map<String,Object> getRegularBill(@RequestParam(value = "id") final String id){
        Map<String,Object> map = new HashMap<>();
        BaseBill regularBill = adminManagementService.getRegularBillDetailById(id);
        map.put("regularBills",regularBill);
        return map;
    }

    /**
     * 新增转正单
     * @param businessId
     * @param storeId
     * @param positionDesc
     * @param workDesc
     * @param suggestion
     * @param approver
     * @returns
     */
    @ResponseBody
    @RequestMapping(value = "/addRegularEmpInfo", method = RequestMethod.POST)
    public String addRegularBill(@RequestParam(value = "businessId") final String businessId,
                                 @RequestParam(value = "storeId") final String storeId,
                                 @RequestParam(value = "employeeId") final String employeeId,
                                 @RequestParam(value = "positionDesc", required = false) final String positionDesc,
                                 @RequestParam(value = "workDesc", required = false) final String workDesc,
                                 @RequestParam(value = "suggestion", required = false) final String suggestion,
                                 @RequestParam(value = "approver") final String approver){


        List<BaseBill> listBillInfo = adminManagementService.getSummaryBillEmpId(employeeId, storeId, CommonEnum.Billinfo.billType.regularBill.getValue());
        if(listBillInfo.size() > 1 ){
            throw new BusiException( CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_bill.getValue());
        }

        RegularBill regularBill = new RegularBill();
        regularBill.setBillType(CommonEnum.Billinfo.billType.regularBill.getValue());
        regularBill.setBusinessId(businessId);
        regularBill.setStoreId(storeId);
        regularBill.setEmployeeId(employeeId);
        regularBill.setPositionDesc(positionDesc);
        regularBill.setWorkDesc(workDesc);
        regularBill.setSuggestion(suggestion);
        regularBill.setApprover(approver);
        adminManagementService.addRegularBill(regularBill);
        return "";
    }

    /**
     * 新增离职单
     * @param businessId
     * @param storeId
     * @param leaveReason
     * @param handler
     * @param handleItem
     * @param approver
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addLeaveEmpInfo", method = RequestMethod.POST)
    public String addLeaveBill(@RequestParam(value = "businessId") final String businessId,
                               @RequestParam(value = "storeId") final String storeId,
                               @RequestParam(value = "leaveDate") final String leaveDate,
                               @RequestParam(value = "employeeId") final String employeeId,
                               @RequestParam(value = "leaveReason") final String leaveReason,
                               @RequestParam(value = "handler") final String handler,
                               @RequestParam(value = "handleItem") final String handleItem,
                               @RequestParam(value = "approver") final String approver){
//        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
//        List<EntryBill>entryBillList = adminManagementService.queryentryBillByEmpId(employeeId);
//        if (entryBillList ==null ||entryBillList.size()<1 ){
//             throw new BusiException( CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_bill.getValue());
//        }
        List<BaseBill> listBillInfo = adminManagementService.getSummaryBillEmpId(employeeId, storeId, CommonEnum.Billinfo.billType.leaveBill.getValue());
        if(listBillInfo.size() > 1 ){
            throw new BusiException( CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_bill.getValue());
        }
        LeaveBill leaveBill = new LeaveBill();
        leaveBill.setBillId(SysConfigConstants.BILL_FREFIX_LEAVE);
        leaveBill.setBillType(CommonEnum.Billinfo.billType.leaveBill.getValue());
        leaveBill.setBusinessId(businessId);
        leaveBill.setStoreId(storeId);
        leaveBill.setEmployeeId(employeeId);
        leaveBill.setEndTime(leaveDate);
        leaveBill.setLeaveReason(leaveReason);
        leaveBill.setHandler(handler);
        leaveBill.setHandleItem(handleItem);
        leaveBill.setApprover(approver);
        adminManagementService.addLeaveBill(leaveBill);
        return "";
    }

    /**
     * 新增调岗单
     * @param businessId
     * @param storeId
     * @param transferReason
     * @param transferDate
     * @param transferPositionId
     * @param transferDepId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addTransferEmpInfo", method = RequestMethod.POST)
    public String addTransferBill(@RequestParam("businessId") final String businessId,
                                  @RequestParam("storeId") final String storeId,
                                  @RequestParam("transferReason") final String transferReason,
                                  @RequestParam("employeeId") final String employeeId,
                                  @RequestParam("transferDate") final String transferDate,
                                  @RequestParam("transferPositionId") final String transferPositionId,
                                  @RequestParam("transferDepId") final String transferDepId,
                                  @RequestParam("approver") final String approver){
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
        TransferBill transferBill = new TransferBill();
        transferBill.setBillType(CommonEnum.Billinfo.billType.transferBill.getValue());
        transferBill.setBusinessId(businessId);
        transferBill.setStoreId(storeId);
        transferBill.setEmployeeId(employeeId);
        transferBill.setTransferReason(transferReason);
        transferBill.setTransferDate(transferDate);
        transferBill.setTransferDepId(transferDepId);
        transferBill.setTransferPositionId(transferPositionId);
        transferBill.setApprover(approver);
        adminManagementService.addTransferBill(transferBill);
        return "";
    }

    /**
     * 新增报销单据信息
     * @param storeId
     * @param employeeId
     * @param description
     * @param account
     * @param approver
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addExpenseAccountBill", method = RequestMethod.POST)
    public Map addExpenseAccountBill(HttpServletRequest request,
                                     @RequestParam("businessId") final String businessId,
                                     @RequestParam("storeId") final String storeId,
                                     @RequestParam("employeeId") final String employeeId,
                                     @RequestParam("description") final String description,
                                     @RequestParam("account") final String account,
                                     @RequestParam("approver") final String approver){
        Map map = new HashMap<>();
        ExpenseAccountBill expenseAccountBill = new ExpenseAccountBill();
        List<String> showPathList = new ArrayList<>();
        expenseAccountBill.setBillType(CommonEnum.Billinfo.billType.reimbursementBill.getValue());
        expenseAccountBill.setBusinessId(businessId);
        expenseAccountBill.setStoreId(storeId);
        expenseAccountBill.setEmployeeId(employeeId);
        expenseAccountBill.setDescription(description);
        expenseAccountBill.setAccount(account);
        expenseAccountBill.setApprover(approver);
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadExpenseAccountPicturePath(employeeId);
            //文件展示地址
            String baseShowPath = Configuration.getStaticShowPath();
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                    showPathList.add(baseShowPath + path);
                }
                expenseAccountBill.setAccountBill(stringBuffer.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("AdminManagementController-addExpenseAccountBill:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        adminManagementService.addExpenseAccountBill(expenseAccountBill);
//        if (showPathList.size() == 0){
//            map.put(showPathList,"");
//        }else{
//            map.put("showPathList",showPathList);
//        }
        map.put("showPathList",showPathList);
        return map;
    }

    /**
     * 获取离职单信息
     * @param id
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/getLeaveBillDetail", method = RequestMethod.POST)
    public Map getLeaveBillDetail(@RequestParam("id") final String id){
        Map<String,Object> map = new HashMap<>();
        LeaveBill leaveBill = adminManagementService.getLeaveBillDetailById(id);
        map.put("leaveBill",leaveBill);
        return map;
    }

    /**
     * 获取调岗单信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTransferBillDetail", method = RequestMethod.POST)
    public Map getTransferBillDetail(@RequestParam("id") final String id){
        Map<String,Object> map = new HashMap<>();
        TransferBill transferBill = adminManagementService.getTransferBillDetailById(id);
        map.put("transferBill",transferBill);
        return map;
    }

    /**
     * 获取报销单信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExpenseAccountBillDetail", method = RequestMethod.POST)
    public Map getExpenseAccountBillDetail(@RequestParam("id") final String id){
        Map<String,Object> map = new HashMap<>();
        ExpenseAccountBill expenseAccountBill = adminManagementService.getExpenseAccountBillDetailById(id);
        map.put("expenseAccountBill",expenseAccountBill);
        return map;
    }

    /**
     * 获取职位信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPositionInfo", method = RequestMethod.POST)
    public Map listPositionInfo(){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = adminManagementService.listPositionInfo();
        map.put("positionList",positionList);
        return map;
    }

    /**
     * 获取职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionInfo", method = RequestMethod.POST)
    public Map getPositionInfo(String id){
        Map<String,Object> map = new HashMap<>();
        Position position = adminManagementService.getPositionInfo(id);
        map.put("position",position);
        return map;
    }

    /**
     * 添加职位信息
     * @param positionName
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public String addPosition(@RequestParam("positionName") final String positionName,
                              @RequestParam("creator") final String creator){
        Position position = new Position();
        position.setPositionName(positionName);
        position.setCreator(creator);
        adminManagementService.addPosition(position);
        return "";
    }

    /**
     * 获取商家职位信息列表
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusiPositionInfo", method = RequestMethod.POST)
    public Map listBusiPositionInfo(@RequestParam("businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = adminManagementService.listBusiPositionInfo(businessId);
        map.put("positionList",positionList);
        return map;
    }

    /**
     * 获取职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBusiPositionInfo", method = RequestMethod.POST)
    public Map getBusiPositionInfo(String id){
        Map<String,Object> map = new HashMap<>();
        Position position = adminManagementService.getBusiPositionInfo(id);
        map.put("position",position);
        return map;
    }

    /**
     * 添加商家职位信息
     * @param positionName
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusiPosition", method = RequestMethod.POST)
    public String addBusiPosition(@RequestParam("businessId") final String businessId,
                                  @RequestParam("positionName") final String positionName,
                                  @RequestParam("creator") final String creator){
        Position position = new Position();
        position.setBusinessId(businessId);
        position.setPositionName(positionName);
        position.setCreator(creator);
        adminManagementService.addBusiPosition(position);
        return "";
    }

    /**
     * 查询入职详情
     * @param EntryBillId 入职单id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryEntryBillInfoById", method = RequestMethod.POST)
    public Map<String,Object> queryEntryBillInfoById(@RequestParam("EntryBillId") final String EntryBillId){

        Map<String,Object> map = new HashMap<>();
        EntryBill entryBill = adminManagementService.queryEntryBillInfoById(EntryBillId);
        if (entryBill==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("entryBill",entryBill);
        return map;
    }

    /**
     * 获取门店入职列表
     * @param storeId 门店id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryEntryBillListByStoreId", method = RequestMethod.POST)
    public Map<String,Object> queryEntryBillListByStoreId(@RequestParam("storeId") final String storeId){
        Map<String,Object> map = new HashMap<>();
       List<EntryBill> list = adminManagementService.queryEntryBillListByStoreId(storeId);
        if (list==null||list.size()<1){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("list",list);
        return map;
    }

    /**
     * 单据审核
     * @param approveStatus 门店id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/auditDocument", method = RequestMethod.POST)
    public String auditDocument(@RequestParam("id") final String id,
                                @RequestParam("approveStatus") final String approveStatus,
                                @RequestParam("employeeId") final String employeeId,
                                @RequestParam("billType") final String billType,
                                @RequestParam(value = "approveDesc", required = false) final String approveDesc,
                                @RequestParam("approver") final String approver ){

        adminManagementService.auditDocument(id,approveStatus, approver,approveDesc);
//        messagePushService.sendMessageToTags(isBillType(billType),approveStatus.equals("2") ? "通过   " : "拒绝  " ,approveDesc,new HashMap(),employeeId);
        return "";
    }

    public String isBillType(String billType){
        String title =null;
        if(billType.equals(CommonEnum.Billinfo.billType.vacationBill.getValue())){
            title ="请假单";
        }
        if(billType.equals(CommonEnum.Billinfo.billType.entryBill.getValue())){
            title ="入职单";
        }
        if(billType.equals(CommonEnum.Billinfo.billType.leaveBill.getValue())){
            title ="离职单";
        }
        if(billType.equals(CommonEnum.Billinfo.billType.transferBill.getValue())){
            title ="调岗单";
        }
        if(billType.equals(CommonEnum.Billinfo.billType.regularBill.getValue())){

            title ="转正单";
        }
        if(billType.equals(CommonEnum.Billinfo.billType.reimbursementBill.getValue())){
            title ="报销单";
        }

        return title;
    }
    /**
     * 删除未审核表单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delBillById", method = RequestMethod.POST)
    public String delBillById(@RequestParam("id") final String id){
        adminManagementService.delBillById(id);
        return "";
    }
}
