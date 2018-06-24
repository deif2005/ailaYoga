package com.dod.sport.dao;

import com.dod.sport.domain.po.PayRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Description:
 * \支付信息
 */

@Repository
public interface IPayDao {


    /**
     * 添加已经支付信息
     * @return
     */
    public void addPayRecord(@Param("payRecord") PayRecord payRecord);

    /**
     * 获取已经支付信息记录
     * @return
     */
    public List<PayRecord> getPayRecordList(@Param("userId") String  userId);

}