package com.dodsport.request;

import com.dodsport.GaHeApplication;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

/**
 * Created by Administrator on 2017/7/22.
 *
 * 考勤 签到-签退请求类
 */

public class PunchTheClockRequest{

    public static String Token = SPUtils.getToken(GaHeApplication.getmContext());
    /**
     * 获取Token
     *
     * */
    public static void getToKen(OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.TOKEN;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("id","dodsport");
            request.add("password","dodsport");
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }


    /**
     * 获取验证码
     *
     * Token 唯一标识
     * phoneNum	String		是	员工电话
     * platformId	String		是	1:客户端;2:商家端;3:服务端(新增)
     * operationId	String		是	1:注册;2:忘记密码(新增)
     * */

    public static void getCode(String phoneNum,String operationId, OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.UserCode;

        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("platformId","2");
            request.add("operationId",operationId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     * 获取用户注册时信息  校对验证码
     *
     * Token 唯一标识
     * phoneNum 用户手机号
     * Code 验证码
     * platformId	String		是	1:客户端;2:商家端;3:服务端(新增)
     * operationId	String		是	1:注册;2:忘记密码(新增)
     * */
    public static void userInfo(String phoneNum,String Code,String operationId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.USERINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("identifyingCode",Code);
            request.add("platformId","2");
            request.add("operationId",operationId);
            RequestMeansManager.request(0, request, onResponseListener);

        }

    }


    /**
     * 用户注册
     *
     * Token 唯一标识
     * phoneNum 用户手机号
     * */
    public static void userRegister (String phoneNum,String password,String repassword,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.REGISTER;

        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("password",password);
            request.add("repassword",repassword);

            RequestMeansManager.request(0, request, onResponseListener);
        }

    }

    /**忘记密码
     * token 用户唯一标识
     * phoneNum 用户账号
     * password 用户密码
     * repassword 确认密码
     * */
    public static void userForgetPassword(String phoneNum,String password,String repassword,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.USERFORGETPASSWOED;


        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("password",password);
            request.add("repassword",repassword);

            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**忘记密码---->验证码验证
     * token 用户唯一标识
     * phoneNum 用户账号
     * identifyingCode  验证码
     * platformId	String		是	1:客户端;2:商家端;3:服务端(新增)
     * operationId	String		是	1:注册;2:忘记密码(新增)
     * */
    public static void codeValidate(String phoneNum,String Code,String operationId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.VERIFYIDENTCODE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("identifyingCode",Code);
            request.add("platformId","2");
            request.add("operationId",operationId);

            RequestMeansManager.request(0, request, onResponseListener);
        }

    }


    /**忘记密码
     * token 用户唯一标识
     * phoneNum 用户账号
     * password 用户密码
     * */
    public static void userLogin(String Token,String phoneNum,String password,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.USERLOGIN;

        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("password",password);

            RequestMeansManager.request(0, request, onResponseListener);
        }

    }

    /**
     * 签到规则
     *
     * token	string		是	口令
     * radius	String		是	签到范围半径;单位米
     * storeId	String		是	分店id
     * morningShift	String			早班时间
     * nightShift	String			晚班时间
     * wifiStr	String			Wifi地址字符串(以逗号拼接)
     * creatorId	String		是	创建人id
     * vacationDays	String		是	可休假天数
     * reissueTimes	String		是	可补卡次数
     * lng	String		是	经度
     * lat	String		是	纬度
     * signAdd	String		是	签到地址
     * */
    public static void signSet(String radius,String storeId,String morningShift,String nightShift,String wifiStr,String creatorId
            ,String vacationDays,String reissueTimes,String lng,String lat,String signAdd,OnResponseListener<String> onResponseListener) {
        String url = UrlInterfaceManager.SIGNSET;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request != null) {
            request.add("token", Token);
            request.add("radius", radius);
            request.add("storeId", storeId);
            request.add("morningShift", morningShift);
            request.add("nightShift", nightShift);
            request.add("wifiStr", wifiStr);
            request.add("creatorId", creatorId);
            request.add("vacationDays", vacationDays);
            request.add("reissueTimes", reissueTimes);
            request.add("lng", lng);
            request.add("lat", lat);
            request.add("signAdd", signAdd);
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }

    /**
     * 获取门店签到规则
     * token	string		是	口令
     * storeId	String		是	门店id
     * */
    public static void querySignTimeByStoreId (String storeId,OnResponseListener<String> onResponseListener) {
        String url = UrlInterfaceManager.QUERYSIGNTIMEBYSTOREID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request != null) {
            request.add("token", Token);
            request.add("storeId", storeId);
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }


    /**
     * 用户签到打卡
     *
     * id	String		是	员工id
     * signType	String		是	签到类型:1.签到,2:签退
     * storeId	String		是	门店id
     * signAddr	String		是	签到地址
     * lng	String		是	经度
     * lat	String		是	纬度
     * */
    public static void userPunchTheClock (String id,String signType,String storeId,String signAddr,String lng,String lat,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.PUNCHTHECLOCK;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            request.add("signType",signType);
            request.add("storeId",storeId);
            request.add("signAddr",signAddr);
            request.add("lng",lng);
            request.add("lat",lat);
            RequestMeansManager.request(0, request, onResponseListener);

        }

    }

    /**
     * 用户wifi签到
     * token	string		是	口令
     * id	String		是	员工id
     * signType	String		是	签到类型:1.签到,2:签退
     * wifiId	String		是	WiFi地址
     * storeId String       是   门店ID
     * */
    public static void wifiSign(String storeId,String id,String signType,String wifiId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.WIFISIGN;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("id",id);
            request.add("signType",signType);
            request.add("wifiId",wifiId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     * 获取员工签到记录
     *
     * token	string		是	口令
     * employeeId	String		是	员工id
     * */
    public static void queryEmployeeSignListByEmployeeId(String storeId,String employeeId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.QUERYEMPLOYEESIGNLISTEMPLOYEEID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("employeeId",employeeId);
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }
    /**
     * 获取员工签到记录  根据日期查询
     *
     * String queryTime     日期
     * String storeId       门店ID
     * */

    public static void querySignListByStoreId(String storeId,String queryTime,int page,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.QUERYSIGNLISTBYSTOREID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("queryTime",queryTime);
            request.add("page",page);
            request.add("Size","1");
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }


    /**
     * 获取员3.1.6.	考勤异常记录
     *
     * String queryTime     日期
     * String storeId       门店ID
     * */

    public static void getEmployeeSignById(String businessId,String storeId,String queryTime,String employeeId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETEMPLOYEESIGNBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("queryTime",queryTime);
            request.add("emplpyeeId",employeeId);
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }


    /**
     * 签到异常记录 补卡
     *
     * token	string		是	用户通行口令
     * storeId	String		是	门店id
     * signType	String		是	签到类型:1.签到,2.签退
     * employeeId	String		是	用户ID
     * depId	String		是	部门Id
     * signTime	String		是	签卡时间
     * signStatus	String		是	签到状态;1;正常,2:迟到;3早退;4补卡;
     * */
    public static void repairSignEmployeeById(String storeId,String signType,String employeeId,String depId,String signTime,String signStatus ,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.REPAIRSIGNEMPLOYEEBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("signType",signType);
            request.add("employeeId",employeeId);
            request.add("depId",depId);
            request.add("signTime",signTime);
            request.add("signStatus",signStatus);
            RequestMeansManager.request(0, request, onResponseListener);

        }
    }
}
