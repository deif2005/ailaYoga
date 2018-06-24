package com.dod.sport.dao;

import com.dod.sport.domain.po.Member.MembercardRecord;
import com.dod.sport.domain.po.Member.MembercardRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 会员卡关系dao
 * Created by Administrator on 2017/8/25.
 */
@Repository
public interface IMembercardRelationDao {
    /**
     * 根据id查询查询会员卡信息
     * @param id
     * @return
     */
    public MembercardRelation queryMembercardrelationById(String id);

    /**
     * 根据会员id查询查询会员卡列表
     * @param memberid 会员id
     * @return
     */
    public List<MembercardRelation> queryMembercardrelationListByMemberId(String memberid);

    /**
     * 会员卡充值
     * @param membercardrelation
     */
    public void memberCardRecharge( MembercardRelation membercardrelation);

    /**
     * 会员卡过户
     * @param membercardrelation
     */
    public void memberCardTransfer(MembercardRelation membercardrelation);

    /**
     * 停卡或启用
     * @param membercardrelation
     */
    public void memberCardStopOrStart(MembercardRelation membercardrelation);

    /**
     * * 会员赠送
     * @param membercardrelation
     */
    public void memberCardGive(MembercardRelation membercardrelation);

    /**
     * 换卡
     * @param membercardrelation
     */
    public void memberCardChange(MembercardRelation membercardrelation);

    /**
     * 开卡
     * @param membercardrelation
     */
    public void addMemberCardRelation(MembercardRelation membercardrelation);

    /**
     * 会员卡激活
     * @param id 会员卡关系id
     */
    public void actMemebercardRelation(String id);

    /**
     * 更新会员卡信息
     * @param membercardrelation
     */
    public void updateMembercardRelation(MembercardRelation membercardrelation);

    /**
     * 获取会员卡的最大编号
     * @param membcardId
     * @return
     */
    public String getMaxMembercardOpencardSerialId(String membcardId);

    /**
     * 查询门店中开卡信息
     * @param storeId 门店id
     * @param startPage
     * @param endPage
     * @param queryParameter 请求参数
     * @param flagtimes 根据次数查询
     * @param flagdays 根据剩余天数查询
     * @param membcardId 根据会员卡id查询
     * @return
     */
    public List <MembercardRelation>queryOpencardInfo( @Param("storeId")String storeId ,
                                                       @Param("startPage") Integer startPage,
                                                       @Param("endPage") Integer endPage,
                                                       @Param("queryParameter")String queryParameter,
                                                       @Param("flagtimes")Integer flagtimes,
                                                       @Param("flagdays")Integer flagdays,
                                                       @Param("membcardId")String membcardId);

    /**
     * 获取刷卡记录
     * @param membercardRelationId 会员卡关系表id
     * @param startPage
     * @param endPage
     * @return
     */
    public List<MembercardRecord>queryRecordListBycardId(@Param("membercardRelationId")String membercardRelationId ,
                                                         @Param("startPage") Integer startPage,
                                                         @Param("endPage") Integer endPage);

    /**
     * 获取刷卡记录
     * @param membercardRelationId 会员卡关系表id
     * @param businessId 会员卡关系表id
     * @param startPage
     * @param endPage
     * @return
     */
    public List<MembercardRecord>listBusinessCardRecord(@Param("membercardRelationId")String membercardRelationId ,
                                                           @Param("businessId")String businessId ,
                                                           @Param("startPage") Integer startPage,
                                                           @Param("endPage") Integer endPage);

    /**
     * 获取最先需要激活的会员卡
     * @return
     */
    public List<MembercardRelation> getMembercardRelationActive();

    /**
     * 判断会员卡是否已激活
     * @param id 会员卡关系表id
     * @return
     */
    public String isMemberCardActivate(String id);

    /**
     * 激活会员卡
     * @param id 会员卡关系表id
     * @param actTime 激活时间
     */
    public void activateMembercardRelation(@Param("id")String id,@Param("actTime")String actTime);

    /**
     * 会员销假
     * @param id
     * @param validityTime
     * @param cardStatus
     */
    public void MembercardResumptionLeave(@Param("id")String id,
                                          @Param("validityTime")String validityTime,
                                          @Param("cardStatus")String cardStatus);

    /**
     * 更新次卡的次数
     * @param id 会员卡关系表id
     * @param flage 更新类型 1:更新 give_times;2:更新times
     * @param times 要更新的次数
     */
    public void updateMembercardrelationTimes(@Param("id")String id,
                                              @Param("flage")String flage,
                                              @Param("times")String times);

    /**
     * 获取刷卡记录
     * @param id 会员卡关系表id
     * @return
     */
    public List<MembercardRecord>getBusinessCardRecordList(@Param("id")String id ,
                                                           @Param("businessId") Integer businessId,
                                                           @Param("startPage") Integer startPage,
                                                           @Param("endPage") Integer endPage);
}
