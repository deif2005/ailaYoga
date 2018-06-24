package com.dod.sport.controller;

import com.dod.sport.domain.common.BusiException;
import com.dod.sport.service.IMessageService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by defi on 2017-08-28.
 * 消息发送controller
 */
@Controller
@RequestMapping(value = "api/message/v1")
public class MessageController {

    @Autowired
    IMessageService messagePushService;

    /**
     * 发送消息至所有设备
     * @param title
     * @param content
     * @param alert
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendMessageToAll", method = RequestMethod.POST)
    public String sendMessageToAll(@RequestParam("title") final String title,
                                   @RequestParam("content") final String content,
                                   @RequestParam("alert") final String alert){
        messagePushService.sendMessageToAllBusiness(title,alert,content);
        return "";
    }

    /**
     * 发送消息至标签设备
     * @param title
     * @param content
     * @param alert
     * @param tags
     * @param extraKey
     * @param extraValue
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendMessageToTags", method = RequestMethod.POST)
    public String sendMessageToTags(@RequestParam("title") final String title,
                                    @RequestParam("content") final String content,
                                    @RequestParam("alert") final String alert,
                                    @RequestParam("tags") final String tags,
                                    @RequestParam(value = "extraKey", required = false) final String extraKey,
                                    @RequestParam(value = "extraValue", required = false) final String extraValue){
        HashMap hashMap = new HashMap();
        if ("".equals(tags)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        if ((extraKey != null && "".equals(extraKey)) || (extraValue != null && !"".equals(extraValue))){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }else {
            hashMap.put(extraKey,extraValue);
        }
        messagePushService.sendMessageToTags(content, alert, title, hashMap, tags.split(","));
        return "";
    }

    /**
     * 发送消息至别名设备
     * @param title
     * @param alert
     * @param aliases
     * @param extraKey
     * @param extraValue
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendMessageToAlias", method = RequestMethod.POST)
    public String sendMessageToAlias(@RequestParam("title") final String title,
                                     @RequestParam("alert") final String alert,
                                     @RequestParam("aliases") final String aliases,
                                     @RequestParam(value = "extraKey", required = false) final String extraKey,
                                     @RequestParam(value = "extraValue", required = false) final String extraValue){
        HashMap hashMap = new HashMap();
        if ("".equals(aliases)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        if ((extraKey != null && "".equals(extraKey)) || (extraValue != null && !"".equals(extraValue))){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }else {
            hashMap.put(extraKey,extraValue);
        }
        messagePushService.sendMessageToAlias(alert,title,"",hashMap,aliases.split(","));
        return "";
    }
}
