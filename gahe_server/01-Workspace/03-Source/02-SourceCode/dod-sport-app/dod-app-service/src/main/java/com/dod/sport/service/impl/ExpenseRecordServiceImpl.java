package com.dod.sport.service.impl;

import com.dod.sport.dao.IExpenseRecordDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.po.ExpenseRecord;
import com.dod.sport.domain.po.ResponseExpenseRecord;
import com.dod.sport.service.IExpenseRecordService;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */
@Service
public class ExpenseRecordServiceImpl implements IExpenseRecordService {

    @Autowired
    IExpenseRecordDao expenseRecordDao;
    /**
     * 新增消费记录
     * @param expenseRecord
     * 消费编号(格式:20171027143111000000001)
     */
    @Override
    public void addExpenseRecord(ExpenseRecord expenseRecord) {
        DateFormat df=new SimpleDateFormat("yyyyMMddkkmmss");
        String dateTimeStr = df.format(new Date());
        String expenseRecordId = RedisCommon.getMaxExpenseRecordId(dateTimeStr);
        if (expenseRecordId ==null ||"".equals(expenseRecordId )){
            String maxExpenseRecordId = expenseRecordDao.getMaxExponseRecordId(dateTimeStr);
            expenseRecordId = RedisCommon.setAndReturnMaxClientUserId(maxExpenseRecordId);
        }
       // expenseRecord.setExpenseSerialId(expenseRecordId);
        expenseRecordDao.addExpenseRecord(expenseRecord);
    }

    /**
     * 获取用户的消费记录
     * @param userId 用户id
     * @param page   开始页
     * @param pageSize 每页显示数据数量
     * @return
     */
    @Override
    public List<ResponseExpenseRecord> listUserExpenseRecord(String userId, Integer page, Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        if (pageSize !=null && pageSize !=0){
            batchDataPage.setPagesize(pageSize);
        }

        List<ResponseExpenseRecord> list =expenseRecordDao.listUserExpenseRecord(userId,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        if (list!=null && list.size()>0){
            for (ResponseExpenseRecord responseExpenseRecord:list){
                responseExpenseRecord.setPayDateTimeStr(responseExpenseRecord.getTheYear()+"年"+responseExpenseRecord.getTheMonth()+"月"+
                                                        responseExpenseRecord.getTheDay()+"日"+responseExpenseRecord.getTheTime());
                responseExpenseRecord.setPayMonTimeStr(responseExpenseRecord.getTheMonth()+"月"+
                        responseExpenseRecord.getTheDay()+"日"+responseExpenseRecord.getTheTime());
            }
        }
        return list;
    }
}
