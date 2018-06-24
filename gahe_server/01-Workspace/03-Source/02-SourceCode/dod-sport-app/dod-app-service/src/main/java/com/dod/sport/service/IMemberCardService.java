package com.dod.sport.service;

import com.dod.sport.domain.po.Member.MembercardRechargeRecord;
import com.dod.sport.domain.po.Member.MemberCard;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.domain.po.Member.MemberCardDetail;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by defi on 2017-08-21.
 */
@Service
public interface IMemberCardService {

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
    public List<MemberCard>listMemberCardInfoByType(String businessId, Integer membcardType);

    /**
     * 获取会员卡信息
     * @return
     */
    public MemberCard getMemberCardInfo(String id);

    /**
     * 新增会员卡信息
     * @param memberCard
     */
    public MemberCard addMemberCard(MemberCard memberCard);

    /**
     * 删除会员卡
     * @param id
     */
    public void delMembercardById(String id);

    /**
     * 编辑会员卡
     * @param memberCard
     */
    public MemberCard updateMembercard(MemberCard memberCard);

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
    public List<StoreInfo>queryStoreInfoList(String businessId ,String membcardId);

    /**
     * 新增会员卡与门店关联
     * @param storeIdStr
     * @param memcardId
     */
    public void addMembercardStoreRelation(String storeIdStr ,String memcardId);

    /**
     * 根据用户获取所有会员卡信息
     * @param phoneNum
     * @param businessId
     * @return
     */
    public List<MemberCard> getUserMemberCardListById( String phoneNum,String businessId);

    /**
     * 会员卡详情信息
     * @param id
     * @return
     */
    public MemberCardDetail getMemberCardDetail( String id);

    /**
     * 获取会员卡充值记录信息
     * @param membercardRechargeRecord
     * @return
     */
    public  List<MembercardRechargeRecord> getMemberRecharegeRecord( MembercardRechargeRecord membercardRechargeRecord);
}
