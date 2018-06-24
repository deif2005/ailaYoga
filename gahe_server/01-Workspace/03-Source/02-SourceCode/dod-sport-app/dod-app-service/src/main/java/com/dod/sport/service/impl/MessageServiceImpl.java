package com.dod.sport.service.impl;

import com.dod.sport.constant.MessageConstants;
import com.dod.sport.dao.ICourseDao;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.dao.IMessageDao;
import com.dod.sport.domain.po.Course.CoursePlan;
import com.dod.sport.domain.po.Member.MembercardRecord;
import com.dod.sport.domain.po.SystemNotice;
import com.dod.sport.jgpush.Jgpushtool;
import com.dod.sport.jgpush.Jpush;
import com.dod.sport.redis.IRedisRepository;
import com.dod.sport.service.IMessageService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * Created by defi on 2017-08-25.
 * 消息推送服务service
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    IRedisRepository redisRepository;

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    IMessageDao messageDao;

    @Autowired
    ICourseDao courseDao;

    /**
     * 保存并发送--系统通知(广播)
     * @param title
     * @param alert
     * @param content
     */
    @Override
    public void sendSystemNotice(String title, String alert, String content){
        String messageId = saveNoticeInfo(title, alert, content,
                CommonEnum.Notice.noticeType.systemNotice.getValue());
        sendMessageToAllBusiness(title, alert, content);
        addIsReadToRedisByEmpStatus(messageId);
    }

    /**
     * 发送联盟消息--发送至所有商家
     * @param title
     * @param alert
     * @param content
     */
    @Override
    public void sendLeagueNotice(String title, String alert, String content){
        String messageId = saveNoticeInfo(title, alert, content,
                CommonEnum.Notice.noticeType.leagueNotice.getValue());
        sendMessageToAllBusiness(title, alert, content);
        addIsReadToRedisByEmpStatus(messageId);

    }

    /**
     * 发送内部消息--发送至指定tags
     * @param title
     * @param alert
     * @param content
     */
    @Override
    public void sendInnerNoticeByTags(String title, String alert, String content, String... storeIds){
        String messageId = saveNoticeInfo(title, alert, content,
                CommonEnum.Notice.noticeType.innerNotice.getValue());
        sendMessageToTags(title, alert, content, null, storeIds);
        addIsReadToRedisByTags(messageId, storeIds);

    }

    /**
     * 发送内部消息--发送至指定aliases
     * @param title
     * @param alert
     * @param content
     */
    @Override
    public void sendInnerNotice(String title, String alert, String content, String... employeeIds){
        String messageId = saveNoticeInfo(title, alert, content,
                CommonEnum.Notice.noticeType.innerNotice.getValue());
        sendMessageToAlias(title,alert,content,null,employeeIds);
        addIsReadToRedisByAliases(messageId, employeeIds);
    }

    /**
     * 群发通知所有设备
     * @param title
     * @param content
     */
    @Override
    public void sendMessageToAllBusiness(String title, String alert, String content){
        Jgpushtool.setTitle(title);
        Jgpushtool.setMsgContent(content);
        Jgpushtool.setAlert(alert);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_android_alertWithTitle());
        Jgpushtool.push();
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_ios_alertWithExtrasAndMessage());
        Jgpushtool.push();
    }

    /**
     * 保存通知
     * @param title
     * @param alert
     * @param content
     */
    @Override
    public String saveNoticeInfo(String title, String alert, String content, String type){
        //入库
        String maxId = RedisCommon.getMaxSystemNoticeId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxSystemNoticeId(messageDao.getSystemNoticeId());
        }
        SystemNotice systemNotice = new SystemNotice();
        String uuid = UUID.randomUUID().toString();
        systemNotice.setId(uuid);
        systemNotice.setNewsSerialId(maxId);
        systemNotice.setType(type);
        systemNotice.setTitle(title);
        systemNotice.setAlert(alert);
        systemNotice.setContent(content);
        messageDao.addSystemNoticeInfo(systemNotice);
        return uuid;
    }

    /**
     * 保存是否阅读标识
     * @param uuid
     */
    private void addIsReadToRedisByEmpStatus(String uuid){
        //增加是否阅读记录
        List<String> employeeIdList = employeeDao.getEmployeeIdByEmpStatus();
        if (employeeIdList.size() > 0){
            RedisCommon.addIsRead(uuid, employeeIdList);
        }
    }

    /**
     * 保存是否阅读的标识
     * @param uuid 消息id
     * @param tags 发送对象标签
     */
    private void addIsReadToRedisByTags(String uuid, String... tags){
        //增加是否阅读记录
//        List<String> stringList = Arrays.asList(tags);
        List<String> employeeIdList = employeeDao.getEmployeeIdByStoreId(tags);
        if (employeeIdList.size() > 0){
            RedisCommon.addIsRead(uuid, employeeIdList);
        }
    }

    /**
     * 保存是否阅读的标识
     * @param uuid 消息id
     * @param Aliases 发送对象标签
     */
    private void addIsReadToRedisByAliases(String uuid, String... Aliases){
        //增加是否阅读记录
        List<String> stringList = Arrays.asList(Aliases);
        if (stringList.size() > 0){
            RedisCommon.addIsRead(uuid, stringList);
        }
    }

    /**
     * 群发安卓设备
     * @param title
     * @param alert
     */
    @Override
    public void sendMessageToAndroid(String title, String alert){
        Jgpushtool.setTitle(title);
        Jgpushtool.setAlert(alert);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_android_alertWithTitle());
        Jgpushtool.push();
    }

    /**
     * 群发IOS设备
     * @param alert
     * @param content
     */
    @Override
    public void sendMessageToIos(String alert, String content, HashMap extrasMap){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setMsgContent(content);
        Jgpushtool.setExtrasMap(extrasMap);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_ios_alertWithExtrasAndMessage());
        Jgpushtool.push();
    }

    /**
     * 群发至设备Tags(标签)
     * @param content
     * @param alert
     * @param title
     * @param extrasMap 附加信息
     * @param tags
     */
    @Override
    public void sendMessageToTags(String title, String alert, String content, HashMap<String,String> extrasMap, String... tags){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setTitle(title);
        Jgpushtool.setTags(tags);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_android_tag_alertWithTitle());
        Jgpushtool.push();
        Jgpushtool.setMsgContent(content);
        Jgpushtool.setExtrasMap(extrasMap);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_ios_tag_alertWithExtrasAndMessage());
        Jgpushtool.push();
    }

    /**
     * 发送消息至所有平台
     * @param alert
     * @param tags
     */
    @Override
    public void sendMessageToTags( String alert, String... tags){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setTags(tags);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_all_alias_alert());
        Jgpushtool.push();
    }


    /**
     * 群发至android设备Tags(标签)
     * @param alert
     * @param title
     * @param tags
     */
    @Override
    public void sendMessageToAndoridTags(String alert, String title, String... tags){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setTitle(title);
        Jgpushtool.setTags(tags);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_android_tag_alertWithTitle());
        Jgpushtool.push();

    }

    /**
     * 群发至ios设备Tags(标签)
     * @param alert
     * @param content
     * @param tags
     */
    @Override
    public void sendMessageToIosTags(String alert, String content, HashMap extrasMap, String... tags){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setTags(tags);
        Jgpushtool.setMsgContent(content);
        Jgpushtool.setExtrasMap(extrasMap);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_ios_tag_alertWithExtrasAndMessage());
        Jgpushtool.push();

    }

    /**
     * 群发至别名alies（别名）
     * @param alert
     * @param title
     * @param content
     * @param extrasMap
     * @param aliases
     */
    @Override
    public void sendMessageToAlias(String title, String alert, String content, HashMap extrasMap,
                                   String... aliases){
        Jgpushtool.setAlert(alert);
        Jgpushtool.setTitle(title);
        Jgpushtool.setExtrasMap(extrasMap);
        Jgpushtool.setMsgContent(content);
        Jgpushtool.setAliases(aliases);
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_Android_alias_alert());
        Jgpushtool.push();
      //  Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_Ios_alias_alertAndMessage());
      //  Jgpushtool.push();



    }

    /**
     * 获取课程取消消息内容
     * @param courseplanId
     * @return
     */
    @Override
    public String getCourseOrderCanceledMessageContent(String courseplanId){
        CoursePlan coursePlan = courseDao.getCoursePlanInfo(courseplanId);
        String content = String.format(MessageConstants.COURSE_ORDER_CANCELED, coursePlan.getStoreName(),
                coursePlan.getEmployeeName(), coursePlan.getClassDatetime(), coursePlan.getCourseName());
        return content;
    }

    /**
     * 获取课程开课通知消息内容
     * @param courseplanId
     * @return
     */
    public String getCourseOrderAttentionMessageContent(String courseplanId){
        CoursePlan coursePlan = courseDao.getCoursePlanInfo(courseplanId);
        String content = String.format(MessageConstants.COURSE_START_ATTENTION,coursePlan.getStoreName(),
                coursePlan.getEmployeeName(),coursePlan.getClassDatetime(),coursePlan.getCourseName());
        return content;
    }

    /**
     * 课程评论消息
     * @return
     */
    public String getCourseCommentMessageContent(){
        String content = MessageConstants.COURSE_COMMENT;
        return content;
    }

    /**
     * 获取课程预约内容
     * @param orderStatus 预约状态:1已预约，2已签到，3爽约，4取消
     * @param empName 老师名称
     * @param couseName 课程名称
     * @return
     */
    @Override
    public String getOrderCouseRecordContent(String orderStatus, String empName, String couseName) {
        String contentStr ="";
        if (CommonEnum.Course.OrderStatus.reserved.getValue().equals(orderStatus)){//已预约
            contentStr = String.format(MessageConstants.RESERVED_COUSE_RECORD,empName,couseName);
        }else if (CommonEnum.Course.OrderStatus.signed.getValue().equals(orderStatus)){//签到
            contentStr = String.format(MessageConstants.SIGNED_COUSE_RECORD,empName,couseName);
        }else if (CommonEnum.Course.OrderStatus.breakorder.getValue().equals(orderStatus)){//爽约
            contentStr = String.format(MessageConstants.BREAKORDER_COUSE_RECORD,empName,couseName);
        }else if (CommonEnum.Course.OrderStatus.cancel.getValue().equals(orderStatus)){//取消
            contentStr = String.format(MessageConstants.CANCEL_COUSE_RECORD,empName,couseName);
        }
        return contentStr;
    }

    /**
     * 获取会卡刷卡变更内容
     * @param membercardRecord
     * @return
     */
    @Override
    public String getModifyRecordContent(MembercardRecord membercardRecord,String membercardType) {

        String modifyType = membercardRecord.getModifyType();
        String contentStr ="";
        if (CommonEnum.Membercardmodify.modifyType.modify_transfer.getValue().equals(modifyType)){//过户
             contentStr = String.format(MessageConstants.TRANSFER_CARD_RECORD,membercardRecord.getNewcardName(),membercardRecord.getTarmemberName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_stop.getValue().equals(modifyType)){//停卡
            contentStr = String.format(MessageConstants.STOP_CARD_RECORD,membercardRecord.getNewcardName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_change.getValue().equals(modifyType)){//换卡
            contentStr = String.format(MessageConstants.CHANGE_CARD_RECORD,membercardRecord.getOldcardName(),membercardRecord.getNewcardName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_start.getValue().equals(modifyType)){//启用
            contentStr = String.format(MessageConstants.START_CARD_RECORD,membercardRecord.getNewcardName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_open.getValue().equals(modifyType)) {//开卡
            contentStr = String.format(MessageConstants.OPEN_CARD_RECORD, membercardRecord.getNewcardName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_give.getValue().equals(modifyType)){//赠送
            if (CommonEnum.MemberCard.membcardType.type_times.getValue().equals(membercardType)){//次卡
                contentStr = String.format(MessageConstants.GIVE_CARD_RECORD_TIMES,membercardRecord.getNewcardName(),membercardRecord.getGiveTimes());
            }else {//期限卡
                contentStr = String.format(MessageConstants.GIVE_CARD_RECORD_DAYS,membercardRecord.getNewcardName(),membercardRecord.getGiveTimes());
            }
        }else if (CommonEnum.Membercardmodify.modifyType.modify_cali.getValue().equals(modifyType)){//校准
            if (CommonEnum.MemberCard.membcardType.type_times.getValue().equals(membercardType)){//次卡
                contentStr = String.format(MessageConstants.CALIBRATION_CARD_RECORD_TIMES,membercardRecord.getNewcardName(),
                        membercardRecord.getOldTimes(),membercardRecord.getNewTimes());
            }else {//期限卡
                contentStr = String.format(MessageConstants.CALIBRATION_CARD_RECORD_DAYS,membercardRecord.getNewcardName(),
                        membercardRecord.getOldValidityTime(),membercardRecord.getNewValidityTime());
            }
        }else if (CommonEnum.Membercardmodify.modifyType.modify_recharge.equals(modifyType)){//充值
            if (CommonEnum.MemberCard.membcardType.type_times.equals(membercardType)){//次卡
                contentStr = String.format(MessageConstants.RECHARGE_CARD_RECORD_TIMES,membercardRecord.getNewcardName(),membercardRecord.getTimes());
            }else {//期限卡
                contentStr = String.format(MessageConstants.RECHARGE_CARD_RECORD_DAYS,membercardRecord.getNewcardName(),membercardRecord.getMonths());
            }
        }else if (CommonEnum.Membercardmodify.modifyType.modify_leave.equals(modifyType)){//请假
            return "";
        }else if (CommonEnum.Membercardmodify.modifyType.modify_del.equals(modifyType)){//删除
            return "";
        }
        return contentStr;
    }

    /**
     * 获取提交订单推送的内容
     * @param membercardRecord
     * @return
     */
    @Override
    public String getSubmitOrderContent(MembercardRecord membercardRecord) {
        String modifyType = membercardRecord.getModifyType();
        String contentStr ="";
        if (CommonEnum.Membercardmodify.modifyType.modify_open.getValue().equals(modifyType)) {//开卡
            contentStr = String.format(MessageConstants.OPEN_CARD_RECORD, membercardRecord.getNewcardName());
        }else if (CommonEnum.Membercardmodify.modifyType.modify_recharge.equals(modifyType)){//充值
            if (CommonEnum.MemberCard.membcardType.type_times.equals(membercardRecord.getMembcardType())){//次卡
                contentStr = String.format(MessageConstants.RECHARGE_CARD_RECORD_TIMES,membercardRecord.getNewcardName(),membercardRecord.getTimes());
            }else {//期限卡
                contentStr = String.format(MessageConstants.RECHARGE_CARD_RECORD_DAYS,membercardRecord.getNewcardName(),membercardRecord.getMonths());
            }
        }else if (CommonEnum.Membercardmodify.modifyType.modify_change.getValue().equals(modifyType)){//换卡
            contentStr = String.format(MessageConstants.CHANGE_CARD_RECORD,membercardRecord.getOldcardName(),membercardRecord.getNewcardName());
        }
        return contentStr;
    }

    /**
     * 群发至别名alies（别名）自定义消息
     * @param title
     * @param alert
     * @param content
     * @param extrasMap
     * @param aliases
     */
    @Override
    public void sendMessageToAliasMsg(String title, String alert, String content, HashMap extrasMap, String... aliases) {
        //自定义消息
        Jpush.setAlert(alert);
        Jpush.setTitle(title);
        Jpush.setExtrasMap(extrasMap);
        Jpush.setMsgContent(content);
        Jpush.setAliases(aliases);
        Jpush.setPushPayload(Jpush.buildPushObject_Android_alias_alert());
        Jpush.push();
        // Jpush.setPushPayload(Jpush.buildPushObject_Ios_alias_alertAndMessage());
        Jpush.push();
    }

    /**
     * 群发至设备Tags(标签)自定义消息
     * @param title
     * @param alert
     * @param content
     * @param extrasMap
     * @param tags
     */
    @Override
    public void sendMessageToTagsMsg(String title, String alert, String content, HashMap<String, String> extrasMap, String... tags) {
        Jpush.setAlert(alert);
        Jpush.setTitle(title);
        Jpush.setTags(tags);
        Jpush.setPushPayload(Jpush.buildPushObject_android_tag_alertWithTitle());
        Jpush.push();
        Jpush.setMsgContent(content);
        Jpush.setExtrasMap(extrasMap);
        Jpush.setPushPayload(Jpush.buildPushObject_ios_tag_alertWithExtrasAndMessage());
        Jpush.push();
    }

}
