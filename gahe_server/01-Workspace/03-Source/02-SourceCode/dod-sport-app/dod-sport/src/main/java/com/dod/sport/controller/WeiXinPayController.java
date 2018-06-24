package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.domain.po.PayRecord;
import com.dod.sport.service.IOrderService;
import com.dod.sport.service.IPayService;
import com.dod.sport.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

/**
 *微信支付接口
 *
 */

@Controller
@RequestMapping("/api/weiXinPay/v1")
public class WeiXinPayController extends BaseController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IPayService payService;

    /**
     * 支付下订单
     *
     */
    @ResponseBody
    @RequestMapping(value = "/weiXinPay", method = RequestMethod.POST)
    public Map<String, String> weiXinPay(@RequestParam(required = false, defaultValue = "0") Double fee ,
                                         @RequestParam(value = "orderNo") final String orderNo,
                                         @RequestParam(value = "body") final String body,
                                         @RequestParam(value = "attach") final String attach
    ) {

        return  payService.weiXinPay(  fee , orderNo, body , attach ,super.request);
    }


    /**
     * 支付下订单
     *
     */
    @ResponseBody
    @RequestMapping(value = "/testWeiXinPay", method = RequestMethod.POST)
    public Map<String, String> testWeiXinPay(
            @RequestParam(value = "orderNo") final String orderNo)throws ParseException {


        orderService.completeOrderByOrderSerialId(orderNo, CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue());
        return null;
    }

    /**
     * 订单支付微信服务器异步通知
     *
     */
    @ResponseBody
    @RequestMapping(value ="/orderPayNotify" , method = RequestMethod.POST)
    public void orderPayNotify()  {

        payService.orderPayNotify(super.response,super.request);
    }

//    /**
//     * 查询订单
//     * @param orderId 商户自己的订单号
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getWeiXinOrder", method = RequestMethod.POST)
//    public static String getWeiXinOrder(String orderId){
//
//        Map<String, String> reqMap = new HashMap<String, String>();
//        try {
//            reqMap.put("appid", Configuration.getWeiXinAppId());
//            reqMap.put("mch_id", Configuration.getWeiXinMchId());
//            reqMap.put("nonce_str",  WeiXinPayUtil.getNonceStr());
//            reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
//            reqMap.put("sign", WeiXinPayUtil.getSign(reqMap, Configuration.getWeiXinApiSecret()));
//
//            String reqStr = XmlUtil.getRequestXml(reqMap);
//            String str = HttpUtil.post(ORDER_PAY_QUERY, reqStr);
//            //        return retStr;
//        } catch (Exception e) {
//            Logger.getLogger("");
//        }
//        return null;
//
//    }


//    /**
//     * 关闭订单
//     * @param orderId  商户自己的订单号
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
//    public static Map<String, String> closeOrder(String orderId){
//
//        Map<String, String> reqMap = new HashMap<String, String>();
//        try {
//            reqMap.put("appid", Configuration.getWeiXinAppId());
//            reqMap.put("mch_id", Configuration.getWeiXinMchId());
//            reqMap.put("nonce_str",  WeiXinPayUtil.getNonceStr());
//            reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
//            reqMap.put("sign", WeiXinPayUtil.getSign(reqMap, Configuration.getWeiXinApiSecret()));
//
//            String reqStr = XmlUtil.getRequestXml(reqMap);
////            String retStr = HttpUtil.post(WeiChartConfig.CloseOrderUrl, reqStr);
//
//        } catch (Exception e) {
//            Logger.getLogger("");
//        }
//        return reqMap;
//    }

//    /**
//     * 退款
//     * @param orderId  商户订单号
//     * @param refundId  退款单号
//     * @param totralFee 总金额（分）
//     * @param refundFee 退款金额（分）
//     * @param opUserId 操作员ID
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/refundWei", method = RequestMethod.POST)
//    public static Map<String, String> refundWei(String orderId,String refundId,String totralFee,String refundFee,String opUserId){
//        try {
//            Map<String, String> reqMap = new HashMap<String, String>();
//            reqMap.put("appid", APP_ID);
//            reqMap.put("mch_id", MCH_ID);
//            reqMap.put("nonce_str", WeiXinPayUtil.getNonceStr());
//            reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
//            reqMap.put("out_refund_no", refundId); //商户退款单号
//            reqMap.put("total_fee", totralFee); //总金额
//            reqMap.put("refund_fee", refundFee); //退款金额
//            reqMap.put("op_user_id", opUserId); //操作员
//            reqMap.put("sign", WeiXinPayUtil.getSign(reqMap, API_SECRET ) );
//            String reqStr = XmlUtil.getRequestXml(reqMap);
////            retStr = HttpUtil.posts(ORDER_REFUND, MCH_ID);
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
////        return getInfoByXml(retStr);
//        return  null;
//    }
//
//    /**
//     * 退款查询
//     * @param refundId  退款单号
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getRefundWeiInfo", method = RequestMethod.POST)
//    public static Map<String, String> getRefundWeiInfo(String refundId){
//        try {
//            Map<String, String> parm = new HashMap<String, String>();
//            parm.put("appid", APP_ID);
//            parm.put("mch_id", MCH_ID);
//            parm.put("nonce_str", WeiXinPayUtil.getNonceStr());
//            parm.put("out_refund_no", refundId); //商户退款单号
//            parm.put("sign",WeiXinPayUtil.getSign(parm, API_SECRET));
//
//            String reqStr = XmlUtil.getRequestXml(parm);
////            String retStr = HttpHelper.httpClientPost(ORDER_REFUND_QUERY, reqStr);
////            return getInfoByXml(retStr);
//        } catch (Exception e) {
//            Logger.getLogger("");
//        }
//        return null;
//    }

}


