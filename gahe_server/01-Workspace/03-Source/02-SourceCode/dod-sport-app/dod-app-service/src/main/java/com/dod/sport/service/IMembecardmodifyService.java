package com.dod.sport.service;

import com.dod.sport.domain.po.Member.MembercardRechargeRecord;
import com.dod.sport.domain.po.Member.MembercardModify;
import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */
public interface IMembecardmodifyService {


    /**
     * 新增充值记录
     * @param cardrecharge
     */
    public void addMembecardRechargeRecord(MembercardRechargeRecord cardrecharge);

    /**
     * 新增变更记录
     * @param membercardModify
     */
    public void addMembercardmodify(MembercardModify membercardModify);

    /**
     * 更新变更记录
     * @param membercardModify
     */
    public void updateMembercardModify(MembercardModify membercardModify);


    /**
     * 提交会员卡充值订单
     * @param memberId 会员id
     * @param membercardRelationId 会员卡关系id
     * @param storeId 门店id
     * @param months 充值月数
     * @param times 充值次数
     * @param nominalAmount 充值金额
     * @param creator 创建人
     */
    public void addMemberCardRechargeOrder(String userId,String memberId,String membercardRelationId,String membercardId,String storeId,String months,String times,String nominalAmount,String creator,String orderStatus);

    /**
     * 提交老会员开卡订单
     * @param membercardId 会员卡种id
     * @param months 充值月数
     * @param times  充值次数
     * @param nominalAmount 充值金额
     * @param storeId  门店id
     * @param memberId 会员id
     * @param orderStatus 订单状态(1未付款,2已付款,3取消交易)
     * @param validityType 次卡是否开启有效期:1:是;2:否
     * @param creator 创建人
     * @param  remark 开卡备注
     * @return
     */
    public void addOldMemberOpenCardOrder( String userId,String memberId,String membercardId,String storeId,String months,String times,String nominalAmount,String creator,String orderStatus,String validityType,String remark);

    /**
     * 提交新会员开卡订单
     * @param businessId 商家id
     * @param phoneNum 会员电话
     * @param nickName 昵称
     * @param sex 性别:1:男;2:女
     * @param birthday 生日
     * @param membercardId  会员卡种id
     * @param months 充值月数
     * @param times 充值次数
     * @param nominalAmount 充值金额
     * @param storeId 门店id
     * @param orderStatus 订单状态(1未付款,2已付款,3取消交易)
     * @param validityType 次卡是否开启有效期:1:是;2:否
     * @param creator 创建人
     * @param remark 开卡备注
     * @return
     */
    public void addNewMemberOpenCardOrder(String businessId,String phoneNum,String nickName,String sex,String birthday,String membercardId,String months,String times,String nominalAmount,String storeId,
                                          String orderStatus,String validityType,String creator,String remark) throws UnsupportedEncodingException;

    /**
     * 提交换卡订单
     * @param membercardRelationId 会员卡关系id
     * @param memberId 会员id
     * @param storeId 门店id
     * @param months 充值月数
     * @param times 充值次数
     * @param nominalAmount 充值金额
     * @param oldMembcardId 原会员卡种id
     * @param newMembcardId 新会员卡种id
     * @param creator  创建人
     * @param discountPrice 折算价值(换卡折算)
     * @param priceSpread 差价(换卡补差价)
     * @param remainTimes 剩余次数/天数
     * @param orderStatus 订单状态(1未付款,2已付款,3取消交易)
     * @return
     */
    public void addMemberCardChangeOrder(String membercardRelationId, String userId,String memberId,String storeId,String months,String times,String nominalAmount, String oldMembcardId,String newMembcardId,
                                         String discountPrice,String priceSpread,String remainTimes,String creator,String orderStatus);

    /**
     * 获取用户的订单信息
     * @param orderSerialId 订单号
     * @param userId 用户id
     * @param orderStatus 订单状态:订单状态:(1未付款,2已付款,3取消交易)
     * @return
     */
    public List<ResponseOrder>listMemberOrderInfo(String orderSerialId,String userId,String orderStatus,String modifyType,Integer page );

}
