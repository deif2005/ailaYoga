package com.dod.sport.dao;

import com.dod.sport.domain.po.Member.MembercardModify;
import com.dod.sport.domain.po.Member.MembercardRechargeRecord;
import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
@Repository
public interface IMembercardmodifyDao {
    /**
     * 获取会员卡充值最大编号
     * @param dateStr
     * @return
     */
    public String getRechargeIdMax(String dateStr);

    /**
     * 新增充值记录
     * @param cardrecharge
     */
    public void addMembecardRecharge(MembercardRechargeRecord cardrecharge);

    /**
     * 获取会员卡变更最大编号
     * @param dateStr
     * @return
     */
    public String getModifyIdMax(@Param("dateStr")String dateStr);

    /**
     * 新增变更记录
     * @param membercardModify
     */
    public void addMembercardModify(MembercardModify membercardModify);

    /**
     * 更新变更记录
     * @param membercardModify
     */
    public void updateMembercardModify(MembercardModify membercardModify);

    /**
     * 获取用户的订单信息
     * @param orderSerialId 订单号
     * @param userId 用户ID
     * @param orderStatus 订单状态:订单状态:(1未付款,2已付款,3取消交易)
     * @return
     */
    public List<ResponseOrder>listMemberOrderInfo(@Param("orderSerialId")String orderSerialId,
                                                  @Param("userId")String userId,
                                                  @Param("orderStatus")String orderStatus,
                                                  @Param("modifyType")String modifyType,
                                                  @Param("startPage")Integer startPage,
                                                  @Param("endPage")Integer endPage);


}
