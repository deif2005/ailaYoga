package com.dod.sport.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.dod.sport.JSMS.SendSMSResult;
import com.dod.sport.JSMS.ValidSMSResult;
import com.dod.sport.JSMS.common.SMSClient;
import com.dod.sport.JSMS.common.model.BatchSMSPayload;
import com.dod.sport.JSMS.common.model.BatchSMSResult;
import com.dod.sport.JSMS.common.model.RecipientPayload;
import com.dod.sport.JSMS.common.model.SMSPayload;
import com.dod.sport.JSMS.schedule.model.ScheduleResult;
import com.dod.sport.JSMS.schedule.model.ScheduleSMSPayload;
import com.dod.sport.JSMS.schedule.model.ScheduleSMSResult;
import com.dod.sport.config.Configuration;
import com.dod.sport.service.ISMSExampleService;
import com.dod.sport.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/11/18
 * \* Time: 17:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:短信验证码
 * \
 */
@Service
public class SMSExampleServiceImpl implements ISMSExampleService{

    protected static Logger logger = LoggerFactory.getLogger(SMSExampleServiceImpl.class);
    /**
     *短信验证码发送
     * @param mobile
     * @return
     */
    public String sendSMSCode(String mobile,String code,String tempId){

        SendSMSResult res = new SendSMSResult();
        SMSClient client = new SMSClient("89786bf250ed7988d32663f8","2cda10a396b5568288c61a69");
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(mobile)
                .setTempId(Integer.parseInt(tempId))
                .setTTL(300)
                .addTempPara("number", code)
                .addTempPara("phone",mobile)
                .build();
        try {
            res = client.sendTemplateSMS(payload);
            logger.info(res.toString());
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        }
        return res.toString();

    }

    /**
     * 短信验证
     *@param code
     * @param msgId
     */
    public String sendValidSMSCode(String code,String msgId) {
        SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
        ValidSMSResult res = new ValidSMSResult() ;
        try {
            res = client.sendValidSMSCode(msgId, code);
            logger.info(res.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }

        return res.toString();
    }

    /**
     * 短信验证
     *@param code
     * @param msgId
     */
    public Boolean isValidSMSCode(String code,String msgId) {
        SMSClient client = new SMSClient(Configuration.getSmsMasterSecret(), Configuration.getSmsAppkey());
        ValidSMSResult res = new ValidSMSResult() ;
        try {
            res = client.sendValidSMSCode(msgId, code);
            logger.info(res.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }

        return res.getIsValid();
    }


}