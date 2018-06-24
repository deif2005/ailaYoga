package com.dod.sport.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import sun.net.www.http.HttpClient;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.KeyStore;

/**
 * HttpUtils
 * http工具类
 * @author yuhao
 * @date 2016/8/11
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

//    private static SSLContext wx_ssl_context = null; //微信支付ssl证书
//    private static String mchid = null; //微信支付ssl证书
//
//    static{
//
//        try {
//            Resource resource =new ClassPathResource("wx_apiclient_cert.p12");
//            KeyStore keystore = KeyStore.getInstance("PKCS12");
//            char[] keyPassword = mchid.toCharArray(); //证书密码
//            keystore.load(  resource.getInputStream(), keyPassword);
//            wx_ssl_context = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 获取访问用户的客户端IP（适用于公网与局域网）.
     */
    public static final String getIpAddr(final HttpServletRequest request)
            throws Exception {
        if (request == null) {
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
        }
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        return ipString;
    }
	
	/* post请求
     * @param url url地址
     * @param json 参数,JSONObject/JSONArray
     * @return
     */
    public static JSONObject sentPost(String url,JSON json) {
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != json) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(json.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str;
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * post请求
     * @param url url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject sentPost(String url,JSONObject jsonParam) {
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str;
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     * @param url 路径
     * @return
     */
    public static JSONObject sendGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * @description 功能描述: post 请求
     * @param url 请求地址
     * @param s 参数xml
     * @return 请求失败返回null
     */
    public static String post(String url, String s) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        String body = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5000).build())
                    .build();
            httpPost.setEntity(new StringEntity(s, "UTF-8"));
            response = httpClient.execute(httpPost);
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }


//    /**
//     * @description 功能描述: post https请求，服务器双向证书验证
//     * @param url 请求地址
//     * @param s 参数xml
//     * @return 请求失败返回null
//     */
//    public static String posts(String url, String s) {
//        CloseableHttpClient httpClient = null;
//        HttpPost httpPost = new HttpPost(url);
//        String body = null;
//        CloseableHttpResponse response = null;
//        try {
//            httpClient = HttpClients.custom()
//                    .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(5000).build())
//                    .setSSLSocketFactory(getSSLConnectionSocket())
//                    .build();
//            httpPost.setEntity(new StringEntity(s, "UTF-8"));
//            response = httpClient.execute(httpPost);
//            body = EntityUtils.toString(response.getEntity(), "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return body;
//    }

//    //获取ssl connection链接
//    private static SSLConnectionSocketFactory getSSLConnectionSocket() {
//        return new SSLConnectionSocketFactory(wx_ssl_context, new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
//                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//    }
}