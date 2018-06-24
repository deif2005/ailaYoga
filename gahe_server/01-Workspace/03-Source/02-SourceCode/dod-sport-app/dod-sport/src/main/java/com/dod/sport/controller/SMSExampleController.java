package com.dod.sport.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import com.dod.sport.JSMS.SendSMSResult;
import com.dod.sport.JSMS.ValidSMSResult;
import com.dod.sport.JSMS.common.SMSClient;
import com.dod.sport.JSMS.common.model.BatchSMSPayload;
import com.dod.sport.JSMS.common.model.BatchSMSResult;
import com.dod.sport.JSMS.common.model.RecipientPayload;
import com.dod.sport.JSMS.common.model.SMSPayload;
import com.dod.sport.JSMS.template.SendTempSMSResult;
import com.dod.sport.JSMS.template.TempSMSResult;
import com.dod.sport.JSMS.template.TemplatePayload;
import com.dod.sport.config.Configuration;
import com.dod.sport.service.ISMSExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 正常（普通）短信验证
 * 需要注意的是要在规定的时间内获取到短信验证码并且完成验证才算成功！
 * */
@Controller
@RequestMapping(value = "api/sendExample/v1")
public class SMSExampleController {

    protected static Logger logger = LoggerFactory.getLogger(SMSExampleController.class);

//    private static final String appkey = "2cda10a396b5568288c61a69";
//    private static final String masterSecret = "89786bf250ed7988d32663f8";

    @Autowired
    private ISMSExampleService SMSExampleService;

    /**
     * 短信验证码
     * @param mobile
     */
    @ResponseBody
    @RequestMapping(value = "/sendSMSCode", method = RequestMethod.POST)
    public String sendSMSCode(@RequestParam(value = "mobile") final String mobile,
                              @RequestParam(value = "code") final  String code,
                              @RequestParam(value = "tempId") final  String tempId) {

        return SMSExampleService.sendSMSCode(mobile,code ,tempId);
    }

    /**
     * 短信验证
     *@param code
     * @param msgId
     */
    @ResponseBody
    @RequestMapping(value = "/sendValidSMSCode", method = RequestMethod.POST)
    public String sendValidSMSCode(String code,String msgId) {

        return SMSExampleService.sendValidSMSCode(code,msgId);
    }

    /**
     * 创建短信验证码模板
     */
    @ResponseBody
    @RequestMapping(value = "/createTemplate", method = RequestMethod.POST)
    public void createTemplate() {
        try {
            SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
            TemplatePayload payload = TemplatePayload.newBuilder()
                    .setTemplate("【极光推送】您的验证码为：{{code}}，60s后过期，请勿泄露。如非本人操作，请忽略此短信。谢谢！")
                    .setType(1)
                    .setTTL(60)
                    .setRemark("验证短信")
                    .build();
            SendTempSMSResult result = client.createTemplate(payload);
            logger.info(result.toString());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * 修改短信验证码模板
     */
    @ResponseBody
    @RequestMapping(value = "/updateTemplate", method = RequestMethod.POST)
    public void updateTemplate() {
        try {
            SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
            TemplatePayload payload = TemplatePayload.newBuilder()
                    .setTempId(12345)
                    .setTemplate("您正在修改密码操作验证码为：{{number}},5分钟后过期，请勿泄露。如非本人操作，请忽略此短信。谢谢！")
                    .setType(1)
                    .setTTL(120)
                    .setRemark("验证短信")
                    .build();
            SendTempSMSResult result = client.updateTemplate(payload);
            logger.info(result.toString());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * 校验短信验证码模板
     */
    @ResponseBody
    @RequestMapping(value = "/checkTemplate", method = RequestMethod.POST)
    public void checkTemplate() {
        try {
            SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
            TempSMSResult result = client.checkTemplate(144923);
            logger.info(result.toString());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * 删除短信验证码模板
     */
    @ResponseBody
    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
    public void deleteTemplate() {
        try {
            SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
            ResponseWrapper result = client.deleteTemplate(144923);
            logger.info(result.toString());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }


}