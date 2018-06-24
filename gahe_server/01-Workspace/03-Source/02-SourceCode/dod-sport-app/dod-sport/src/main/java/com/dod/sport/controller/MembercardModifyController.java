package com.dod.sport.controller;

import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.service.IMembecardmodifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
@RequestMapping("api/modify/v1")
public class MembercardModifyController {

    @Autowired
    IMembecardmodifyService membecardmodifyService;

    /**
     * 提交会员卡充值订单
     * @param membercardRelationId 会员卡关系id
     * @param months 充值月数
     * @param times 充值次数
     * @param nominalAmount 充值金额
     * @param storeId 门店id
     * @param memberId 会员id
     * @param userId 用户id
     * @param orderStatus 订单状态(1未付款,2已付款,3取消交易)
     * @param creator 创建人
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberCardRechargeOrder", method = RequestMethod.POST)
    public String  addMemberCardRechargeOrder(@RequestParam(value ="membercardRelationId") final String membercardRelationId,
                                              @RequestParam(value ="membercardId") final String membercardId,
                                              @RequestParam(value ="months",required = false) final String months,
                                              @RequestParam(value ="times",required = false) final String times,
                                              @RequestParam(value ="nominalAmount") final String nominalAmount,
                                              @RequestParam(value ="storeId") final String storeId,
                                              @RequestParam(value ="memberId") final String memberId,
                                              @RequestParam(value ="userId") final String userId,
                                              @RequestParam(value ="orderStatus") final String orderStatus,
                                              @RequestParam(value = "creator",required = false) final String creator){
        membecardmodifyService.addMemberCardRechargeOrder(userId,memberId,membercardRelationId,membercardId,storeId,months,times,nominalAmount,creator,orderStatus);
        return "";
    }

    /**
     * 提交老会员开卡订单
     * @param membercardId 会员卡种id
     * @param months 充值月数
     * @param times  充值次数
     * @param nominalAmount 充值金额
     * @param storeId  门店id
     * @param memberId 会员id
     * @param userId 用户id
     * @param orderStatus 订单状态(1未付款,2已付款,3取消交易)
     * @param validityType 次卡是否开启有效期:1:是;2:否
     * @param creator 创建人
     * @param remark 开卡备注
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOldMemberOpenCardOrder", method = RequestMethod.POST)
    public String  addOldMemberOpenCardOrder(@RequestParam(value = "membercardId") final String membercardId,
                                             @RequestParam(value = "months",required = false) final String months,
                                             @RequestParam(value = "times",required = false) final String times,
                                             @RequestParam(value = "nominalAmount") final String nominalAmount,
                                             @RequestParam(value = "storeId") final String storeId,
                                             @RequestParam(value = "memberId") final String memberId,
                                             @RequestParam(value = "userId") final String userId,
                                             @RequestParam(value = "orderStatus") final String orderStatus,
                                             @RequestParam(value = "validityType",required = false) final String validityType,
                                             @RequestParam(value = "creator",required = false) final String creator,
                                             @RequestParam(value = "remark",required = false) final String remark){
        membecardmodifyService.addOldMemberOpenCardOrder(userId,memberId,membercardId,storeId,months,times,nominalAmount,
                                                         creator,orderStatus,validityType,remark);
        return "";
    }

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
    @ResponseBody
    @RequestMapping(value = "/addNewMemberOpenCardOrder", method = RequestMethod.POST)
    public String  addNewMemberOpenCardOrder(@RequestParam(value ="businessId") final String businessId,
                                             @RequestParam(value ="phoneNum") final String phoneNum,
                                             @RequestParam(value ="nickName") final String nickName,
                                             @RequestParam(value ="sex") final String sex,
                                             @RequestParam(value ="birthday") final String birthday,
                                             @RequestParam(value ="membercardId") final String membercardId,
                                             @RequestParam(value ="months",required = false) final String months,
                                             @RequestParam(value ="times",required = false) final String times,
                                             @RequestParam(value ="nominalAmount") final String nominalAmount,
                                             @RequestParam(value ="storeId") final String storeId,
                                             @RequestParam(value ="orderStatus") final String orderStatus,
                                             @RequestParam(value = "validityType",required = false) final String validityType,
                                             @RequestParam(value = "creator",required = false) final String creator,
                                             @RequestParam(value = "remark",required = false) final String remark) throws UnsupportedEncodingException {
        membecardmodifyService.addNewMemberOpenCardOrder(businessId,phoneNum,nickName,sex,birthday,membercardId,months,
                                                         times,nominalAmount,storeId,orderStatus,validityType,creator,remark);
        return "";
    }

    /**
     * 提交换卡订单
     * @param membercardRelationId 会员卡关系id
     * @param memberId 会员id
     * @param userId 用户id
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
    @ResponseBody
    @RequestMapping(value = "/addMemberCardChangeOrder", method = RequestMethod.POST)
    public String  addMemberCardChangeOrder(  @RequestParam(value ="membercardRelationId") final String membercardRelationId,
                                              @RequestParam(value ="userId") final String userId,
                                              @RequestParam(value ="memberId") final String memberId,
                                              @RequestParam(value ="storeId") final String storeId,
                                              @RequestParam(value ="months",required = false) final String months,
                                              @RequestParam(value ="times",required = false) final String times,
                                              @RequestParam(value ="nominalAmount") final String nominalAmount,
                                              @RequestParam(value ="oldMembcardId") final String oldMembcardId,
                                              @RequestParam(value ="newMembcardId") final String newMembcardId,
                                              @RequestParam(value ="creator",required = false) final String creator,
                                              @RequestParam(value ="discountPrice") final String discountPrice,
                                              @RequestParam(value ="priceSpread") final String priceSpread,
                                              @RequestParam(value ="remainTimes") final String remainTimes,
                                              @RequestParam(value ="orderStatus") final String orderStatus ){
        membecardmodifyService.addMemberCardChangeOrder(membercardRelationId,userId,memberId,storeId,months,times,nominalAmount,oldMembcardId,newMembcardId,
                                                        discountPrice,priceSpread,remainTimes,creator,orderStatus);
        return "";
    }

    /**
     * 获取用户的订单信息
     * @param orderSerialId 订单号
     * @param userId 用户id
     * @param orderStatus 订单状态:订单状态:(1未付款,2已付款,3取消交易)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberOrderInfo", method = RequestMethod.POST)
    public Map<String,Object> listMemberOrderInfo(  @RequestParam(value ="userId",required = false) final String userId,
                                                    @RequestParam(value ="orderSerialId",required = false) final String orderSerialId,
                                                    @RequestParam(value ="orderStatus",required = false) final String orderStatus,
                                                    @RequestParam(value ="modifyType",required = false) final String modifyType,
                                                    @RequestParam("page") Integer page){
        Map<String,Object> map = new HashMap<>();
        List<ResponseOrder> responseOrderList = membecardmodifyService.listMemberOrderInfo(orderSerialId, userId, orderStatus, modifyType,page);
        map.put("responseOrderList",responseOrderList);
        return map;
    }
}
