package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Member.MembercardRecord;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseOrder;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * 会员卡关系业务层接口
 * Created by Administrator on 2017/8/25.
 */
public interface IMembercardRelationService {

    /**
     * 根据id查询查询会员卡信息
     * @param id
     * @return
     */
    public MembercardRelation queryMembercardrelationById(String id);

    /**
     * 根据会员id查询查询会员卡列表
     *
     * @param memberid
     * @return
     */
    public List<MembercardRelation> queryMembercardrelationListByMemberId(String memberid) throws ParseException;

    /**
     * 会员卡充值
     *
     * @param responseOrder 订单号
     */
    public void memberCardRecharge(ResponseOrder responseOrder) throws ParseException;

    /**
     * 会员卡过户
     *
     * @param relationId
     * @param memberId
     */
    public void memberCardTransfer(String relationId, String memberId, String creator);

    /**
     * 停卡或启用或删除或请假
     *
     * @param relationId
     * @param status
     */
    public void memberCardStopOrStart(String relationId, String status, String creator);

    /**
     * 会员赠送
     *
     * @param relationId 会员卡关系表id
     * @param giveTimes  赠送次数/天数
     */
    public void memberCardGive(String relationId, String giveTimes, String creator) throws ParseException;

    /**
     * 换卡
     * @param responseOrder 订单号
     */
    public void memberCardChange(ResponseOrder responseOrder);

    /**
     * 开卡
     *
     * @param
     */
    public void addMemberCardRelation(ResponseOrder responseOrder) throws ParseException;

    /**
     * 更新会员卡信息
     * @param membercardrelation
     */
    public void updateMembercardRelation(MembercardRelation membercardrelation);

    /**
     * 查询门店中开卡信息
     *
     * @param
     * @return
     */
    public List<MembercardRelation> queryOpencardInfo(String storeId, Integer page, String queryParameter, String flagtimes,
                                                      String flagdays,
                                                      String membcardId) throws ParseException;

    /**
     * 校准
     * @param id  会员卡关系表id
     * @param validityType 是否开启有效期:1:是;2否
     * @param times 次卡变更次数数
     * @param validityTime 有效期时间
     * @param creator 创建人
     * @return
     */
    public void calibrationMembercardrelation(String id, String validityType, String times, String validityTime,String creator);


    /**
     * 获取刷卡记录
     * @param id 会员关系表id
     * @param page 当前页
     * @return
     */
    public MembercardRelation queryRecordListBycardId(String id ,  Integer page);

    /**
     * 会员卡是否有效
     * @param membercardRelationId
     * @return
     */
    public MembercardRelation isCardValid(String membercardRelationId,String classDate);

    /**
     * 获取需要激活的会员卡
     * @return
     */
    public List<MembercardRelation> getMembercardRelationActive();
    /**
     * 判断会员卡是否已激活
     * @param id
     * @return
     */
    public String isMemberCardActivate(String id);

    /**
     * 激活会员卡
     * @param id
     */
    public void activateMembercardRelation(String id );

    /**
     * 会员销假
     * @param id 会员卡关系表id
     */
    public void MembercardResumptionLeave(String id) throws ParseException;

    /**
     * 更新次卡的次数
     * @param id 会员卡关系表id
     * @param flage 更新类型 1:更新 give_times;2:更新times
     * @param times 要更新的次数
     */
    public void updateMembercardrelationTimes(String id,String flage,String times);
    /**
     * 获取刷卡记录
     * @param membercardRelationId 会员关系表id
     * @param page 当前页
     * @return
     */
    public List<MembercardRecord> listBusinessCardRecord(String membercardRelationId,String businessId, Integer page);
}
