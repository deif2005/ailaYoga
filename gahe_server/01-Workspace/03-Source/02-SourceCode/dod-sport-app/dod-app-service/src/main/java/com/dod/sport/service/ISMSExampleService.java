package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BaseRechargegrad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/11/18
 * \* Time: 17:15
 * \* To change this template use File | Settings | File Templates.
 * \* Description:短信验证码
 * \
 */
public interface ISMSExampleService {

    /**
     *短信验证码发送
     * @param mobile
     * @return
     */
    public String sendSMSCode(String mobile,String code ,String tempId );

    /**
     * 短信验证
     *@param code
     * @param msgId
     */
    public String sendValidSMSCode(String code,String msgId);

    /**
     * 短信验证
     *@param code
     * @param msgId
     */
    public Boolean isValidSMSCode(String code,String msgId);
}