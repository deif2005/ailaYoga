package com.dod.sport.service;


import com.dod.sport.domain.po.PayRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *  \* Date: 2017/9/15
 * \* Description:支付记录接口
 * \
 */
public interface IPayService {

    /**
     * 添加已经支付信息记录
     * @return
     */
    public void addPayRecord(PayRecord payRecord);

    /**
     * 微信支付下订单
     *
     */
    public Map<String, String> weiXinPay( Double fee ,String orderNo,String body , String attach , HttpServletRequest request);


    /**
     *  订单支付微信服务器异步通知
     *
     */
    public void orderPayNotify(HttpServletResponse response,HttpServletRequest request);


    /**
     *  支付宝服务器异步通知
     *
     */
    public void alinNotify(HttpServletResponse response,HttpServletRequest request);

    /***
     *  支付宝支付接口
     * @return
     * @throws Exception
     */
    public String alipay( Double fee ,String orderNo,String body , String attach ) throws Exception;


    /**
     * 获取已经支付信息记录
     * @return
     */
    public List<PayRecord> getPayRecordList(String userId);


}