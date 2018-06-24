package com.dod.sport.service;

import com.dod.sport.domain.po.ExpenseRecord;
import com.dod.sport.domain.po.ResponseExpenseRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */
public interface IExpenseRecordService {

    /**
     * 新增消费记录
     * @param expenseRecord
     */
    public void addExpenseRecord(ExpenseRecord expenseRecord);

    /**
     * 获取用户的消费记录
     * @param userId 用户id
     * @param page   //开始页
     * @param pageSize //每页显示数据数量
     * @return
     */
    public List<ResponseExpenseRecord> listUserExpenseRecord(String userId, Integer page, Integer pageSize);
}
