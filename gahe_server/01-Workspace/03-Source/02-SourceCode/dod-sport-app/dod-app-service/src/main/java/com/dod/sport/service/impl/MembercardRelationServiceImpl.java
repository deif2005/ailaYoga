package com.dod.sport.service.impl;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IMembercardRelationDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Member.*;
import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.service.*;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.DateUtil;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会员卡关系业务层实现类
 * Created by Administrator on 2017/8/25.
 */
@Service
public class MembercardRelationServiceImpl implements IMembercardRelationService {
    @Autowired
    private IMembercardRelationDao membercardrelationDao;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IMembecardmodifyService membecardmodifyService;
    @Autowired
    private IMessageService messageService;

    /**
     * 根据id查询查询会员关系卡信息
     *
     * @param id
     * @return
     */
    @Override
    public MembercardRelation queryMembercardrelationById(String id) {
        return membercardrelationDao.queryMembercardrelationById(id);
    }

    /**
     * 根据会员id查询查询会员关系卡列表
     *
     * @param memberid
     * @return
     */
    @Override
    public List<MembercardRelation> queryMembercardrelationListByMemberId(String memberid) throws ParseException {
        List<MembercardRelation> list = membercardrelationDao.queryMembercardrelationListByMemberId(memberid);
        DateFormat df = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
        String nowDate = df.format(new Date());
        if (list != null && list.size() > 0) {
            for (MembercardRelation membercardrelation : list) {
                if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
                    String validaityTime = membercardrelation.getValidityTime();
                    //算出还剩多少天到期
                    if (validaityTime != null) {
                        long days = BusiUtils.getDays(nowDate, validaityTime.substring(0,19));
                        if (days >= 0) {
                            membercardrelation.setDays(String.valueOf(days));
                        }
                    }
                }else if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())){
                    Integer theTimes =0;
                    Integer giveTimes = 0;
                    if(membercardrelation.getTimes()!=null ){
                        theTimes = Integer.valueOf(membercardrelation.getTimes()) ;
                    }
                    if (membercardrelation.getGiveTimes()!=null){
                        giveTimes = Integer.valueOf(membercardrelation.getGiveTimes());
                    }
                    Integer sumTimes =theTimes+giveTimes;
                    membercardrelation.setTimes(String.valueOf(sumTimes));
                }
            }
        }
        return list;
    }

    /**
     * 会员卡充值
     *
     * @param responseOrder
     */
    @Override
    @Transactional
    public void memberCardRecharge(ResponseOrder responseOrder) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
        MembercardRelation membercardrel = new MembercardRelation();
        Calendar cal = Calendar.getInstance();
        //会员关系表信息
        MembercardRelation membercardRelation = membercardrelationDao.queryMembercardrelationById(responseOrder.getMembercardRelationId());
        if (responseOrder.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
            String validityTime = membercardRelation.getValidityTime();//获取期限卡的有效期时间
            if (validityTime == null || "".equals(validityTime) || DateUtil.isAfterNow(sdf.parse(validityTime))) {//会员卡已过期,从当前时间时间开始算
                cal.setTime(new Date());
            } else {//会员卡未过期
                cal.setTime(DateUtil.parseDateTime(validityTime));
            }
            cal.add(cal.MONDAY, Integer.parseInt(responseOrder.getMonths()));
            membercardrel.setValidityTime(sdf.format(cal.getTime()));
        } else if (membercardRelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())) {//次卡
            if (membercardRelation.getTimes() !=null && membercardRelation.getTimes() !=""){
                membercardrel.setTimes(String.valueOf(Integer.parseInt(responseOrder.getTimes()) + Integer.parseInt(membercardRelation.getTimes())));
            }else {
                membercardrel.setTimes(String.valueOf(Integer.parseInt(responseOrder.getTimes())));
            }
            if (membercardRelation.getValidityType() !=null && membercardRelation.getValidityType() != "" &&
                    membercardRelation.getValidityType().equals(CommonEnum.Membercardrelation.validityType.validityType_yes)) {//如果该次卡已开启有效期则从今天开始往后算一年的有效期
                cal.add(cal.YEAR, SysConfigConstants.TIMESCARD_VALIDITYTIME_YEAR_LENGTH_1);//设置有效期为一年
                membercardrel.setValidityTime(sdf.format(cal.getTime()));
            }
        }
        membercardrel.setId(responseOrder.getMembercardRelationId());
        membercardrelationDao.memberCardRecharge(membercardrel);
        //推送充值成功的消息
        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(responseOrder.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, membercardRelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_RECHANGESUCCEED_ALERT, messageContent, extrasMap, membercardRelation.getMemberId());
    }

    /**
     * 会员卡过户
     *
     * @param relationId 会员关系卡id
     * @param memberId   会员id
     */
    @Override
    @Transactional
    public void memberCardTransfer(String relationId, String memberId, String creator) {
        MembercardRelation membercardrelation = new MembercardRelation();
        membercardrelation.setId(relationId);
        membercardrelation.setMemberId(memberId);
        membercardrelationDao.memberCardTransfer(membercardrelation);
        membercardrelation = membercardrelationDao.queryMembercardrelationById(relationId);
        //生成会员变更记录
        MembercardModify membercardModify = new MembercardModify();
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setMembercardRelationId(relationId);
        membercardModify.setMemberId(memberId);
        membercardModify.setStoreId(membercardrelation.getStoreId());
        membercardModify.setModifyTime(DateUtil.getDateTime());
        membercardModify.setCreator(creator);
        membercardModify.setNewcardId(membercardrelation.getMembcardId());
        membercardModify.setStatus(CommonEnum.Membercardmodify.status.status_finished.getValue());
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_transfer.getValue());
        membecardmodifyService.addMembercardmodify(membercardModify);

        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(membercardModify.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, membercardrelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_TRANSFERSUCCEED_ALERT, messageContent, extrasMap, membercardrelation.getMemberId());
    }

    /**
     * 停卡/启用/删除会员卡
     *
     * @param relationId 会员关系卡id
     * @param status     状态:1.正常,2.停卡,3.删除
     */
    @Override
    @Transactional
    public void memberCardStopOrStart(String relationId, String status, String creator) {
        MembercardRelation membercardrelation = new MembercardRelation();
        membercardrelation.setCardStatus(status);
        membercardrelation.setId(relationId);
        membercardrelationDao.memberCardStopOrStart(membercardrelation);
        MembercardRelation cardrelation = membercardrelationDao.queryMembercardrelationById(relationId);
        //生成会员变更记录
        MembercardModify membercardModify = new MembercardModify();
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setMembercardRelationId(relationId);
        membercardModify.setMemberId(cardrelation.getMemberId());
        membercardModify.setNewcardId(cardrelation.getMembcardId());
        membercardModify.setStoreId(membercardrelation.getStoreId());
        membercardModify.setCreator(creator);
        membercardModify.setStatus(CommonEnum.Membercardmodify.status.status_finished.getValue());
        membercardModify.setModifyTime(DateUtil.getDateTime());
        String alert = "";
        if (Integer.parseInt(status) == 1) {//启用
            membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_start.getValue());
            alert =SysConfigConstants.MEMBERCARD_STARTSUCCEED_ALERT;
        } else if (Integer.parseInt(status) == 2) {//停卡
            membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_stop.getValue());
            alert = SysConfigConstants.MEMBERCARD_STOPCARDSUCCEED_ALERT;
        } else if (Integer.parseInt(status) == 3) {//删除
            membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_del.getValue());
            alert = SysConfigConstants.MEMBERCARD_DELCARDSUCCEED_ALERT;
        }
        membecardmodifyService.addMembercardmodify(membercardModify);
        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(membercardModify.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, cardrelation.getMembcardType());

       //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, alert, messageContent, extrasMap, cardrelation.getMemberId());
    }

    /**
     * 赠送
     * @param relationId 会员卡关系表id
     * @param giveTimes  赠送次数/天数
     */
    @Override
    @Transactional
    public void memberCardGive(String relationId, String giveTimes, String creator) throws ParseException {
        MembercardRelation membercardrelation = new MembercardRelation();
        MembercardRelation cardrelation = membercardrelationDao.queryMembercardrelationById(relationId);
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
        Calendar cal = Calendar.getInstance();
        if (cardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
            String validityTime=null ;
            if (cardrelation.getValidityTime()!=null ){
                 validityTime = cardrelation.getValidityTime().substring(0,19);//获取期限卡的有效期时间
            }
            if (validityTime == null || "".equals(validityTime) || DateUtil.isBeforeNow(sdf.parse(validityTime))) {//会员卡已过期,从当前时间时间开始算
                cal.setTime(new Date());
            } else {//会员卡未过期
                cal.setTime(DateUtil.parseDateTime(validityTime));
            }
            cal.add(cal.DAY_OF_MONTH, Integer.parseInt(giveTimes));
            membercardrelation.setValidityTime(sdf.format(cal.getTime()));
        } else if (cardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())) {//次卡
            membercardrelation.setGiveTimes(String.valueOf(Integer.valueOf(cardrelation.getGiveTimes())+Integer.valueOf(giveTimes)));
        }
        membercardrelation.setId(relationId);
        membercardrelationDao.memberCardGive(membercardrelation);
        //生成变更记录
        MembercardModify membercardModify = new MembercardModify();
        membercardModify.setGiveTimes(giveTimes);
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setModifyTime(DateUtil.getDateTime());
        membercardModify.setMemberId(cardrelation.getMemberId());
        membercardModify.setMembercardRelationId(relationId);
        membercardModify.setStoreId(cardrelation.getStoreId());
        membercardModify.setNewcardId(cardrelation.getMembcardId());
        membercardModify.setCreator(creator);
        membercardModify.setStatus(CommonEnum.Membercardmodify.status.status_finished.getValue());
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_give.getValue());
        membecardmodifyService.addMembercardmodify(membercardModify);

        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(membercardModify.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, cardrelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_GIVESUCCEED_ALERT, messageContent, extrasMap, cardrelation.getMemberId());
    }

    /**
     * 换卡
     * @param responseOrder
     */
    @Override
    @Transactional
    public void memberCardChange(ResponseOrder responseOrder) {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
        MembercardRelation queryMembercardrelation = membercardrelationDao.queryMembercardrelationById(responseOrder.getMembercardRelationId());
        MembercardRelation membercardrelation = new MembercardRelation();
        Calendar cal = Calendar.getInstance();
        if (responseOrder.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
            cal.add(cal.MONTH, Integer.parseInt(responseOrder.getMonths()));//从当前开始算期限卡的有效期时间
            membercardrelation.setValidityTime(sdf.format(cal.getTime())); //设置会员卡有效期时间
        } else if (responseOrder.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())) {//次卡
            membercardrelation.setTimes(responseOrder.getTimes());
            if (queryMembercardrelation.getValidityType().equals(CommonEnum.Membercardrelation.validityType.validityType_yes.getValue())) {//如果该次卡已开启有效期则从今天开始往后算一年的有效期
                cal.add(cal.YEAR, SysConfigConstants.TIMESCARD_VALIDITYTIME_YEAR_LENGTH_1);//设置有效期为一年
                membercardrelation.setValidityTime(sdf.format(cal.getTime()));
            }
        }
        membercardrelation.setId(responseOrder.getMembercardRelationId());
        membercardrelation.setMembcardId(responseOrder.getNewcardId());
        membercardrelation.setFlagType(CommonEnum.Membercardrelation.flageType.changed.getValue());//设置为已换卡标记
        membercardrelationDao.memberCardChange(membercardrelation);

        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(responseOrder.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, responseOrder.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_CHANGESUCCEED_ALERT, messageContent, extrasMap, queryMembercardrelation.getMemberId());
    }

    /**
     * 开卡
     *
     * @param responseOrder
     */
    @Override
    @Transactional
    public void addMemberCardRelation(ResponseOrder responseOrder) throws ParseException {
        MembercardRelation membercardRelation = new MembercardRelation();
        membercardRelation.setId(responseOrder.getMembercardRelationId());
        //1:卡流水号设置
        String maxId = RedisCommon.getMaxMemberCardNum(responseOrder.getNewcardId());
        if ("".equals(maxId)) {
            maxId = RedisCommon.setAndReturnMaxMemberCardNum(responseOrder.getNewcardId(),
                    membercardrelationDao.getMaxMembercardOpencardSerialId(responseOrder.getNewcardId()));// 刷新最新会员卡编号，并返回
        }
        //2:开卡
        membercardRelation.setOpencardSerialId(maxId);
        String specact_time = BusiUtils.getNextDate(new Date(), 1, SysConfigConstants.DATE_FORMAT_FORDATETIME);
        membercardRelation.setSpecactTime(specact_time);
        membercardRelation.setMemberId(responseOrder.getMemberId());
        membercardRelation.setStoreId(responseOrder.getStoreId());
        membercardRelation.setValidityType(responseOrder.getValidityType());
        membercardRelation.setMembcardId(responseOrder.getNewcardId());
        membercardRelation.setOpencardDate(DateUtil.getFormateDate(SysConfigConstants.DATE_FORMAT_FORDATETIME));
        membercardrelationDao.addMemberCardRelation(membercardRelation);
        this.memberCardRecharge(responseOrder);

        //推送开卡成功的消息
        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(responseOrder.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, responseOrder.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAlias(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_OPENCARDSUCCEED_ALERT, messageContent, extrasMap, responseOrder.getMemberId());
    }

    /**
     * 更新会员卡信息
     * @param membercardrelation
     */
    @Override
    public void updateMembercardRelation(MembercardRelation membercardrelation) {
        membercardrelationDao.updateMembercardRelation( membercardrelation);
    }

    /**
     * 获取门店开卡信息
     *
     * @param storeId
     * @param page
     * @param queryParameter 会员名称 or 会员卡号 or 会员电话
     * @return
     */
    @Override
    public List<MembercardRelation> queryOpencardInfo(String storeId, Integer page, String queryParameter, String flagtimes,
                                                      String flagdays, String membcardId) throws ParseException {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        DateFormat df = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
        String nowDate = df.format(new Date());
        Integer times;
        Integer theDays;
        //times /thdays =2时是无效的
        if (flagtimes == null || "".equals(flagtimes)) {
            times = 2;
        } else {
            times = Integer.parseInt(flagtimes);
        }
        if (flagdays == null || "".equals(flagdays)) {
            theDays = 2;
        } else {
            theDays = Integer.parseInt(flagdays);
        }
        List<MembercardRelation> list = membercardrelationDao.queryOpencardInfo(storeId, batchDataPage.getOffset(), batchDataPage.getPagesize(), queryParameter, times, theDays, membcardId);
        if (list != null && list.size() > 0) {
            for (MembercardRelation membercardrelation : list) {
                if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
                    String validaityTime = membercardrelation.getValidityTime();
                    //算出还剩多少天到期
                    if (validaityTime != null) {
                        long days = BusiUtils.getDays(nowDate, validaityTime.substring(0,19));
                        if (days >= 0) {
                            membercardrelation.setDays(String.valueOf(days));
                        }
                    }
                }else if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())){
                    Integer theTimes =0;
                    Integer giveTimes = 0;
                    if(membercardrelation.getTimes()!=null ){
                        theTimes = Integer.valueOf(membercardrelation.getTimes()) ;
                    }
                    if (membercardrelation.getGiveTimes()!=null){
                        giveTimes = Integer.valueOf(membercardrelation.getGiveTimes());
                    }
                    Integer sumTimes =theTimes+giveTimes;
                    membercardrelation.setTimes(String.valueOf(sumTimes));
                }
            }
        }
        return list;
    }

    /**
     * 校准
     *
     * @param membercardrelationId 会员卡关系id
     * @param validityType         次卡是否开启有效期:1:是;2:否
     * @param newTimes             校准校准后的次数/天数
     * @param newValidityTime      校准后的有效期时间
     * @return
     */
    @Override
    @Transactional
    public void calibrationMembercardrelation(String membercardrelationId, String validityType, String newTimes, String newValidityTime, String creator) {
        MembercardRelation membercardrelation = new MembercardRelation();
        MembercardModify membercardModify = new MembercardModify();
        //获取会员卡关系表信息
        MembercardRelation cardrelation = membercardrelationDao.queryMembercardrelationById(membercardrelationId);
        membercardrelation.setId(membercardrelationId);
        if (validityType != null) {
            membercardrelation.setValidityType(validityType);
        }
        if (newTimes != null && !"".equals(newTimes) && Integer.parseInt(newTimes) != 0) {
            membercardrelation.setTimes(newTimes); //更新校准后的次数
            membercardModify.setOldTimes(cardrelation.getTimes());//记录校准前的次数
            membercardModify.setNewTimes(newTimes); //记录校准后的次数
        }
        if (newValidityTime != null) {//判断校准后的有效期时间
            membercardrelation.setValidityTime(newValidityTime); //设置校准后的有效期时间
            membercardModify.setOldValidityTime(cardrelation.getValidityTime()); //记录校准前的有效期时间
            membercardModify.setNewValidityTime(newValidityTime); //记录校准后的有效期时间
        }
        membercardrelationDao.memberCardChange(membercardrelation);
        //生成变更记录
        membercardModify.setId(UUID.randomUUID().toString());
        membercardModify.setModifyType(CommonEnum.Membercardmodify.modifyType.modify_cali.getValue());
        membercardModify.setCreator(creator);
        membercardModify.setStoreId(cardrelation.getStoreId());
        membercardModify.setMembercardRelationId(membercardrelationId);
        membercardModify.setNewcardId(cardrelation.getMembcardId());
        membecardmodifyService.addMembercardmodify(membercardModify);

        //推送开卡成功的消息
        MembercardRecord membercardRecord = new MembercardRecord();
        membercardRecord.setModifyType(membercardModify.getModifyType());
        String messageContent = messageService.getModifyRecordContent(membercardRecord, cardrelation.getMembcardType());
        //消息推送
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAlias(SysConfigConstants.MEMBERCARD_MODIFY_TITLE, SysConfigConstants.MEMBERCARD_CALIBRATIONSUCCEED_ALERT, messageContent, extrasMap, cardrelation.getMemberId());
    }

    /**
     * 获取刷卡记录
     * @param id   会员关系表id
     * @param page 当前页
     * @return
     */
    @Override
    public MembercardRelation queryRecordListBycardId(String id, Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        MembercardRelation membercardRelation = this.queryMembercardrelationById(id);
        if(membercardRelation==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        List<MembercardRecord> membercardRecords = membercardrelationDao.queryRecordListBycardId(id, batchDataPage.getOffset(), batchDataPage.getPagesize());
        if (membercardRelation != null && membercardRecords!=null &&membercardRecords.size()>0) {
            String orderStatus ="";
            String contentStr = "";
            for (MembercardRecord membercardRecord:membercardRecords){
                 orderStatus = membercardRecord.getOrderStatus();
                if ( orderStatus !=null || "".equals(orderStatus)){//会员卡约课记录
                    contentStr =messageService.getOrderCouseRecordContent(orderStatus,membercardRecord.getEmployeeName(),membercardRecord.getCourseName());
                }else{//会员卡变更记录
                    contentStr = messageService.getModifyRecordContent(membercardRecord,membercardRelation.getMembcardType());
                }
                membercardRecord.setContentStr(contentStr);
            }
        }
        membercardRelation.setMembercardRecords(membercardRecords);
        return membercardRelation;
    }

    /**
     * 获取刷卡记录
     * @param id   会员关系表id
     * @param page 当前页
     * @return
     */
    @Override
    public List<MembercardRecord>listBusinessCardRecord(String id,String businessId,Integer page){
            BatchDataPage batchDataPage=new BatchDataPage();
            batchDataPage.setPage(page);
            List<MembercardRecord>membercardRecords=membercardrelationDao.listBusinessCardRecord(id, businessId, batchDataPage.getOffset(), batchDataPage.getPagesize());
        if (membercardRecords!=null &&membercardRecords.size()>0) {
            String contentStr = "";
            for (MembercardRecord membercardRecord:membercardRecords){
                 contentStr = messageService.getModifyRecordContent(membercardRecord,membercardRecord.getMembcardType());
                 membercardRecord.setContentStr(contentStr);
            }
        }
         return membercardRecords;
     }

    /**
     * 会员卡是否有效
     *
     * @param membercardRelationId
     * @param classDate
     * @return
     */
    public MembercardRelation isCardValid(String membercardRelationId,String classDate){
            MembercardRelation membercardRelation=membercardrelationDao.queryMembercardrelationById(membercardRelationId);
            if(membercardRelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())){//次卡
                if(Integer.parseInt(membercardRelation.getTimes())==0){//已无剩余次数
                     throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cardtimesisinvalid.getValue());
                }
                if(Integer.parseInt(membercardRelation.getValidityType())==1){//次卡开启有效期
                    if(DateUtil.isBefore(DateUtil.parse(membercardRelation.getValidityTime()),DateUtil.parse(classDate))){
                         throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cardisover.getValue());
                    }
                 }
            }else{//卡是否过期
                if(DateUtil.isBefore(DateUtil.parse(membercardRelation.getValidityTime()),DateUtil.parse(classDate))){
                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cardisover.getValue());
                }
            }
            //停卡
            if(Integer.parseInt(membercardRelation.getCardStatus())>1){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cardstatusisstop.getValue());
            }
            return membercardRelation;
    }

    /**
     * 获取最先需要激活的会员卡
     *
     * @return
     */
    @Override
    public List<MembercardRelation> getMembercardRelationActive(){
            return membercardrelationDao.getMembercardRelationActive();
     }

    /**
     * 判断会员卡是否已激活 1:未激活;2:已激活
     * @param id 会员关系表id
     * @return
     */
    @Override
    public String isMemberCardActivate(String id){
            return membercardrelationDao.isMemberCardActivate(id);
            }

    /**
     * 激活会员卡
     *
     * @param id 会员关系表id
     */
    @Override
    public void activateMembercardRelation(String id){
            SimpleDateFormat df=new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);//设置日期格式
            String actTime=df.format(new Date());//激活时间
            membercardrelationDao.activateMembercardRelation(id,actTime);
        //f推送消息
    }

    /**
     * 会员销假
     *
     * @param id 会员卡关系表id
     */
    @Override
    public void MembercardResumptionLeave(String id)throws ParseException{
            MembercardRelation membercardRelation=membercardrelationDao.queryMembercardrelationById(id);
            if(membercardRelation==null){
                   throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
            }
            List<MembercardRelation>list=membercardrelationDao.queryMembercardrelationListByMemberId(membercardRelation.getMemberId());
            if(list==null&&list.size()<1){
                   throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
            }
            MemberLeave memberLeave=new MemberLeave();
            memberLeave.setMemberId(membercardRelation.getMemberId());
            memberLeave.setApproveStatus("2");
            MemberLeave queryMemberLeave=memberService.getMemberLeaveById(memberLeave);
            String starTime=queryMemberLeave.getStartTime();
            SimpleDateFormat df=new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);//设置日期格式
            SimpleDateFormat df1=new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);//设置日期格式
            String nowTime=df1.format(new Date());//激活时间
            int days=BusiUtils.getDays(starTime,nowTime);//请假几天
            Calendar cal=Calendar.getInstance();
            String validityTime="";
            for(MembercardRelation cardRelation:list){
                if(cardRelation.getValidityType().equals("1")){
                    cal.setTime(df.parse(cardRelation.getValidityTime()));//将会员卡的有效期时间设置进去
                    cal.add(cal.DAY_OF_MONTH,days);
                    membercardrelationDao.MembercardResumptionLeave(cardRelation.getId(),df.format(cal.getTime()),CommonEnum.Membercardrelation.cardStatus.cardStatus_normal.getValue());
                }else{
                    membercardrelationDao.MembercardResumptionLeave(cardRelation.getId(),validityTime,CommonEnum.Membercardrelation.cardStatus.cardStatus_normal.getValue());
                }
            }
            memberService.deleteMemberApprove(queryMemberLeave);
    }

    /**
     * 更新次卡的次数
     * @param id    会员卡关系表id
     * @param flage 更新类型 1:更新 give_times;2:更新times
     * @param times 要更新的次数
     */
    @Override
    public void updateMembercardrelationTimes(String id,String flage,String times){
            membercardrelationDao.updateMembercardrelationTimes(id,flage,times);
     }

}
