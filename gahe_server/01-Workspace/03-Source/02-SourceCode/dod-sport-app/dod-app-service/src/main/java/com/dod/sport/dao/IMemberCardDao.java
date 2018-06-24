package com.dod.sport.dao;

import com.dod.sport.domain.po.Member.*;
import com.dod.sport.domain.po.Base.StoreInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by defi on 2017-08-21.
 * 会员考dao层
 */
@Repository
public interface IMemberCardDao {
    /**
     * 获取会员列表
     * @return
     */
    public List<MemberCard> listMemberCardInfo(String businessId);

    /**
     * 根据类型获取会员卡列表
     * @param membcardType 会员卡类型
     * @return
     */
    public List<MemberCard>listMemberCardInfoByType(@Param("businessId") String businessId,
                                                    @Param("membcardType") Integer membcardType);

    /**
     * 获取会员卡信息
     * @return
     */
    public MemberCard getMemberCardInfo(String id);

    /**
     * 新增会员卡信息
     * @param memberCard
     */
    public void addMemberCard(MemberCard memberCard);

    /**
     * 获取最大会员卡类型编号
     * @return
     */
    public String getMaxMemberCardTypeId();

    /**
     * 获取最大会员卡编号
     * @param businessId
     * @return
     */
    public String getMaxMemberCardId(String businessId);

    /**
     * 删除会员卡
     * @param id
     */
    public void delMembercardById(String id);

    /**
     * 编辑会员卡
     * @param memberCard
     */
    public void updateMembercard(MemberCard memberCard);

    /**
     * 获取获取会员卡持卡人数信息
     * @param businessId
     * @return
     */
    public List<MemberCard>queryMembercardSum(String businessId);

    /**
     * 获取商家门店以及会员卡关联的门店信息
     * @param businessId
     * @return
     */
    public List<StoreInfo>queryStoreInfoList(@Param("businessId") String businessId ,
                                             @Param("membcardId")String membcardId);

    /**
     * 获取会员卡关联门店列表
     * @param membcardId
     * @return
     */
    public List<Memcardstorerelation> queryMembercardStoreRelationListByCardId(String membcardId);

    /**
     * 删除关联门店
     * @param id
     */
    public void delMemcardstorerelation(String id);

    /**
     * 新增会员卡与门店关联
     * @param memcardstorerelation
     */
    public void addMembercardStoreRelation(Memcardstorerelation memcardstorerelation);

    /**
     * 获取关联该门店的会员卡列表
     * @param stordId
     * @param membcardType
     * @return
     */
    public List<MemberCard>queryMembercardListByStoreId(@Param("stordId")String stordId,
                                                        @Param("membcardType")String membcardType);

    /**
     * 根据用户获取所有会员卡信息
     * @param phoneNum
     * @param businessId
     */
    public  List<MemberCard> getUserMemberCardListById(@Param("phoneNum") String phoneNum,
                                                       @Param("businessId") String businessId);


    /**
     * 会员卡详情信息
     */
    public MemberCardDetail getMemberCardDetail(String id);

    /**
     * 获取会员卡充值记录信息
     * @return
     */
    public List<MembercardRechargeRecord> getMemberRecharegeRecord( MembercardRechargeRecord membercardRechargeRecord);


}
