package com.dod.sport.controller;

import com.dod.sport.domain.po.ExpenseRecord;
import com.dod.sport.domain.po.ResponseExpenseRecord;
import com.dod.sport.service.IExpenseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/1.
 */
@Controller
@RequestMapping("api/ExpenseRecord/v1")
public class ExpenseRecordController {

    @Autowired
    IExpenseRecordService expenseRecordService;

    /**
     * 新增消费记录
     * @param expenseClassify 消费分类 1:对商家;2:对平台
     * @param payType 支付方式:1.零钱支付;2支付宝支付;3微信支付;4银行卡支付;5信用卡支付
     * @param payMoney 消费金额
     * @param tradingObject 交易对象
     * @param tradingContent 交易内容
     * @param storeId 门店id
     * @param clientUserId 用户id
     * @param payStatus 支付状态:1成功;2失败
     * @param remark /备注
     * @return
     */
    @RequestMapping(value = "addExpenseRecord",method = RequestMethod.POST)
    @ResponseBody
    public String addExpenseRecord(@RequestParam(value = "expenseClassify")String expenseClassify,
                                   @RequestParam(value = "payType")String payType,
                                   @RequestParam(value = "payMoney")String payMoney,
                                   @RequestParam(value = "tradingObject")String tradingObject,
                                   @RequestParam(value = "tradingContent")String tradingContent,
                                   @RequestParam(value = "clientUserId")String clientUserId,
                                   @RequestParam(value = "storeId")String storeId,
                                   @RequestParam(value = "payStatus")String payStatus,
                                   @RequestParam(value = "remark")String remark){
        ExpenseRecord expenseRecord = new ExpenseRecord();
        expenseRecord.setExpenseClassify(expenseClassify);
        expenseRecord.setPayType(payType);
     /*   expenseRecord.setPayMoney(payMoney);
        expenseRecord.setTradingObject(tradingObject);
        expenseRecord.setTradingContent(tradingContent);
        expenseRecord.setClientUserId(clientUserId);
        expenseRecord.setStoreId(storeId);
        expenseRecord.setPayStatus(payStatus);*/
        expenseRecord.setRemark(remark);
        expenseRecordService.addExpenseRecord(expenseRecord);
        return "";
    }

    /**
     * 获取用户的消费记录
     * @param userId 用户id
     * @param page   //开始页
     * @param pageSize //每页显示数据数量
     * @return
     */
    @RequestMapping(value = "listUserExpenseRecord" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>listUserExpenseRecord(@RequestParam(value = "userId")String userId,
                                                   @RequestParam(value = "page")Integer page,
                                                   @RequestParam(value="pageSize")Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<ResponseExpenseRecord> responseExpenseRecords = expenseRecordService.listUserExpenseRecord(userId, page, pageSize);
        map.put("responseExpenseRecords",responseExpenseRecords);
        return map;
    }
}
