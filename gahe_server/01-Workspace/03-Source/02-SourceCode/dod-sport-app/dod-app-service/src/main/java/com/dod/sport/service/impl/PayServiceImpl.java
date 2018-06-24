package com.dod.sport.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.dod.sport.config.Configuration;
import com.dod.sport.dao.IOrderDao;
import com.dod.sport.dao.IPayDao;
import com.dod.sport.domain.po.PayRecord;
import com.dod.sport.service.IOrderService;
import com.dod.sport.service.IPayService;
import com.dod.sport.util.*;
import com.dod.sport.util.Base64;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.logging.Logger;

/**

 * \* Description:
 * \   支付
 */
@Service
public class PayServiceImpl implements IPayService {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    private static final String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单

    private static final String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery"; // 支付订单查询

    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088821277083684";

    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDN1hNb0yqQuPe3u3/dcYQGUCnjgWIcIwel1C8mb9uL1AvM2uOYCQAQqVwBYXq0WBBtrSON3RjzN4Se5QvZjn5WAx9vqXMJK6l9gvVYvstmK38lSFe06CZUO0y5kt9IfrcxhCp69/H/daTa8cJm1y8fn0+w1OR7Jo1kKY3wW/PrhmajN3pTXCoAW1/2QIVM/kgqvoEkzA3dtpv62/M+x/UCRTJ2VmE6y8Hm5+Br2gTHmCqX57aajw97c1VtJ7Vg+PUGSJCE+XBBbk9aChB6pJ1R2w/h+RsoCpP1Xln4DereXZQmLIwQ69dq6GA7d/OsrisedUvfRSwGSp1eYsj1n0d1AgMBAAECggEBAMlENDWra4mLc51SV5reLmxF4k+06Yv9rLK3oUsCQTiepNMwjS7STh/8mhw3htXk7ltv2r4SnRzlAmakLEwUiu/rqcdzdypWaSpy7RhTp2tFyNNZw3j/LstaGJG0TWQDk3l1dFw9KYHTNNlpX99TNZ7EFhYgdTD9528zOrj2ELIQPkg1a2bJGLU7hy8JGoWzOZIKNY9yKyv3u7gsaPEB3LDadrzzG3SrtmPM0gWj/oZezlkBK4hO3HvLjOnORaSGTTAQJMwD6LMh4AYS6qFJQ1Vh0KNYNqE+sbk5owAihlVwkAFcC+9plfbenuGPqKTgCWL818A9Yr4xDt/4Zm4zJUECgYEA56lJy67l5mGwCC/+n6d7Hv1ctFIyGW5SgQNDWV/DLqXZHD5PREKQUsrjrFV6LKNjqb2ElbTpqCgR/33Jnm5Rugk5n8irheIeZKYPrV+JfLGCAc6K6KT6T/0mgKFRd5EuhCSiS7W28RRCvZybNC6wGPMlHxXOx7fTkvayToENW70CgYEA43Yz0WGPyx/3ecTFKaLeZhwyahYn6hqTeldihZp8u/7VYKVWGziFaDE3Zw9sWP2qRw5Ofy2oAU9OKEzRrgPfFHwAnH2pa17xPdIcmLNNqw/567kooWuHROecKNiKKz/9OX9GVU6+mQsubdDDoqesHnIDgJI/qHPwEsQmWYbyuhkCgYB78PeZcNZxXrxmdP8efeWCW/1YQE2ri3m6qntC+tgbQgIZeUDBfXdnxELw9b5q2XO94bZoviTwoZeXgL349H7nMH5S/XK/K44lPS9QC7gsLXAn9VAeTf3aA8+wrB5BATzI2Cy4otzguH1IotvqnDv87UFkEy/RbIlWrIQTOjpiZQKBgQDIv1mcQW6W5u+TGIap6IQ/ukMEPemcQzhFkTnykBXmFVJcqY8FFy0md8ldu87z+2x8qbO9qp3MCiIAj0u/OMt5WLdxZbZQfMqMtRNn8Vdq0f7Qe+nQDQalWYQIyJOo+CCUzi9UutqcV7HyWX1P4IuXkyhkIK7uDOSkq1jtSpyOgQKBgQCPES1Sb241J5mbWsg0DRkc0/PUtH/8Zc+C8rjmKSRk+gQPxVG1UQxPON+nUPHHrl3sMdeBTsyUPLImNeHIZlFFdCQ/apcYmu4Cq+EkmMWu9VUbbHFsN/qIRfBXosLHmzHd3CjkQcUdJe+ldZ2MARm/wlPpX4TBPPzv4kj1fdOkcg==";

    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhDpoMvJCXA/6Evtc2hA1zZAmUEZHGsslGcwSoo7z2RWl8SbuMREUd5Io6RAmCvX3Wt6Uy5VVFCPhNSXrIo4C1Y9uDa1ZlTch3Q278EEXYrenrBwbQHNDj+WMQD12Z83Aqm0y5kbXomghkJW4sZODcqgEB5CY5XmYE8hbiSL8d8pZRebAxDixXa6XLduYPK94WOJnJKhtCrtO9u1b0M+LyYuroMdZQjw2++pIff4wuSaWYLfr1C8qfM2omETLK6feAGhv7d8jzaoVZ5XTr+TSvjXl99O0pRS+zZjBQZC685ST6gPbydWdiTaDybeK83giPUSFjsqvVHHcsrD60aBCdQIDAQAB";
    // 签名方式
    public static String sign_type = "RSA";

    // 接收通知的接口名
    public static String service = "https://docs.open.alipay.com/399/106844/";

    //APPID
    public static String app_id="2017100609158754";

    @Autowired
    private IPayDao payDao;

    @Autowired
    IOrderService orderService;

    /**
     * 添加已经支付信息
     * @return
     */
    public void addPayRecord(PayRecord payRecord){
        payDao.addPayRecord(payRecord);
    }

    /**
     * 支付下订单
     *
     */
    @Override
    public Map<String, String> weiXinPay( Double fee ,String orderNo,String body ,String attach ,HttpServletRequest baseRequest){

        Map<String, String> restmap = new HashMap<String, String>();
        boolean flag = true; // 是否订单创建成功
        String requests = null;
        try {
            String total_fee = BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(100))
                    .setScale(0, BigDecimal.ROUND_HALF_UP).toString();
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("appid", Configuration.getWeiXinAppId());
            parm.put("mch_id",Configuration.getWeiXinMchId());
            parm.put("device_info", "WEB");
            parm.put("nonce_str", WeiXinPayUtil.getNonceStr());
            parm.put("body", body);
            parm.put("attach", attach);
            parm.put("out_trade_no", WeiXinPayUtil.getTradeNo());
            parm.put("total_fee", total_fee);
            parm.put("spbill_create_ip", WeiXinPayUtil.getRemoteAddrIp(baseRequest));
            parm.put("notify_url", "http://120.77.156.227:8082/dodsportserver/api/weiXinPay/v1/orderPayNotify"); //微信服务器异步通知支付结果地址  下面的order/notify 方法
            parm.put("trade_type", "APP");
            parm.put("sign", WeiXinPayUtil.getSign(parm,Configuration.getWeiXinApiSecret()));
            String reqStr = XmlUtil.getRequestXml(parm);
            requests = HttpUtil.post(ORDER_PAY, reqStr);

        } catch (Exception e) {
            Logger.getLogger("");
        }
        restmap = XmlUtil.getXmlToMap(requests);
        Map<String, String> payMap = new HashMap<String, String>();
        if (restmap != null  && "SUCCESS".equals(restmap.get("result_code"))) {
            payMap.put("appid",Configuration.getWeiXinAppId());
            payMap.put("partnerid", Configuration.getWeiXinMchId());
            payMap.put("prepayid", restmap.get("prepay_id"));
            payMap.put("package", "Sign=WXPay");
            payMap.put("noncestr", WeiXinPayUtil.getNonceStr());
            payMap.put("timestamp", WeiXinPayUtil.payTimestamp());
            try {
                payMap.put("sign", WeiXinPayUtil.getSign(payMap, Configuration.getWeiXinApiSecret()));
            } catch (Exception e) {

            }
        }
        return  payMap;
    }

    /**
     * 订单支付微信服务器异步通知
     *
     */
    @Override
    public void orderPayNotify(HttpServletResponse response ,HttpServletRequest request){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        try {
            ServletInputStream in = request.getInputStream();
            String resxml = FileUtil.readInputStream2String(in);
            Map<String, String> restmap = XmlUtil.getXmlToMap(resxml);
            if ("SUCCESS".equals(restmap.get("return_code"))) {
                // 订单支付成功 业务处理
                String out_trade_no = restmap.get("out_trade_no"); // 商户订单号
                // 通过商户订单判断是否该订单已经处理 如果处理跳过 如果未处理先校验sign签名 再进行订单业务相关的处理
                String sing = restmap.get("sign"); // 返回的签名
                restmap.remove("sign");
                String signnow =  WeiXinPayUtil.getSign(restmap, Configuration.getWeiXinApiSecret());
                if (signnow.equals(sing)) {
                    // 进行业务处理
                    PayRecord payRecord =new PayRecord();
                    payRecord.setOrderId(restmap.get("out_trade_no"));
                    payRecord.setPayTtime(restmap.get("timestamp"));
                    payRecord.setPayMoney(restmap.get("total_fee"));
                    payRecord.setExpenseClassify("1");
                    payRecord.setPayMode("1");
                    payRecord.setExpenseSerialId(WeiXinPayUtil.getTradeNo());
                    payRecord.setId(UUID.randomUUID().toString());
                    payDao.addPayRecord(payRecord);
                    orderService.completeOrderByOrderSerialId(out_trade_no, CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue());

                    logger.info("订单支付通知： 支付成功，订单号" + out_trade_no);
                } else {
                    logger.info("订单支付通知：签名错误");
                }
            } else {
                logger.error("订单支付通知：支付失败，" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }



    /***
     *  支付宝支付接口
     * @return
     * @throws Exception
     */
    @Override
    public  String alipay(Double fee ,String orderNo, String body,String attach) throws Exception {

        //公共参数
        Map<String, String> map = new HashMap<String ,String>();
        map.put("app_id", app_id);
        map.put("method", "alipay.trade.app.pay");
        map.put("format", "json");
        map.put("charset", "utf-8");
        map.put("sign_type", "RSA2");
        map.put("timestamp",DateUtil.getDateTime());
        map.put("version", "1.0");
        map.put("notify_url",service);
        Map<String, String> bizContentMap = new HashMap<String ,String>();
        bizContentMap.put("body", body);
        bizContentMap.put("subject", attach);
        bizContentMap.put("out_trade_no", orderNo);
        bizContentMap.put("product_code", "QUICK_MSECURITY_PAY");
        bizContentMap.put("timeout_express", "30m");
        bizContentMap.put("total_amount", "fee");
        JSONObject bizcontentJson = JSONObject.fromObject(bizContentMap);
        map.put("biz_content", bizcontentJson.toString());
        String sign =AlipayUtil.getSign(map, private_key, true);
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" +  URLEncoder.encode(value, "utf-8");
            } else {
                prestr = prestr + key + "=" + URLEncoder.encode(value ,"utf-8") + "&";
            }
        }
        return prestr +"&"+ sign;
    }

    /**
     *  支付宝服务器异步通知
     *
     */
    @Override
    public void alinNotify(HttpServletResponse response,HttpServletRequest request){

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams =  request.getParameterMap();
        Set<String> keySet = requestParams.keySet();
        String out_trade_no = "";
        String trade_status = "";
        String total_amount = "";
        String sign = "";
        for (String key : keySet) {
            StringBuffer buffer = new StringBuffer();
            for (String string : requestParams.get(key)) {
                buffer.append(string);
            }
            params.put(key, buffer.toString());
            if (key.equals("out_trade_no")) {
                out_trade_no = buffer.toString();// 商户订单号
                System.out.println(key + " : " + buffer.toString());
            } else if (key.equals("trade_status")) {
                trade_status = buffer.toString();// 交易状态
                System.out.println(key + " : " + buffer.toString());
            } else if (key.equals("total_amount")) {
                total_amount = buffer.toString().substring(0,buffer.toString().length()-3) + "";// 充值金额
                System.out.println(key + " : " + total_amount);
            }else if (key.equals("sign")) {
                sign = buffer.toString();//签名
            }
        }
        try {
            // 计算得出通知验证结果
            boolean verify_result =verify(params.toString(),sign, alipay_public_key, "utf-8");

            if (verify_result) {// 验证成功
                if (trade_status.equals("TRADE_SUCCESS")) { // 交易支付成功

                    // 进行业务处理
                    PayRecord payRecord =new PayRecord();
                    payRecord.setOrderId(out_trade_no);
                    payRecord.setPayTtime(DateUtil.getDateTime());
                    payRecord.setPayMoney(total_amount);
                    payRecord.setExpenseClassify("1");
                    payRecord.setPayMode("1");
                    payRecord.setExpenseSerialId(WeiXinPayUtil.getTradeNo());
                    payRecord.setId(UUID.randomUUID().toString());
                    payDao.addPayRecord(payRecord);
                    orderService.completeOrderByOrderSerialId(out_trade_no, CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue());
                }
            } else {// 验证失败
                response.getWriter().println("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param alipay_public_key 支付宝公钥
     * @param input_charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String alipay_public_key, String input_charset)
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey  = Base64.decode(alipay_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new  X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA256WithRSA");

            signature.initVerify(pubKey);
            signature.update( content.getBytes(input_charset) );
            boolean bverify = signature.verify( Base64.decode(sign) );
            return bverify;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取已经支付信息记录
     * @return
     */
    @Override
    public List<PayRecord> getPayRecordList(String userId){

        return payDao.getPayRecordList(userId);
    }
}