package com.dod.sport.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.dod.sport.domain.po.Member.MemberCard;
import com.dod.sport.domain.po.PayRecord;
import com.dod.sport.service.IOrderService;
import com.dod.sport.service.IPayService;
import com.dod.sport.util.*;
import com.dod.sport.util.Base64;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * \* Description:
 * \   支付宝支付接口
 */

@Controller
@RequestMapping(value = "/api/alipay/v1")
public class AlipayController extends BaseController {

    @Autowired
    IPayService payService;

    @Autowired
    IOrderService orderService;

    /***
     *  支付宝支付接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/alipay", method = RequestMethod.POST)
    public String alipay(@RequestParam(required = false, defaultValue = "0") Double fee ,
                         @RequestParam(value = "orderNo") final String orderNo,
                         @RequestParam(value = "body") final String body,
                         @RequestParam(value = "attach") final String attach) throws Exception {

        return payService.alipay(fee,orderNo,body,attach);
    }

    /***
     *  支付宝支付接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/testAlipay", method = RequestMethod.POST)
    public String alipay(@RequestParam(value = "orderNo") final String orderNo) throws Exception {

        orderService.completeOrderByOrderSerialId(orderNo, CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue());
        return  "";
    }


    /***
     *  APP--支付成功后回调方法
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/alinNotify" , method = RequestMethod.POST)
    public void alinNotify()  {

        payService.alinNotify(super.response, super.request);
    }

    /**
     * 获取已经支付信息记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/getPayRecordList" , method = RequestMethod.POST)
    public Map<String,Object> getPayRecordList(@RequestParam(value = "userId") final String userId){

        Map<String,Object> map = new HashMap<>();
        List<PayRecord> payList = payService.getPayRecordList(userId);
        map.put("payList",payList);
        return map;
    }

}