package com.dod.sport.service;

import com.dod.sport.domain.po.Member.MembercardRecord;

import java.util.HashMap;

/**
 * Created by defi on 2017-08-25.
 * 消息推送服务service
 */
public interface IMessageService {

    /**
     * 群发通知所有设备
     * @param title
     * @param content
     */
    public void sendMessageToAllBusiness(String title, String alert, String content);

    /**
     * 群发安卓设备
     * @param title
     * @param content
     */
    public void sendMessageToAndroid(String title, String content);

    /**
     * 群发ios设备
     * @param alert
     * @param content
     * @param extrasMap
     */
    public void sendMessageToIos(String alert, String content, HashMap extrasMap);

    /**
     * 群发至设备Tags
     * @param content
     * @param extrasMap
     * @param tags
     */
    public void sendMessageToTags(String title, String alert, String content, HashMap<String,String> extrasMap, String... tags);

    /**
     * 发送消息至所有平台
     * @param alert
     * @param tags
     */
    public void sendMessageToTags( String alert, String... tags);

    /**
     * 群发至android设备Tags
     * @param alert
     * @param title
     * @param tags
     */
    public void sendMessageToAndoridTags(String alert, String title, String... tags);

    /**
     * 群发至别名alies
     * @param alert
     * @param title
     * @param extrasMap
     * @param aliases
     */
    public void sendMessageToAlias(String title, String alert, String content, HashMap extrasMap,
                                   String... aliases);

    /**
     * 群发至ios设备Tags
     * @param alert
     * @param content
     * @param tags
     */
    public void sendMessageToIosTags(String alert, String content, HashMap extrasMap, String... tags);

    /**
     * 保存系统通知
     * @param title
     * @param alert
     * @param content
     */
    public String saveNoticeInfo(String title, String alert, String content, String type);

    /**
     * 保存并发送--系统通知(广播)
     * @param title
     * @param alert
     * @param content
     */
    public void sendSystemNotice(String title, String alert, String content);

    /**
     * 发送联盟消息--发送至所有商家
     * @param title
     * @param alert
     * @param content
     */
    public void sendLeagueNotice(String title, String alert, String content);

    /**
     * 发送内部消息--发送至指定tags
     * @param title
     * @param alert
     * @param content
     * @param storeIds
     */
    public void sendInnerNoticeByTags(String title, String alert, String content, String... storeIds);

    /**
     * 发送内部消息--发送至指定aliases
     * @param title
     * @param alert
     * @param content
     * @param employeeIds
     */
    public void sendInnerNotice(String title, String alert, String content, String... employeeIds);

 /**
     * 获取课程取消消息内容
     * @param courseplanId
     * @return
     */
    public String getCourseOrderCanceledMessageContent(String courseplanId);

    /**
     * 获取课程开课通知消息内容
     * @param courseplanId
     * @return
     */
    public String getCourseOrderAttentionMessageContent(String courseplanId);

    /**
     * 课程评论消息
     * @return
     */
    public String getCourseCommentMessageContent();

    /**
     * 获取预约内容
     * @param orderStatus
     * @param empName
     * @param couseName
     * @return
     */
    public String getOrderCouseRecordContent(String orderStatus,String empName,String couseName);

    /**
     * 获取会卡刷卡变更内容
     * @param membercardRecord
     * @return
     */
    public String getModifyRecordContent(MembercardRecord membercardRecord,String membercardType);

    /**
     * 获取提交订单推送的内容
     * @param membercardRecord
     * @return
     */
    public String getSubmitOrderContent(MembercardRecord membercardRecord);

    /**
     * 群发至别名alies（别名）自定义消息
     * @param title
     * @param alert
     * @param content
     * @param extrasMap
     * @param aliases
     */
    public void sendMessageToAliasMsg(String title, String alert, String content, HashMap extrasMap,
                                               String... aliases);

    /**
     * 群发至设备Tags(标签)自定义消息
     * @param title
     * @param alert
     * @param content
     * @param extrasMap
     * @param tags
     */
    public void sendMessageToTagsMsg(String title, String alert, String content, HashMap<String,String> extrasMap, String... tags);


}
