package com.dod.sport.service.impl;

import com.dod.sport.dao.IMemberCardDao;
import com.dod.sport.domain.po.Member.*;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.service.IMemberCardService;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by defi on 2017-08-21.
 * 会员卡管理service
 */

@Service
public class MemberCardServiceImpl implements IMemberCardService{

    @Autowired
    private IMemberCardDao memberCardDao;

    /**
     * 获取会员列表
     * @return
     */
    @Override
    public List<MemberCard> listMemberCardInfo(String businessId){
        List<MemberCard> memberCardList = memberCardDao.listMemberCardInfo(businessId);

        return memberCardList;
    }

    /**
     * 根据类型获取会员卡列表
     * @param membcardType 会员卡类型
     * @return
     */
    @Override
    public List<MemberCard> listMemberCardInfoByType(String businessId, Integer membcardType) {
        List<MemberCard> memberCardList = memberCardDao.listMemberCardInfoByType(businessId, membcardType);
        return memberCardList;
    }

    /**
     * 获取会员卡信息
     * @return
     */
    @Override
    public MemberCard getMemberCardInfo(String id){
        MemberCard memberCard = memberCardDao.getMemberCardInfo(id);
        return memberCard;
    }

    /**
     * 新增会员卡信息
     * @param memberCard
     */
    @Override
    public MemberCard addMemberCard(MemberCard memberCard){
        String maxid = RedisCommon.getMaxMemberCardId(memberCard.getMembcardSerialId());
        String id = UUID.randomUUID().toString() ;
        memberCard.setId(id);
        if ("".equals(maxid)){
            maxid = RedisCommon.setAndReturnMaxMemberCardId(memberCard.getMembcardSerialId(),memberCardDao.getMaxMemberCardId(memberCard.getMembcardSerialId()));
        }
        memberCard.setMembcardSerialId(maxid);
        memberCardDao.addMemberCard(memberCard);
        MemberCard card = this.getMemberCardInfo(id);
        return card;
    }

    /**
     * 删除会员卡
     * @param id
     */
    @Override
    public void delMembercardById(String id) {
        memberCardDao.delMembercardById(id);
    }

    /**
     * 编辑会员卡
     * @param memberCard
     */
    @Override
    public MemberCard updateMembercard(MemberCard memberCard) {
        memberCardDao.updateMembercard(memberCard);
        return this.getMemberCardInfo(memberCard.getId());
    }

    /**
     * 获取获取会员卡持卡人数信息
     * @param businessId
     * @return
     */
    @Override
    public List<MemberCard> queryMembercardSum(String businessId) {
        return memberCardDao.queryMembercardSum(businessId);
    }

    /**
     * 获取商家门店信息以及会员卡关联门店信息列表
     * @param businessId
     * @param membcardId
     * @return
     */
    @Override
    public List<StoreInfo> queryStoreInfoList(String businessId, String membcardId) {
        return memberCardDao.queryStoreInfoList(businessId,membcardId);
    }

    /**
     * 关联门店
     * @param storeIdStr
     * @param memcardId
     */
    @Override
    public void addMembercardStoreRelation(String storeIdStr, String memcardId) {
        String [] storeIdArr = storeIdStr.split(",");
        Memcardstorerelation memcardstorerelation =  new Memcardstorerelation();
        memcardstorerelation.setMembcardId(memcardId);
        //1查询该会员卡关联了那些门店
        List<Memcardstorerelation>list = memberCardDao.queryMembercardStoreRelationListByCardId(memcardId);
        //2.删除所有关联门店
        if (list !=null &&list.size()>0){
            for (Memcardstorerelation resultMemcardstorerelation:list){
                memberCardDao.delMemcardstorerelation(resultMemcardstorerelation.getId());
            }
        }
        //3.新增要关联的门店
        for (String stordId :storeIdArr){
            memcardstorerelation.setId(UUID.randomUUID().toString());
            memcardstorerelation.setStoreId(stordId);
            memberCardDao.addMembercardStoreRelation(memcardstorerelation);
        }
    }

    /**
     * 根据用户获取所有会员卡信息
     * @param phoneNum
     * @param businessId
     */
    @Override
    public  List<MemberCard> getUserMemberCardListById(String phoneNum,String businessId) {

        return memberCardDao.getUserMemberCardListById(phoneNum,businessId);
    }

    /**
     * 会员卡详情信息

     */
    @Override
    public MemberCardDetail getMemberCardDetail(String id) {

        return memberCardDao.getMemberCardDetail(id);
    }


    /**
     * 获取会员卡充值记录信息
     * @return
     */
    @Override
    public List<MembercardRechargeRecord> getMemberRecharegeRecord( MembercardRechargeRecord membercardRechargeRecord) {

        return memberCardDao.getMemberRecharegeRecord(membercardRechargeRecord);
    }
}
