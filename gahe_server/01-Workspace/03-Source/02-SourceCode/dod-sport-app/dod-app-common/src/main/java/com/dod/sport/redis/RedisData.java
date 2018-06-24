package com.dod.sport.redis;


import com.dod.sport.constant.RedisConstants;
import com.dod.sport.domain.po.UserRole;
import com.dod.sport.util.SpringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.management.relation.RoleInfo;
import java.util.List;

/**
 * RedisConfig
 * redis缓存数据类
 * 用于获取redis缓存数据
 * @author deif
 */
public class RedisData {

    static IRedisRepository redisRepository;

    static{
        redisRepository = SpringUtil.getBean(IRedisRepository.class);
    }

    public static UserRole getRedisRoleInfo(String id){
        JSONObject data = JSONObject.fromObject(redisRepository.get(String.format(RedisConstants.SYSTEM_ROLE,id)));
        return (UserRole)JSONObject.toBean(data,UserRole.class);
    }

    public static List<String> listRedisModelEnable(String platform){
        String data = (String)redisRepository.get(String.format(RedisConstants.MODEL_ID_STRING,platform));
        JSONArray jsonArray1 = JSONArray.fromObject(data);
        List<String> modelEnableList = (List)JSONArray.toCollection(jsonArray1);
        return modelEnableList;
    }

    public static List<String> listRedisFunctionEnable(String platform){
        String data = (String)redisRepository.get(String.format(RedisConstants.FUNCTION_ID_STRING,platform));
        JSONArray jsonArray1 = JSONArray.fromObject(data);
        List<String> functionEnableList = (List)JSONArray.toCollection(jsonArray1);
        return functionEnableList;
    }
}
