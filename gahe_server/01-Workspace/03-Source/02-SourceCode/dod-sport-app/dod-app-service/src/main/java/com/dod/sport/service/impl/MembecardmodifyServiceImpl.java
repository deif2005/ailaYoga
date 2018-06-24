package com.dod.sport.service.impl;

import com.dod.sport.constant.MessageConstants;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IMembercardmodifyDao;
import com.dod.sport.dao.IMembercardRelationDao;
import com.dod.sport.dao.IRechargegradDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Member.*;
import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;
import com.dod.sport.service.*;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.MD5Utils;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/10/26.
 * 会员卡充值,变更业务实现层
 */
@Service
public class MembecardmodifyServiceImpl implements IMembecardmodifyService {
    @Autowired
    IMembercardmodifyDao membecardmodifyDao;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IClientUserService clientUserService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ISMSExampleService smsExampleService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMembercardRelationDao membercardrelationDao;
    @Autowired
    private IMemberCardService memberCardService;

    /**
     * 新增充值记录
     * @param membercardRechargeRecord
     */
    @Override
    public void addMembecardRechargeRecord(MembercardRechargeRecord membercardRechargeRecord) {
        //1.判断id是否为空,如果为空则生成id
        if (membercardRechargeRecord.getId()==null|| "".equals(membercardRechargeRecord.getId())){
            membercardRechargeRecord.setId(UUID.randomUUID().toString());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
        String dateStr = sdf.format(new Date());
        //2.从缓存中获取该卡的最大充值编号
        String rechargeSerialId = RedisCommon.getMaxMemberCardRechargeId(dateStr);
        if(rechargeSerialId == ""||rechargeSerialId==null){//如果为空,从数据库中获取该卡的最大充值编号,并设置到缓存当中
            String maxMemberechargeId = membecardmodifyDao.getRechargeIdMax(dateStr);
            if (maxMemberechargeId.equals("0")){
                maxMemberechargeId = dateStr+"000";
            }
            rechargeSerialId = RedisCommon.setAndReturnMaxMemberCardRechargeId(dateStr, maxMemberechargeId);
        }
        membercardRechargeRecord.setRechargeSerialId(rechargeSerialId);
        membecardmodifyDao.addMembecardRecharge(membercardRechargeRecord);
    }

    /**
     * 新增变更记录
     * @param membercardModify
     */
    @Override
    public void addMembercardmodify(MembercardModify membercardModify) {
        //1.判断id是否为空,如果为空则生成id
        if (membercardModify.getId()==null||"".equals( membercardModify.getId())){
            membercardModify.setId(UUID.randomUUID().toString());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
        String dateStr = sdf.format(new Date());
        String modifyId = RedisCommon.getMaxMembercardModifyId(dateStr);
        if(modifyId == ""||modifyId==null){
            String maxmodifyId = membecardmodifyDao.getModifyIdMax(dateStr);
            if (maxmodifyId.equals("0")){
                maxmodifyId = dateStr+"000";
            }
            modifyId = RedisCommon.setAndReturnMembercardModifyId(dateStr, maxmodifyId);
        }
        //编号20171114115100001
        membercardModify.setModifySerialId(modifyId);
        membecardmodifyDao.addMembercardModify(membercardModify);
    }

    /**
     * 更新变更记录
     * @param membercardModify
     */
    @Override
    public void updateMembercardModify(MembercardModify membercardModify) {
        membecardmodifyDao.updateMembercardModify(membercardModify);
    }

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
    @Override
    @Transactional
    public void addMemberCardRechargeOrder(String userId,String memberId,String membercardRelationId,String membercardId,String storeId,String months,String times,
                                           String nominalAmount,String creator,String orderStatus) {
        MembercardRelation membercardRelation=membercardrelationDao.queryMembercardrelationById(membercardRelationId);
        if(membercardRelation==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membercardnotexists.getValue());
        }
        //1:新增充值记录
        MembercardModify membercardModify =  new MembercardModify();
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setMembercardRelationId(membercardRelationId);
        membercardModify.setNewcardId(membercardId);
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_recharge.getValue());
        membercardModify.setMonths(months);
        membercardModify.setTimes(times);
        membercardModify.setStoreId(storeId);
        membercardModify.setMemberId(memberId);
        membercardModify.setNominalAmount(nominalAmount);
        membercardModify.setCreator(creator);
        this.addMembercardmodify(membercardModify);

        Date date= new Date();
        //2:新增订单记录
        //生成订单单号201711091806000000001
        Order order  = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderSerialId(orderService.getOrderSerialId(date));
        order.setMemberId(memberId);
        order.setTotalPrice(nominalAmount);
        order.setStoreId(storeId);
        order.setUserId(userId);
        order.setOrderStatus(orderStatus);
        orderService.addOrder(order);
        //3:新增销售记录
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setOrderId(order.getId());
        saleDetail.setProductId(membercardModify.getId());
        saleDetail.setProductPrice(nominalAmount);
        saleDetail.setProcuctCount("1");
        saleDetail.setDiscount("0");
        saleDetail.setAmountMoney(nominalAmount);
        saleDetail.setSaleType(CommonEnum.SaleDetail.saleType.sale_store_service.getValue());  //设置为店内服务
        orderService.addOrderDetail(saleDetail);


        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_change.getValue());

        String messageContent = messageService.getModifyRecordContent(membercardRecord, membercardRelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_SUBMITRECHANGEORDER_ALERT, messageContent, extrasMap, memberId);

    }

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
     * @param remark 开卡备注
     * @return
     */

    @Override
    @Transactional
    public void addOldMemberOpenCardOrder(String userId,String memberId,String membercardId,String storeId,String months,String times, String nominalAmount,
                                          String creator, String orderStatus, String validityType,String remark) {
        //1:生成开卡记录
        String membercardRelationId = UUID.randomUUID().toString();//生成会员卡关系id
        MembercardModify membercardModify =  new MembercardModify();
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setMemberId(memberId);
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_open.getValue());
        membercardModify.setRemark(remark);
        membercardModify.setCreator(creator);
        membercardModify.setMembercardRelationId(membercardRelationId);
        membercardModify.setStoreId(storeId);
        membercardModify.setValidityType(validityType);
        membercardModify.setNewcardId(membercardId);
        membercardModify.setMonths(months);
        membercardModify.setTimes(times);
        membercardModify.setNominalAmount(nominalAmount);
        this.addMembercardmodify(membercardModify);
        Date date= new Date();
        //2:新增订单记录
        //生成订单单号201711091806000000001
        Order order  = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderSerialId(orderService.getOrderSerialId(date));
        order.setMemberId(memberId);
        order.setTotalPrice(nominalAmount);
        order.setStoreId(storeId);
        order.setUserId(userId);
        order.setOrderStatus(orderStatus);
        orderService.addOrder(order);
        //3:新增销售记录
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setOrderId(order.getId());
        saleDetail.setProductId(membercardModify.getId());
        saleDetail.setProductPrice(nominalAmount);
        saleDetail.setProcuctCount("1");
        saleDetail.setDiscount("0");
        saleDetail.setAmountMoney(nominalAmount);
        saleDetail.setSaleType(CommonEnum.SaleDetail.saleType.sale_store_service.getValue());  //设置为店内服务
        orderService.addOrderDetail(saleDetail);

        MemberCard memberCard = memberCardService.getMemberCardInfo(membercardId);
        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_change.getValue());

        String messageContent = messageService.getModifyRecordContent(membercardRecord, memberCard.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_SUBMITOPENCARDORDER_ALERT, messageContent, extrasMap, memberId);

    }

    /**
     * 提交新会员开卡订单
     * @param businessId 商家id
     * @param phoneNum 会员电话
     * @param nickName 用户名称
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
    @Override
    @Transactional
    public void addNewMemberOpenCardOrder(String businessId, String phoneNum , String nickName, String sex, String birthday, String membercardId, String months,
                                          String times, String nominalAmount, String storeId, String orderStatus, String validityType, String creator, String remark) throws UnsupportedEncodingException {
        //1:先新增平台用户信息
        ClientUser clientUser = new ClientUser();
        clientUser.setPhoneNum(phoneNum);
        ClientUser getclientUser  = clientUserService.getClientUserInfo(clientUser);
        String userId = "";
        if (getclientUser ==null){//判断平台用户中是否已存在该用户
            String password = String.valueOf(((int)(Math.random() * 9 + 1) * 100000));//随机生成六位数密码
            userId = UUID.randomUUID().toString();
            clientUser.setId(userId);
            clientUser.setSex(sex);
            clientUser.setBirthday(birthday);
            clientUser.setNickName(nickName);
            clientUser.setPassword(MD5Utils.getMD5(password+phoneNum));
            clientUserService.addClientUser(clientUser);
            //消息推送给客户端提醒成为平台用户和开卡信息
            smsExampleService.sendSMSCode(phoneNum,password, MessageConstants.ADD_USER_MODE_ID);
        }else {
            userId = getclientUser.getId();
        }
        //2:新增会员信息
        BusinessMember businessMember = new BusinessMember();
        businessMember.setUserId(userId);
        businessMember.setId(UUID.randomUUID().toString());
        businessMember.setBusinessId(businessId);
        businessMember.setStoreId(storeId);
        businessMember.setCreator(creator);
        businessMember.setPhoneNum(phoneNum);
        memberService.addMemberInfo(businessMember);
        //3:提交开卡订单
        this.addOldMemberOpenCardOrder(userId, businessMember.getId(),membercardId,storeId,months,times,nominalAmount,
                 creator,orderStatus,validityType,remark);

    }

    /**
     * 提交换卡订单
     * @param months 充值月数
     * @param times 充值次数
     * @param oldMembcardId 原会员卡种id
     * @param newMembcardId 新会员卡种id
     * @param creator  创建人
     * @param discountPrice 折算价值(换卡折算)
     * @param priceSpread 差价(换卡补差价)
     * @param remainTimes 剩余次数/天数
     * @return
     */
    @Override
    @Transactional
    public void addMemberCardChangeOrder(String membercardRelationId,String userId,String memberId,String storeId,String months,String times,String nominalAmount, String oldMembcardId,
                                         String newMembcardId, String discountPrice,String priceSpread,String remainTimes,String creator,String orderStatus) {

        MembercardRelation membercardRelation=membercardrelationDao.queryMembercardrelationById(membercardRelationId);
        if(membercardRelation==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membercardnotexists.getValue());
        }
        //2:生成换卡记录
        MembercardModify membercardModify =  new MembercardModify();
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setMemberId(memberId);
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_change.getValue());
        membercardModify.setCreator(creator);
        membercardModify.setMembercardRelationId(membercardRelationId);
        membercardModify.setStoreId(storeId);
        membercardModify.setNewcardId(newMembcardId);
        membercardModify.setOldcardId(oldMembcardId);
        membercardModify.setResidueTimes(remainTimes);
        membercardModify.setMonths(months);
        membercardModify.setTimes(times);
        membercardModify.setNominalAmount(nominalAmount);
        this.addMembercardmodify(membercardModify);
        Date date= new Date();
        //3:新增订单记录
        //生成订单单号201711091806000000001
        Order order  = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderSerialId(orderService.getOrderSerialId(date));
        order.setMemberId(memberId);
        order.setTotalPrice(priceSpread);
        order.setStoreId(storeId);
        order.setUserId(userId);
        order.setOrderStatus(orderStatus);
        orderService.addOrder(order);
        //4:新增销售记录
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setOrderId(order.getId());
        saleDetail.setProductId(membercardModify.getId());
        saleDetail.setProductPrice(priceSpread);//设置单价
        saleDetail.setProcuctCount("1");//设置数量
        saleDetail.setDiscount("0");//设置折扣
        saleDetail.setAmountMoney(priceSpread);//总价
        saleDetail.setSaleType(CommonEnum.SaleDetail.saleType.sale_store_service.getValue()); //设置为店内服务
        orderService.addOrderDetail(saleDetail);

        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_change.getValue());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, membercardRelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_SUBMITCHANGESUCCEEDORDER_ALERT, messageContent, extrasMap, memberId);
    }

    /**
     * 获取用户的订单信息
     * @param orderSerialId 订单号
     * @param userId 用户id
     * @param orderStatus 订单状态:订单状态:(1未付款,2已付款,3取消交易)
     * @return
     */
    @Override
    public List<ResponseOrder> listMemberOrderInfo(String orderSerialId, String userId, String orderStatus,String modifyType,Integer page ) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        return membecardmodifyDao.listMemberOrderInfo(orderSerialId,userId, orderStatus,modifyType,batchDataPage.getOffset(), batchDataPage.getPagesize());
    }


}
