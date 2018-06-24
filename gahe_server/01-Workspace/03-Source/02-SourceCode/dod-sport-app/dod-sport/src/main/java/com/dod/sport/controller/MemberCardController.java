package com.dod.sport.controller;

import com.dod.sport.dao.IMemberCardDao;
import com.dod.sport.domain.po.Member.*;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.service.IMemberCardService;
import com.dod.sport.service.IMembercardRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by defi on 2017-08-21.
 * 会员卡controller
 */
@Controller
@RequestMapping(value = "api/memberCard/v1")
public class MemberCardController {

    @Autowired
    IMemberCardService memberCardService;
    @Autowired
    IMemberCardDao memberCardDao;

    @Autowired
    private IMembercardRelationService membercardRelationService;

    /**
     * 获取会员卡列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberCard", method = RequestMethod.POST)
    public Map<String,Object> listMemberCard(@RequestParam("businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<MemberCard> memberCardList = memberCardService.listMemberCardInfo(businessId);
        map.put("memberCardList",memberCardList);
        return map;
    }
    /**
     * 根据卡类型获取会员卡列表(用于换卡时展示会员卡列表)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberCardByType", method = RequestMethod.POST)
    public Map<String,Object> listMemberCardByType(@RequestParam("membcardType") final Integer membcardType,
                                                   @RequestParam("businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<MemberCard> memberCardList = memberCardService.listMemberCardInfoByType(businessId, membcardType);
        map.put("memberCardList",memberCardList);
        return map;
    }

    /**
     * 获取会员卡信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberCard", method = RequestMethod.POST)
    public Map<String,Object> getMemberCard(@RequestParam("id") final String id){
        Map<String,Object> map = new HashMap<>();
        MemberCard memberCard = memberCardService.getMemberCardInfo(id);
        map.put("memberCard",memberCard);
        return map;
    }

    /**
     * 新增会员卡信息
     * @param businessId
     * @param membcardName
     * @param membcardType
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberCard", method = RequestMethod.POST)
    public Map<String,Object> addMemberCard(@RequestParam(value = "businessId") final String businessId,
                                            @RequestParam(value = "membcardName") final String membcardName,
                                            @RequestParam(value = "membcardType") final String membcardType,
                                            @RequestParam(value = "orderCourseType") final String orderCourseType,
                                            @RequestParam(value = "creator") final String creator,
                                            @RequestParam(value = "remark",required = false) final String remark){
        MemberCard memberCard = new MemberCard();
        memberCard.setBusinessId(businessId);
        memberCard.setMembcardName(membcardName);
        memberCard.setCreator(creator);
        memberCard.setRemark(remark);
        memberCard.setMembcardType(membcardType);
        memberCard.setOrderCourseType(orderCourseType);
        MemberCard card =  memberCardService.addMemberCard(memberCard);
        Map<String,Object> map = new HashMap<>();
        map.put("memberCard",card);
        return map;
    }

    /**
     * 根据id删除会员卡
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delMembercardById", method = RequestMethod.POST)
    public String delMembercardById(@RequestParam("id") final String id){
        MemberCard memberCard = memberCardService.getMemberCardInfo(id);
        memberCardService.delMembercardById(id);
        return "";
    }

    /**
     * 编辑会员卡
     * @param membcardName
     * @param membcardType
     * @param remark
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMembercard", method = RequestMethod.POST)
    public Map<String,Object> updateMembercard( @RequestParam(value = "membcardName") final String membcardName,
                                    @RequestParam(value = "membcardType") final String membcardType,
                                    @RequestParam(value = "orderCourseType") final String orderCourseType,
                                    @RequestParam(value = "remark") final String remark,
                                    @RequestParam(value = "id") final String id) {
        Map<String,Object> map = new HashMap<>();
        MemberCard memberCard = new MemberCard();
        memberCard.setId(id);
        memberCard.setMembcardType(membcardType);
        memberCard.setRemark(remark);
        memberCard.setMembcardName(membcardName);
        memberCard.setOrderCourseType(orderCourseType);
        MemberCard card = memberCardService.updateMembercard(memberCard);
        map.put("memberCard",card);
        return map;
    }

    /**
     * 获取获取会员卡持卡人数信息
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMembercardSum", method = RequestMethod.POST)
    public Map<String,Object> queryMembercardSum(@RequestParam("businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<MemberCard> memberCardList = memberCardService.queryMembercardSum(businessId);
        map.put("memberCardList",memberCardList);
        return map;
    }

    /**
     * 获取商家门店信息以及会员卡关联门店信息列表
     * @param businessId 商家id
     * @param membercardId 会员卡id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryStoreInfoList", method = RequestMethod.POST)
    public Map<String,Object> queryStoreInfoList(@RequestParam("businessId") final String businessId ,
                                                 @RequestParam("membercardId") final String membercardId){
        Map<String,Object> map = new HashMap<>();
        List<StoreInfo> storeInfoListlist = memberCardService.queryStoreInfoList(businessId, membercardId);
        map.put("storeInfoListlist",storeInfoListlist);
        return map;
    }

    /**
     * 会员卡关联门店
     * @param storeIdStr 门店idist
     * @param membercardId  会员卡id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMembercardStoreRelationList", method = RequestMethod.POST)
    public String addMembercardStoreRelationList(@RequestParam("storeIdStr") final String storeIdStr,
                                                  @RequestParam("membercardId") final String membercardId){
        memberCardService.addMembercardStoreRelation(storeIdStr,membercardId);
        return "";
    }

    /**
     * 获取关联该门店的会员卡列表
     * @param stordId
     * @param membcardType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMembercardListByStoreId", method = RequestMethod.POST)
    public Map<String,Object> queryMembercardListByStoreId(@RequestParam("stordId") final String stordId,
                                                           @RequestParam(value = "membcardType",required = false) final String membcardType){
        List<MemberCard> MemberCardList = memberCardDao.queryMembercardListByStoreId(stordId, membcardType);
        Map<String,Object> map = new HashMap<>();
        map.put("MemberCardList",MemberCardList);
        return map;
    }

    /**
     * 根据用户获取所有会员卡信息
     * @return
     *  @param phoneNum
     *  @param businessId
     */
    @ResponseBody
    @RequestMapping(value = "/getUserMemberCardListById", method = RequestMethod.POST)
    public Map<String,Object> getUserMemberCardListById(@RequestParam("phoneNum") final String phoneNum,
                                                        @RequestParam(value = "businessId",required = false) final String businessId){
        List<MemberCard> MemberCardList = memberCardService.getUserMemberCardListById(phoneNum, phoneNum);
        Map<String,Object> map = new HashMap<>();
        map.put("MemberCardList", MemberCardList);
        return map;
    }

    /**
     * 会员卡详情信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberCardDetail", method = RequestMethod.POST)
    public Map<String,Object> getMemberCardDetail(@RequestParam("id") final String id){
        MemberCardDetail memberCardDetail = memberCardService.getMemberCardDetail(id);
        Map<String,Object> map = new HashMap<>();
        map.put("memberCardDetail", memberCardDetail);
        return map;
    }

    /**
     * 获取会员卡充值记录信息
     * @param id
     * @param memberId
     * @param membrelationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberRecharegeRecord", method = RequestMethod.POST)
    public Map<String,Object> getMemberRecharegeRecord(@RequestParam(value ="memberId" ,required = false) final String memberId,
                                                       @RequestParam(value ="id" ,required = false) final String id,
                                                       @RequestParam(value ="membrelationId" ,required = false) final String membrelationId){
        MembercardRechargeRecord membercardRechargeRecord = new MembercardRechargeRecord();
        membercardRechargeRecord.setId(id);
        List<MembercardRechargeRecord> membercardRechargeRecordList = memberCardService.getMemberRecharegeRecord(membercardRechargeRecord);
        Map<String,Object> map = new HashMap<>();
        map.put("memberCardDetail", membercardRechargeRecordList);
        return map;
    }

    /**
     * 获取会员卡充值记录信息
     * @param id
     * @param membrelationId
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBusinessCardRecordList", method = RequestMethod.POST)
    public Map<String,Object> getBusinessCardRecordList(@RequestParam(value ="id" ,required = false) final String id,
                                                        @RequestParam(value ="membrelationId" ,required = false) final String membrelationId,
                                                       @RequestParam(value ="storeId" ,required = false) final String storeId){

        MembercardRechargeRecord membercardRechargeRecord = new MembercardRechargeRecord();
        membercardRechargeRecord.setId(id);
        membercardRechargeRecord.setStoreId(storeId);
        List<MembercardRechargeRecord> membercardRechargeRecordList = memberCardService.getMemberRecharegeRecord(membercardRechargeRecord);
        Map<String,Object> map = new HashMap<>();
        map.put("memberCardDetail", membercardRechargeRecordList);
        return map;
    }
}
