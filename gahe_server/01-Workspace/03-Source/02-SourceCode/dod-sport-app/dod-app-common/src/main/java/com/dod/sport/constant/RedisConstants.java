package com.dod.sport.constant;

/**
 * RedisConstants
 * redis相关常量类
 * @author yuhao
 * @date 2016/7/25
 */
public class RedisConstants {

    /**
     * 公共url资源key
     */
    public static final String PUBLIC_URL_KEY = "AUTH_PUBLIC_URL:%s";

    /**
     * 用户url资源key
     */
    public static final String USER_URL_KEY = "AUTH_USER_URL;USER_ID:%s,URL:%s";

    /**
     * 用户菜单资源key
     */
    public static final String USER_MENU_KEY = "AUTH_USER_MENU;USER_ID:%s";


    /***************************************************************************/

    /**
     * api口令时长
     * 时间秒
     */
    public static final long API_TOKEN_TIMEOUT = 7200;

    /**
     * api访问口令
     */
    public static final String API_ACCESS_TOKEN = "API_ACCESS_TOKEN";

    /**
     * 验证码有效时间
     */
    public static final long IDENTIFYINGCODE_TIMEOUT = 300;

    /**
     * 验证码key值名
     */
  //  public static final String IDENTIFYING_CODE ="IDENTIFYING_CODE:%s";

    /**
     * 验证码key值名
     */
    public static final String IDENTIFYING_CODE ="IDENTIFYING_CODE;PHONE_NUM:%s,PLATFORM_ID:%s,OPERATION_ID:%s";


    /**
     * 商家端注册验证码key值名
     *//*
    public static final String BUSINESS_REGISTER_IDENTIFYING_CODE ="BUSINESS_REGISTER_IDENTIFYING_CODE:%s";
    *//**
     * 商家端忘记密码验证吗key值名
     *//*
    public static final String BUSINESS_PASSWORD_IDENTIFYING_CODE ="BUSINESS_PASSWORD_IDENTIFYING_CODE:%s";

    *//**
     * 客户端注册验证码key值名
     *//*
    public static final String CLIENT_REGISTER_IDENTIFYING_CODE ="CLIENT_REGISTER_IDENTIFYING_CODE:%s";
    *//**
     * 客户端忘记密码验证吗key值名
     *//*
    public static final String CLIENT_PASSWORD_IDENTIFYING_CODE ="CLIENT_PASSWORD_IDENTIFYING_CODE:%s";
*/
    /**
     * 单据key值
     */
    public static final String BILL_ID = "BILL_ID:%s";

    /**
     * 商检编号key值
     */
    public static final String BUSINESS_ID = "BUSINESS_ID:%s";

    /**
     * 门店编号key值
     */
    public static final String STORE_ID = "STORE_ID:%s";

    /**
     * 平台部门编号key值
     */
    public static final String DEPARTMENT_ID = "DEPARTMENT_ID";

    /**
     * 商家部门编号key值
     */
    public static final String DEPARTMENT_BUSI_ID = "DEPARTMENT_BUSI_ID:%s";

    /**
     * 获取员工入职key值
     */
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID:%s";

    /**
     * 获取员工key值
     */
    public static final String BASE_EMPLOYEE_ID = "BASE_EMPLOYEE_ID";

    /**
     * 平台用户编号key值
     */
    public static final String USER_ID = "USER_ID";

    /**
     * 会员编号key值
     */
    public static final String MEMBER_ID = "MEMBER_ID:%s";

    /**
     * 会员卡编号key值
     */
    public static final String MEMBERCARD_ID = "MEMBERCARD_ID";

    /**
     * 会员卡类型编号key值
     */
    public static final String MEMBERCARDTYPE_ID = "MEMBERCARDTYPE_ID";

    /**
     * 课程编号key值
     */
    public static final String COURSE_ID = "COURSE_ID:%s";

    /**
     * 课程类型编号key值
     */
    public static final String COURSETYPE_ID = "COURSETYPE_ID:%s";

    /**
     * 平台职位编号key值
     */
    public static final String POSITION_ID = "POSITION_ID";

    /**
     * 商家职位编号key值
     */
    public static final String POSITION_BUSI_ID = "POSITION_BUSI_ID:%s";

    /**
     * 系统通知编号key值
     */
    public static final String SYSTEMNOTICE_ID = "SYSTEMNOTICE_ID";

    /**
     * 会员卡编号key值
     */
    public static final String MEMCARDRELATION_ID = "MEMCARDRELATION_ID:%s";

    /**
     * 充值梯度key值
     */
    public static final String RECHARGEGRAD_ID = "RECHARGEGRAD_ID:%s";

    /**
     * 功能模块编号
     */
    public static final String MODEL_ID="MODEL_ID:%s";

    /**
     * 功能编号
     */
    public static final String FUNCTION_ID="FUNCTION_ID:%s";

    /**
     * 功能细节编号
     */
    public static final String FUNCTION_DETAIL_ID="FUNCTION_DETAIL_ID:%s";

    public static final String ROLE_ID_STRING="ROLE_ID_STRING:%s";

    public static final String MODEL_ID_STRING="MODEL_ID_STRING:%s";

    public static final String FUNCTION_ID_STRING="FUNCTION_ID_STRING:%s";

    public static final String SYSTEM_ROLE="SYSTEM_ROLE:%s";

    public static final String SYSTEM_MODEL="SYSTEM_MODEL:%s";

    public static final String SYSTEM_FUNCTION="SYSTEM_FUNCTION:%s%s";

    /**
     * 平台用户key值
     */
    public static final String CLIENTUSER_ID = "CLIENTUSER_ID";

    /**
     * 会员评价key值
     */
    public static final String MEMBEREVALUATE_ID = "MEMBEREVALUATE_ID:%s";
    /**
     * 会员充值key值
     */
    public static final String MEMBERRECHARGE_ID = "MEMBERRECHARGE_ID:%s";

    /**
     * 会员卡变更key值
     */
    public static final String MEMBERCARDMODIFY_ID = "MEMBERCARDMODIFY_ID:%s";

    /**
     * 用户消费记录key值
     */
    public static final String EXPENSE_RECORD_ID = "EXPENSE_RECORD_ID:%s";

    /**
     * 订单编号key值
     */
    public static final String ORDER_SERIAL_ID = "ORDER_SERIAL_ID:%s";
}
