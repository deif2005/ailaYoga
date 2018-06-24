package com.dod.sport.util;

import com.dod.sport.constant.RedisConstants;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.redis.IRedisRepository;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by defi on 2017/7/26.
 * redis通用操作类
 */

public class RedisCommon {

    private static Logger logger = LoggerFactory.getLogger(RedisCommon.class);

    static IRedisRepository redisRepository;

    static{
        redisRepository = SpringUtil.getBean(IRedisRepository.class);
    }

    /**
     * 设置api访问口令，限时120分钟
     * @param token
     */
    private static void setApiAccessToken(String token){
        redisRepository.set(RedisConstants.API_ACCESS_TOKEN,token,RedisConstants.API_TOKEN_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 获取api访问口令，如果不存在即生成
     * @return
     */
    public static String getApiAccessToken(){
        String token = "";
        try {
            token = UUIDGenerator.getUUID();
            if (!redisRepository.exists(RedisConstants.API_ACCESS_TOKEN)){
                setApiAccessToken(token);
                return token;
            }else{
                token = (String)redisRepository.get(RedisConstants.API_ACCESS_TOKEN);
            }
        }catch (Exception e){
            logger.error("获取api访问Token失败",e);
        }
        return token;
    }


    /**
     * 生成随机验证码,存入缓存,五分钟内有效
     * @param phoneNum
     * @param operationId
     * @return string
     */
    public static String createIdentifyingCode(String phoneNum,String platformId,String operationId){
        String keyStr = String.format(RedisConstants.IDENTIFYING_CODE, phoneNum ,platformId,
                operationId);
        if (redisRepository.exists(keyStr)){
            redisRepository.del(keyStr);
        }
        String randomStr = VertifyCodeUtil.getRandromNum();
        redisRepository.set(keyStr,randomStr, RedisConstants.IDENTIFYINGCODE_TIMEOUT,TimeUnit.SECONDS);
        return randomStr;
    }

    /**
     * token值校验
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        boolean result = false;
        String validToken = (String)redisRepository.get(RedisConstants.API_ACCESS_TOKEN);
        if (token.equals(validToken)){
            result = true;
        }
        return result;
    }
    /**
     * 验证码校验
     * @param phoneNum
     * @param identCode
     * @param platformId 1:客户端;2:商家端;3:服务端
     * @param operationId 1:注册;2:忘记密码
     * @return
     */
    public static boolean verifyIdentCode(String phoneNum,String platformId, String operationId, String identCode){
        boolean result = false;
        if (!redisRepository.exists(String.format(RedisConstants.IDENTIFYING_CODE,phoneNum,platformId,operationId))){
            return result;
        }
        String validIdentCode = (String)redisRepository.get(String.format(RedisConstants.IDENTIFYING_CODE,phoneNum,platformId,operationId));

        if (identCode.equals(validIdentCode)){
            result = true;
        }
        return result;
    }

    /**
     * 获取最新流水号
     * @param type AMB
     * @return
     */
    public static String getBillIdByType(String type){
        String dataPrefix = StringUtil.getDateByFormatString(SysConfigConstants.BILL_PREFIX_DATE);
        String maxId = "";
        //获取最新流水号
        if (!redisRepository.exists(String.format(RedisConstants.BILL_ID,type))){
            maxId = StringUtil.getMaxBillId("0",SysConfigConstants.BILL_SERIAL_LENGTH);
        }else{
            maxId = (String)redisRepository.get(String.format(RedisConstants.BILL_ID,type));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.BILL_SERIAL_LENGTH);
        }
        redisRepository.set(String.format(RedisConstants.BILL_ID,type),maxId);
        String result = type+dataPrefix+maxId;
        return result;
    }

    /**
     * 刷新单据最新流水号，并返回
     * @param nowId 当前编号
     * @param type
     * @return
     */
    public static String setAndReturnBillIdByType(String nowId, String type){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.BILL_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.BILL_ID,type),maxId);
        return maxId;
    }

    /**
     * 获取最新商家编号
     * @param businessType 商家分类
     * @return
     */
    public static String getMaxBusinessId(String businessType){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.BUSINESS_ID,businessType))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.BUSINESS_ID,businessType));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.BILL_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.BUSINESS_ID,businessType),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新商家编号，并返回
     * @param businessType 商家类型
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxBusinessId(String businessType, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.BUSINESS_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.BUSINESS_ID, businessType),maxId);
        return maxId;
    }

    /**
     * 获取最新门店编号
     * @param  businessId 商家编号
     * @return
     */
    public static String getMaxStoreId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.STORE_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.STORE_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.STORE_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.STORE_ID, businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新门店编号，并返回
     * @param nowId 当前编号
     * @param businessId 商家编号
     * @return
     */
    public static String setAndReturnMaxStoreId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.STORE_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.STORE_ID, businessId),maxId);
        return maxId;
    }

    /**
     * 获取最新部门编号
     * @return
     */
    public static String getMaxDepartmentId(){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(RedisConstants.DEPARTMENT_ID)){
            maxId = (String)redisRepository.get(String.format(RedisConstants.DEPARTMENT_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.DEPARTMENT_ID),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 获取商家最新部门编号
     * @return
     */
    public static String getMaxBusiDepartmentId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.DEPARTMENT_BUSI_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.DEPARTMENT_BUSI_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.DEPARTMENT_BUSI_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新部门编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxDepartmentId(String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.DEPARTMENT_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.DEPARTMENT_ID),maxId);
        return maxId;
    }

    /**
     * 刷新商家最新部门编号，并返回
     * @param businessId 商家id
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxDepartmentId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.DEPARTMENT_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.DEPARTMENT_BUSI_ID, businessId),maxId);
        return maxId;
    }

    /**
     * 获取最新员工基础编号
     * @return
     */
    public static String getMaxBaseEmployeeRegisterId(){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(RedisConstants.EMPLOYEE_ID)){
            maxId = (String)redisRepository.get(String.format(RedisConstants.BASE_EMPLOYEE_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
            redisRepository.set(String.format(RedisConstants.BASE_EMPLOYEE_ID),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新员工基础编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxBaseEmployeeRegisterId(String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
        redisRepository.set(String.format(RedisConstants.BASE_EMPLOYEE_ID),maxId);
        return maxId;
    }
    /**
     * 获取员工关系最大编号
     * 返回为"" 则需要从数据库获取最新编号
     * @param businessId 商家编号
     * @return
     */
    public static String getMaxEmployeeId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.EMPLOYEE_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.EMPLOYEE_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH_5);
            redisRepository.set(String.format(RedisConstants.EMPLOYEE_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新员工关系编号，并返回
     * @param businessId 商家编号
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxEmployeeId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_5);
        redisRepository.set(String.format(RedisConstants.EMPLOYEE_ID,businessId),maxId);
        return maxId;
    }

    /**
     * 获取会员编号
     * @param businessId
     * @return
     */
    public static String getMaxMemberId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBER_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBER_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
            redisRepository.set(String.format(RedisConstants.MEMBER_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxMemberId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
        redisRepository.set(String.format(RedisConstants.MEMBER_ID,businessId),maxId);
        return maxId;
    }

    /**
     * 获取会员卡编号
     * @return
     */
    public static String getMaxMemberCardId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBERCARD_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBERCARD_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.MEMBERCARD_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.MEMBERCARD_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员卡编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxMemberCardId(String memberCardId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.MEMBERCARD_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.MEMBERCARD_ID,memberCardId),maxId);
        return maxId;
    }

    /**
     * 获取会员卡卡号
     * @return
     */
    public static String getMaxMemberCardNum(String memberCardId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBERCARD_ID,memberCardId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBERCARD_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.MEMBERCARD_NUM_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.MEMBERCARD_ID,memberCardId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员卡卡号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxMemberCardNum(String memberCardId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.MEMBERCARD_NUM_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.MEMBERCARD_ID,memberCardId),maxId);
        return maxId;
    }

    /**
     * 获取课程编号
     * @return
     */
    public static String getMaxCourseId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.COURSE_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.COURSE_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COURSE_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.COURSE_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新课程编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxCourseId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COURSE_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.COURSE_ID,businessId),maxId);
        return maxId;
    }

    /**
     * 获取课程类型编号
     * @return
     */
    public static String getMaxCourseTypeId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.COURSETYPE_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.COURSETYPE_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COURSETYPE_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.COURSETYPE_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新课程类型编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxCourseTypeId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COURSETYPE_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.COURSETYPE_ID, businessId),maxId);
        return maxId;
    }

    /**
     * 获取平台职位编号
     * @return
     */
    public static String getMaxPositionId(){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.POSITION_ID))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.POSITION_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.POSITION_ID),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新平台职位编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxPositionId(String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.POSITION_ID),maxId);
        return maxId;
    }

    /**
     * 获取商家职位编号
     * @return
     */
    public static String getMaxBusiPositionId(String businessId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.POSITION_BUSI_ID,businessId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.POSITION_BUSI_ID,businessId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.POSITION_BUSI_ID,businessId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新商家最新职位编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxBusiPositionId(String businessId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.POSITION_BUSI_ID,businessId),maxId);
        return maxId;
    }

    /**
     * 获取系统消息编号
     * @return
     */
    public static String getMaxSystemNoticeId(){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.SYSTEMNOTICE_ID))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.SYSTEMNOTICE_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.SYSTEMNOTICE_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.SYSTEMNOTICE_ID),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员卡编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxSystemNoticeId(String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.SYSTEMNOTICE_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.SYSTEMNOTICE_ID),maxId);
        return maxId;
    }

    /**
     * 增加是否阅读记录
     * @param uuid 消息id
     * @param tList
     */
    public static void addIsRead(String uuid, List<String> tList){
        for (int i=0; i < tList.size(); i++){
            redisRepository.set(uuid+":"+tList.get(i), 0, 30, TimeUnit.DAYS);
        }
    }

    /**
     *获取会员关系表最大编号
     * @param memCardId
     * @return
     */
    public static String getMaxMemberCardRelationId(String memCardId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMCARDRELATION_ID,memCardId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMCARDRELATION_ID,memCardId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.MEMBERCARD_RELATION_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.MEMCARDRELATION_ID,memCardId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新会员关系表编号
     * @param memCardId
     * @param nowId
     * @return
     */
    public static String setAndReturnMaxMemberCardRelationId(String memCardId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.MEMBERCARD_RELATION_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.MEMCARDRELATION_ID,memCardId),maxId);
        return maxId;
    }


    /**
     * 获取充值梯度最大编号
     * @param membercardId
     * @return
     */
    public static String getMaxRechargegradId(String membercardId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.RECHARGEGRAD_ID,membercardId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.RECHARGEGRAD_ID,membercardId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH_3);
            redisRepository.set(String.format(RedisConstants.RECHARGEGRAD_ID,membercardId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新充值梯度编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxRechargegradId(String membercardId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_3);
        redisRepository.set(String.format(RedisConstants.RECHARGEGRAD_ID,membercardId),maxId);
        return maxId;
    }


    /**
     * 获取最大模块编号
     * @param platformId
     * @return
     */
    public static String getMaxModelId(String platformId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MODEL_ID,platformId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MODEL_ID,platformId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.MODEL_ID,platformId),maxId);
        }
        String result = maxId;
        return result;
    }

    public static String setAndReturnMaxModelId(String platformId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.MODEL_ID, platformId),maxId);
        return maxId;
    }

    /**
     * 获取最大功能编号
     * @param platformId
     * @return
     */
    public static String getMaxFunctionId(String platformId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.FUNCTION_ID,platformId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.FUNCTION_ID,platformId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.FUNCTION_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.FUNCTION_ID,platformId),maxId);
        }
        String result = maxId;
        return result;
    }

    public static String setAndReturnMaxFunctionId(String platformId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.FUNCTION_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.FUNCTION_ID, platformId),maxId);
        return maxId;
    }

    /**
     * 获取功能细节最大编号
     * @param platformId
     * @return
     */
    public static String getMaxFunctionDetailId(String platformId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.FUNCTION_DETAIL_ID,platformId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.FUNCTION_DETAIL_ID,platformId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.FUNCTION_DETAIL_ID,platformId),maxId);
        }
        String result = maxId;
        return result;
    }

    public static String setAndReturnMaxFunctionDetailId(String platformId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.FUNCTION_DETAIL_ID, platformId),maxId);
        return maxId;
    }

    /**
     * 获取平台用户编号
     * 返回为"" 则需要从数据库获取最新编号
     * @return
     */
    public static String getMaxClientUserId(){
        String maxId = "";
        if (redisRepository.exists(String.format(RedisConstants.CLIENTUSER_ID))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.CLIENTUSER_ID));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
            redisRepository.set(String.format(RedisConstants.CLIENTUSER_ID),maxId);
        }
        String result = maxId;
        return result;

    }

    /**
     * 刷新最新平台用户编号，并返回
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxClientUserId(String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_6);
        redisRepository.set(String.format(RedisConstants.CLIENTUSER_ID),maxId);
        return maxId;
    }

    /**
     * 获取会员评价的最大编号
     * @param empRelationId 员工关系表id
     * @return
     */
    public static String getMaxMemberEvaluateId(String empRelationId){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBEREVALUATE_ID,empRelationId))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBEREVALUATE_ID,empRelationId));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.BILL_SERIAL_LENGTH);
            redisRepository.set(String.format(RedisConstants.MEMBEREVALUATE_ID,empRelationId),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新商家编号，并返回
     * @param empRelationId 员工关系表id
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxMemberEvaluateId(String empRelationId, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.BUSINESS_SERIAL_LENGTH);
        redisRepository.set(String.format(RedisConstants.MEMBEREVALUATE_ID, empRelationId),maxId);
        return maxId;
    }

    /**
     * 获取会员最大充值编号
     * 返回为"" 则需要从数据库获取最新编号
     * @param dateStr 当前时间
     * @return
     */
    public static String getMaxMemberCardRechargeId(String dateStr){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBERRECHARGE_ID,dateStr))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBERRECHARGE_ID,dateStr));
            maxId = StringUtil.getMaxOrderSerialId(dateStr, maxId, SysConfigConstants.COMMON_SERIAL_LENGTH_13);
            redisRepository.set(String.format(RedisConstants.MEMBERRECHARGE_ID,dateStr),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员充值编号，并返回
     * @param dateStr 当前时间
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxMemberCardRechargeId(String dateStr, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxOrderSerialId(dateStr,nowId, SysConfigConstants.COMMON_SERIAL_LENGTH_3);
        redisRepository.set(String.format(RedisConstants.MEMBERRECHARGE_ID,dateStr),maxId);
        return maxId;
    }

    /**
     * 获取会员卡最大变更记录编号
     * 返回为"" 则需要从数据库获取最新编号
     * @param dateStr 当前事假
     * @return
     */
    public static String getMaxMembercardModifyId(String dateStr){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.MEMBERCARDMODIFY_ID,dateStr))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.MEMBERCARDMODIFY_ID,dateStr));
            maxId = StringUtil.getMaxOrderSerialId(dateStr, maxId, SysConfigConstants.COMMON_SERIAL_LENGTH_3);
            redisRepository.set(String.format(RedisConstants.MEMBERCARDMODIFY_ID,dateStr),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新会员卡最大变更记编号，并返回
     * @param dateStr 当前时间
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMembercardModifyId(String dateStr, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxOrderSerialId(dateStr,nowId,SysConfigConstants.COMMON_SERIAL_LENGTH_3);
        redisRepository.set(String.format(RedisConstants.MEMBERCARDMODIFY_ID,dateStr),maxId);
        return maxId;
    }

    /**
     * 获取消费记录最大编号
     * 返回为"" 则需要从数据库获取最新编号
     * @param dateTimeStr
     * @return
     */
    public static String getMaxExpenseRecordId(String dateTimeStr){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.EXPENSE_RECORD_ID,dateTimeStr))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.EXPENSE_RECORD_ID,dateTimeStr));
            maxId = StringUtil.getMaxBillId(maxId,SysConfigConstants.EXPENSE_RECORD_SERIAL_LENGTH_23);
            redisRepository.set(String.format(RedisConstants.EXPENSE_RECORD_ID,dateTimeStr),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新消费记录编号，并返回
     * @param dateTimeStr 商家编号
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxExpenseRecordId(String dateTimeStr, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxBillId(nowId,SysConfigConstants.EXPENSE_RECORD_SERIAL_LENGTH_23);
        redisRepository.set(String.format(RedisConstants.EXPENSE_RECORD_ID,dateTimeStr),maxId);
        return maxId;
    }
    /**
     * 获取订单编号
     * 返回为"" 则需要从数据库获取最新编号
     * @param dateTimeStr
     * @return
     */
    public static String getMaxOrderSerialId(String dateTimeStr){
        String maxId = "";
        //获取最新流水号
        if (redisRepository.exists(String.format(RedisConstants.ORDER_SERIAL_ID,dateTimeStr))){
            maxId = (String)redisRepository.get(String.format(RedisConstants.ORDER_SERIAL_ID,dateTimeStr));
            maxId = StringUtil.getMaxOrderSerialId(dateTimeStr, maxId, SysConfigConstants.ORDER_SERIAL_LENGTH_9);
            redisRepository.set(String.format(RedisConstants.ORDER_SERIAL_ID,dateTimeStr),maxId);
        }
        String result = maxId;
        return result;
    }

    /**
     * 刷新最新订单编号，并返回
     * @param dateTimeStr 商家编号
     * @param nowId 当前编号
     * @return
     */
    public static String setAndReturnMaxOrderSerialId(String dateTimeStr, String nowId){
        //刷新最新流水号
        String maxId = StringUtil.getMaxOrderSerialId(dateTimeStr,nowId, SysConfigConstants.ORDER_SERIAL_LENGTH_9);
        redisRepository.set(String.format(RedisConstants.ORDER_SERIAL_ID,dateTimeStr),maxId);
        return maxId;
    }

}