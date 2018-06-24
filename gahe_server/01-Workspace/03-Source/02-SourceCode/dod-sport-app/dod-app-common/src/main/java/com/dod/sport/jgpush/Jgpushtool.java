package com.dod.sport.jgpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.dod.sport.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

/**
 * Created by defi on 2017-08-25.
 * 极光推送工具类
 */
public class Jgpushtool {

    private static Logger logger = LoggerFactory.getLogger(Jgpushtool.class);

    private static String masterSecret = Configuration.getJgPushMasterSecret();
    private static String appKey = Configuration.getJgPushAppkey();

    private static String alert;

    private static String title;

    private static String msgContent;

    private static String[] aliases;

    private static String[] tags;

    private static PushPayload pushPayload;

    private static HashMap<String,String> extrasMap;

    private static String extrasKey="";

    private static String extrasValue="";

    private static JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());


    public static void push(){
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = pushPayload;

        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

//    推送对象：所有平台，所有设备，内容为 ALERT 的通知
    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(alert);
    }

//    所有平台，推送目标是别名为 "aliases"，通知内容为 alert
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(aliases))
                .setNotification(Notification.alert(alert))
                .build();
    }

//    推送对象：android平台，所有设备，
//    内容是 alert，并且标题为 title
    public static PushPayload buildPushObject_android_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(alert, title, null))
                .build();
    }

//    推送对象：android平台，推送目标是别名为 "aliases"，通知内容为 alert, 标题为 title
    public static PushPayload buildPushObject_Android_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(aliases))
                .setNotification(Notification.android(alert, title, extrasMap))
                .setMessage(Message.content(msgContent))
                .build();
    }

    //    推送对象：ios平台，推送目标是别名为 "aliases"，通知内容为 alert
    public static PushPayload buildPushObject_Ios_alias_alertAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(aliases))
                .setNotification(Notification.ios(alert, extrasMap))
                .setMessage(Message.content(msgContent))
                .build();
    }

    //    推送对象：平台是 ios，所有设备
    //    内容是 通知ALERT
    public static PushPayload buildPushObject_ios_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra(extrasKey, extrasValue)
                                .build())
                        .build())
                .setMessage(Message.content(msgContent))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    //    推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，
    //    内容是 Android 通知 ALERT，并且标题为 TITLE
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.tag_and(tags))
            .setNotification(Notification.android(alert, title, null))
            .build();
    }

    //    推送对象：平台是 iOS，推送目标是 "tag1"推送内容同时包括通知与消息 - 通知信息是
    //    ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。
    //    通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”
    //    （如果不显式设置的话，Library 会默认指定为开发）
    public static PushPayload buildPushObject_ios_tag_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and(tags))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra(extrasKey, extrasValue)
                                .build())
                        .build())
                .setMessage(Message.content(msgContent))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

//    推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息 - 通知信息是
//    ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。
//    通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”
//    （如果不显式设置的话，Library 会默认指定为开发）
public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
    return PushPayload.newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.tag_and("tag1", "tag_all"))
            .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                            .setAlert(alert)
                            .setBadge(5)
                            .setSound("happy")
                            .addExtra(extrasKey, extrasValue)
                            .build())
                    .build())
            .setMessage(Message.content(msgContent))
            .setOptions(Options.newBuilder()
                    .setApnsProduction(true)
                    .build())
            .build();
    }

//    推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），
//    推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtra(extrasKey, extrasValue)
                        .build())
                .build();
    }

//    推送对象：推送内容包含SMS信息
    public void testSendWithSMS() {
        try {
            SMS sms = SMS.content("Test SMS", 10);
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "alias1");
            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static String getAlert() {
        return alert;
    }

    public static void setAlert(String alert) {
        Jgpushtool.alert = alert;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Jgpushtool.title = title;
    }

    public static String getMsgContent() {
        return msgContent;
    }

    public static void setMsgContent(String msgContent) {
        Jgpushtool.msgContent = msgContent;
    }

    public static String[] getAliases() {
        return aliases;
    }

    public static void setAliases(String... aliases) {
        Integer a = aliases.length;
        for (int i=0; i<a; i++){
            aliases[i] = aliases[i].replaceAll("-","");
        }
        Jgpushtool.aliases = aliases;
    }

    public static String[] getTags() {
        return tags;
    }

    public static void setTags(String... tags) {
        Integer a = tags.length;
        for (int i=0; i<a; i++){
            tags[i] = tags[i].replaceAll("-","");
        }
        Jgpushtool.tags = tags;
    }

    public static PushPayload getPushPayload() {
        return pushPayload;
    }

    public static void setPushPayload(PushPayload pushPayload) {
        Jgpushtool.pushPayload = pushPayload;
    }

    public static HashMap<String, String> getExtrasMap() {
        return extrasMap;
    }

    public static void setExtrasMap(HashMap<String, String> extrasMap) {
        Jgpushtool.extrasMap = extrasMap;
        for (String key : extrasMap.keySet()){
            extrasKey = key;
            extrasValue = extrasMap.get(key);
        }
    }


}
