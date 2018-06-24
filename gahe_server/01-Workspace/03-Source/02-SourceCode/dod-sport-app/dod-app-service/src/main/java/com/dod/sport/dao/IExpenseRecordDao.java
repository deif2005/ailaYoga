package com.dod.sport.dao;

import com.dod.sport.domain.po.ExpenseRecord;
import com.dod.sport.domain.po.ResponseExpenseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 * 消费记录dao
 */
@Repository
public interface IExpenseRecordDao {

    /**
     * 新增消费记录
     * @param expenseRecord
     */
   public void addExpenseRecord(ExpenseRecord expenseRecord);

    /**
     * 获取用户的消费记录
     * @param userId    用户id
     * @param startPage 开始行
     * @param endPage
     * @return
     */
   public List<ResponseExpenseRecord> listUserExpenseRecord(@Param("userId") String userId ,
                                                            @Param("startPage") Integer startPage,
                                                            @Param("endPage") Integer endPage);

    /**
     * 获取消费记录最大编号
     * @param dateTimeStr
     * @return
     */
   public String getMaxExponseRecordId(String dateTimeStr);
}
