package com.dod.sport.controller;


import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Member.*;
import com.dod.sport.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
@RequestMapping(value = "api/cardRelation/v1")
public class MembercardrelationController extends  BaseController {
    @Autowired
    private IMembercardRelationService membercardRelationService;
    @Autowired
    IMemberService memberService;
    @Autowired
    IMemberCardService memberCardService;
    @Autowired
    IClientUserService clientUserService;

    /**
     * 获取会员的会员卡列表
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMembercardrelationListByMemberId", method = RequestMethod.POST)
    public  Map<String,Object> queryMembercardrelationListByMemberId(@RequestParam("memberId") final String memberId) throws ParseException {
        Map<String,Object>map = new HashMap<>();
        List<MembercardRelation>list = membercardRelationService.queryMembercardrelationListByMemberId(memberId);
        map.put("membercardrelationList",list);
        return map;
    }

    /**
     * 会员卡过户
     * @param relationId 会员关系卡id-
     * @param memberId 会员id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberCardTransfer", method = RequestMethod.POST)
    public String memberCardTransfer(@RequestParam("relationId") final String relationId,
                                     @RequestParam("creator") final String creator,
                                     @RequestParam("memberId") final String memberId){

        membercardRelationService.memberCardTransfer(relationId, memberId, creator);
        return "";

    }

    /**
     * 会员卡启用或停止
     * @param relationId 会员关系卡id
     * @param chooseType 1:启用;2:停止
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberCardStopOrStart", method = RequestMethod.POST)
    public String memberCardStopOrStart(@RequestParam("relationId") final String relationId,
                                        @RequestParam("creator") final String creator,
                                        @RequestParam("chooseType") final String chooseType){
         membercardRelationService.memberCardStopOrStart(relationId, chooseType, creator);
        //推送消息


         return "";

    }

    /**
     * 会员卡赠送
     * @param relationId
     * @param giveTimes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberCardGive", method = RequestMethod.POST)
    public String memberCardGive(@RequestParam("relationId") final String relationId,
                                 @RequestParam("creator") final String creator,
                                 @RequestParam("giveTimes") final String giveTimes) throws ParseException {
        membercardRelationService.memberCardGive(relationId, giveTimes, creator);
        return "";

    }

    /**
     * 查询门店中开卡信息
     * @param storeId 门店id
     * @param page 当前页
     * @param queryParameter 请求参数
     * @param flagtimes 按剩余次数查询
     * @param flagdays 按剩余天数查询
     * @param membcardId 会员卡id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryOpencardInfo", method = RequestMethod.POST)
    public Map<String,Object>queryOpencardInfo(@RequestParam(value ="storeId") final String storeId,
                                                @RequestParam(value = "page") final Integer page,
                                                @RequestParam(value ="queryParameter", required = false) final String queryParameter,
                                                @RequestParam(value ="flagtimes", required = false)String flagtimes,
                                                @RequestParam(value ="flagdays", required = false)String flagdays,
                                                @RequestParam(value ="membcardId", required = false)String membcardId) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        List<MembercardRelation> membercardrelationList = membercardRelationService.queryOpencardInfo(storeId,page,queryParameter,flagtimes,flagdays,membcardId);
        map.put("membercardrelationList",membercardrelationList);
        return  map;
    }

    /**
     * 会员关系表校准
     * @param relationId 关系表id
     * @param validityType 是否开启有效期:1是;2否
     * @param times 剩余次数
     * @param validityTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/calibrationMembercardrelation", method = RequestMethod.POST)
    public String calibrationMembercardrelation(@RequestParam(value ="relationId") final String relationId,
                                                @RequestParam(value ="validityType", required = false) final String validityType,
                                                @RequestParam(value ="times", required = false) final String times,
                                                @RequestParam(value ="validityTime", required = false) final String validityTime,
                                                @RequestParam(value ="creator") final String creator){
        membercardRelationService.calibrationMembercardrelation(relationId, validityType, times, validityTime, creator);
        return "";
    }

    /**
     * 查询刷卡记录
     * @param relationId 会员关系表id
     * @param page 当前页
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryRecordListBycardId", method = RequestMethod.POST)
    public Map<String,Object> queryRecordListBycardId(@RequestParam(value ="relationId") final String relationId,
                                                      @RequestParam(value ="page") final Integer page){
        Map<String,Object> map = new HashMap<>();
        MembercardRelation membercardRelation = membercardRelationService.queryRecordListBycardId(relationId, page);
        map.put("membercardRelation",membercardRelation);
        return map;
    }

    /**
     * 获取卡卷记录
     * @param relationId
     * @param businessId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusinessCardRecord", method = RequestMethod.POST)
    public Map<String,Object>listBusinessCardRecord(@RequestParam(value ="relationId") final String relationId,
                                                       @RequestParam(value ="businessId",required = false) final String businessId,
                                                       @RequestParam(value ="page") final Integer page){
        Map<String,Object> map = new HashMap<>();
        List <MembercardRecord> MembercardRecords = membercardRelationService.listBusinessCardRecord(relationId, businessId, page);
        map.put("MembercardRecords",MembercardRecords);
        return map;
    }

}
